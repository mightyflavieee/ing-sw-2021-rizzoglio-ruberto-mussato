package it.polimi.ingsw.project.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.project.controller.Controller;
import it.polimi.ingsw.project.messages.ConfirmJoinMessage;
import it.polimi.ingsw.project.messages.ErrorChosenLeaderCards;
import it.polimi.ingsw.project.messages.ErrorJoinMessage;
import it.polimi.ingsw.project.messages.LeaderCardsToChooseMessage;
import it.polimi.ingsw.project.messages.MoveMessage;
import it.polimi.ingsw.project.messages.ResourcesToChooseMessage;
import it.polimi.ingsw.project.messages.WaitForOtherPlayersMessage;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.actionTokens.ActionToken;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.faithMap.tile.ActivableTile;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.TypeAdapters.AdapterActionToken;
import it.polimi.ingsw.project.utils.TypeAdapters.AdapterActivableTile;
import it.polimi.ingsw.project.view.RemoteView;

public class Server {
    private static final int PORT = 12345;
    private final ServerSocket serverSocket;
    private final Map<String, Lobby> mapOfAvailableLobbies = new HashMap<>();
    private final Map<String, Lobby> mapOfUnavailableLobbies = new HashMap<>();

    /**
     * @param matchId unique id of the game
     * @param connection socketClient connection of the player that wants to join
     * @param name it is the name of the player
     */
    public synchronized void addToLobby(String matchId, SocketClientConnection connection, String name){
        Lobby currentLobby = mapOfAvailableLobbies.get(matchId);
        if (currentLobby.lenght() + 1 <= currentLobby.getMaxNumberOfPlayers()) {
            currentLobby.insertPlayer(name, connection);
        }
    }

    /**
     * @param matchId the id of the wanted match
     * @return it returns true if the game is readyToStart, false if not
     */
    public synchronized boolean tryToStartGame(String matchId) {
        Lobby currentLobby = mapOfAvailableLobbies.get(matchId);
        if (currentLobby.lenght().equals(currentLobby.getMaxNumberOfPlayers())) {
            mapOfUnavailableLobbies.put(matchId, mapOfAvailableLobbies.get(matchId));
            mapOfAvailableLobbies.remove(matchId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * it is used to rejoin a game after disconnection
     * @param matchId unique id of the match
     * @param connection SocketClient connection of the player that is rejoining
     * @param nickName the name of the player that it's trying to rejoin
     */
    public void rejoinGame(String matchId, SocketClientConnection connection, String nickName) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.insertPlayer(nickName, connection);
        currentLobby.getMapOfViews().get(nickName).setClientConnection(connection);
        currentLobby.getModel().setPlayerConnectionToTrue(nickName);
    }

    /**
     * @param matchId unique id of the player
     * @param nickname name of the player that needs the cards back
     */
    public void resendCardsToPlayer(String matchId, String nickname) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        List<LeaderCard> possibleCardsToChoose = currentLobby.getLeaderCardContainer().getMapOfExtractedCards()
                .get(nickname);
        if (currentLobby.getMapOfSocketClientConnections().containsKey(nickname)) {
            currentLobby.getMapOfSocketClientConnections().get(nickname).asyncSend(new ErrorChosenLeaderCards(
                    "There was a problem handling your choose, retry.", possibleCardsToChoose));
        }
    }

    /**
     * it sends to the players the leaderCards that they need to choose
     * @param matchId unique id of the match needed to access the lobby
     */
    public void sendChooseLeaderCards(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> {
            List<LeaderCard> listToSend = currentLobby.getLeaderCardContainer().getFourCardsForPlayer(nickname);
            connection.asyncSend(new LeaderCardsToChooseMessage(listToSend));
        });
    }

    /**
     * 
     * @param matchId
     * @param nickname
     * @param selectedResources
     */
    public void addChosenResourcesToPlayer(String matchId, String nickname, List<ResourceType> selectedResources) {
        Lobby currentLobby = this.mapOfUnavailableLobbies.get(matchId);
        currentLobby.addChosenResourcesToPlayer(nickname, selectedResources);
        if (allPlayersHasChosenResources(matchId)) {
            initModel(matchId);
        } else {
            sendWaitMessageToPlayer(matchId, nickname);
        }
    }

    public synchronized boolean allPlayersHasChosenCards(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        return currentLobby.getchosenLeaderCardsByPlayer().size() == currentLobby.getMaxNumberOfPlayers();
    }

    public synchronized boolean allPlayersHasChosenResources(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        return currentLobby.getChosenResourcesByPlayer().size() == currentLobby.getMaxNumberOfPlayers() - 1;
    }

    public void sendModelBackToPlayer(String matchId, String nickname) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.getMapOfSocketClientConnections().get(nickname)
                .asyncSend(new MoveMessage(currentLobby.getModel().getMatchCopy()));
    }

    public void sendWaitMessageToPlayer(String matchId, String nickname) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.getMapOfSocketClientConnections().get(nickname).asyncSend(new WaitForOtherPlayersMessage());
    }

    public synchronized void addChosenCardsToPlayer(String matchId, String nickname,
            List<LeaderCard> chosenLeaderCards) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        if (currentLobby.leaderCardsChosenWereRight(nickname, chosenLeaderCards)) {
            currentLobby.addChosenLeaderCardsToPlayer(nickname, chosenLeaderCards);
            if (allPlayersHasChosenCards(matchId)) {
                initPlayers(matchId);
            } else {
                sendWaitMessageToPlayer(matchId, nickname);
            }
        } else {
            resendCardsToPlayer(matchId, nickname);
        }

    }

    public void initPlayers(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        List<Player> listOfPlayer = new ArrayList<>();
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> listOfPlayer.add(new Player(nickname)));
        Map<String, SocketClientConnection> mapOfConnections = currentLobby.getMapOfSocketClientConnections();
        Collections.shuffle(listOfPlayer);
        for (int i = 0; i < listOfPlayer.size(); i++) {
            final Player player = listOfPlayer.get(i);
            if (i != 0) {
                int numberOfResourcesToChoose = 0;
                switch (i) {
                    case 1:
                    case 2:
                        numberOfResourcesToChoose = 1;
                        break;
                    case 3:
                        numberOfResourcesToChoose = 2;
                        break;
                }
                mapOfConnections.get(player.getNickname())
                        .asyncSend(new ResourcesToChooseMessage(numberOfResourcesToChoose));
            }
        }
        currentLobby.setPlayerList(listOfPlayer);
        if (listOfPlayer.size() == 1) {
            initModel(matchId);
        }
    }

    public void initModel(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        List<Player> listOfPlayer = currentLobby.getPlayerList();
        Map<String, RemoteView> mapOfViews = new HashMap<>();
        for (Player player : listOfPlayer) {
            mapOfViews.put(player.getNickname(),
                    new RemoteView(player, currentLobby.getMapOfSocketClientConnections().get(player.getNickname())));
        }
        currentLobby.setMapOfViews(mapOfViews);
        for (Player player : listOfPlayer) {
            player.setLeaderCards(currentLobby.getchosenLeaderCardsByPlayer().get(player.getNickname()));
        }
        currentLobby.setModel(new Model(listOfPlayer, matchId));
        currentLobby.setController(new Controller(currentLobby.getModel()));
        for (String nickname : currentLobby.getMapOfViews().keySet()) {
            currentLobby.getModel().addObserver(currentLobby.getMapOfViews().get(nickname));
            currentLobby.getMapOfViews().get(nickname).addObserver(currentLobby.getController());
        }
        currentLobby.getModel().getMatch().moveForwardForStartingGame();
        currentLobby.getModel().getMatch().setSelectedResourcesForEachPlayer(currentLobby.getChosenResourcesByPlayer());
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> connection.asyncSend(new MoveMessage(currentLobby.getModel().getMatchCopy())));
    }

    public void recreateLobby(String gameId) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ActivableTile.class, new AdapterActivableTile())
                .registerTypeAdapter(ActionToken.class, new AdapterActionToken()).serializeNulls().create();
        try {
            Reader reader = new FileReader(gameId + ".json");
            Model recreatedModel = gson.fromJson(reader, Model.class);
            reader.close();
            recreatedModel.reAddObserversOnMatch();
            Lobby recreatedLobby = new Lobby(recreatedModel.extractNumberOfPlayers());
            
            recreatedLobby.setModel(recreatedModel);
            Map<String, SocketClientConnection> mapOfSocketClientConnection = new HashMap<>();
            Map<String, RemoteView> mapOfViews = new HashMap<>();
            for (Player player : recreatedLobby.getModel().getMatch().getPlayerList()) {
                final Socket newSocket = new Socket();
                newSocket.close();
                mapOfSocketClientConnection.put(player.getNickname(), new SocketClientConnection(newSocket, this));
                player.setIsConnected(false);
                mapOfViews.put(player.getNickname(),
                        new RemoteView(player, mapOfSocketClientConnection.get(player.getNickname())));
            }
            recreatedLobby.setMapOfSocketClientConnections(mapOfSocketClientConnection);
            recreatedLobby.setMapOfViews(mapOfViews);
            recreatedLobby.setController(new Controller(recreatedLobby.getModel()));
            for (String nickname : recreatedLobby.getMapOfViews().keySet()) {
                recreatedLobby.getModel().addObserver(recreatedLobby.getMapOfViews().get(nickname));
                recreatedLobby.getMapOfViews().get(nickname).addObserver(recreatedLobby.getController());
            }
            this.mapOfUnavailableLobbies.put(gameId, recreatedLobby);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isGamePresent(String id) {
        return this.mapOfAvailableLobbies.containsKey(id);
    }

    public boolean isGameStarted(String id) {
        return this.mapOfUnavailableLobbies.containsKey(id);
    }

    public boolean isNicknameUnique(String gameId, String nickName) {
        Lobby currentLobby = this.mapOfAvailableLobbies.get(gameId);
        return !currentLobby.getMapOfSocketClientConnections().containsKey(nickName);
    }

    public boolean isPlayerPresentAndDisconnected(String gameId, String nickName) {
        Lobby currentLobby = this.mapOfUnavailableLobbies.get(gameId);
        return currentLobby.isPlayerPresentAndDisconnected(nickName);
    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                Thread threadSocket = new Thread(socketConnection);
                threadSocket.start();
            } catch (IOException e) {
                System.out.println("Connection Error!");
                break;
            }
        }
    }

    public String createGame(Integer playersNumber) {
        while (true) {
            UUID uuid = UUID.randomUUID();
            String gameId = uuid.toString().substring(0, 5);
            if (!this.mapOfAvailableLobbies.containsKey(gameId) && !(new File(gameId + ".json").exists())) {
                this.mapOfAvailableLobbies.put(gameId, new Lobby(playersNumber));
                return gameId;
            }
        }
    }

    public boolean isGameNotFull(String gameId) {
        return !this.mapOfAvailableLobbies.get(gameId).getMaxNumberOfPlayers().equals(this.mapOfAvailableLobbies.get(gameId)
                .lenght());
    }

    public boolean doesGameExistedAndHasNotRestarted(String gameId) {
        if (new File(gameId + ".json").exists()) {
            if (!this.mapOfUnavailableLobbies.containsKey(gameId)) {
                return true;
            } else return !this.mapOfUnavailableLobbies.get(gameId).isGameStarted();
        }
        return false;
    }

    public boolean isRestartedGameReadyToStart(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        int playersConnectedCounter = 0;
        for (String nickname : currentLobby.getMapOfSocketClientConnections().keySet()) {
            if (!currentLobby.getMapOfSocketClientConnections().get(nickname).getSocket().isClosed()) {
                playersConnectedCounter++;
            }
        }
        return playersConnectedCounter == currentLobby.getMaxNumberOfPlayers();
    }

    public void sendToAllPlayersMoveMessage(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> connection.asyncSend(new MoveMessage(currentLobby.getModel().getMatchCopy())));
    }

    public void handleReconnectionAfterServerCrashed(SocketClientConnection connection, String gameId,
            String nickName) {
        if (this.isGameStarted(gameId)) {
            if (this.isPlayerPresentAndDisconnected(gameId, nickName)) {
                this.rejoinGame(gameId, connection, nickName);
                if (this.isRestartedGameReadyToStart(gameId)) {
                    this.setGameStarted(gameId);
                    this.sendToAllPlayersMoveMessage(gameId);
                } else {
                    this.sendWaitMessageToPlayer(gameId, nickName);
                }
            } else {
                connection.send(new ErrorJoinMessage(
                        "We are sorry but in this game you are not a player or there is already a player with this name but it is connected! Try another nickname."));
            }
        } else {
            if (this.doesMatchContainPlayer(nickName, gameId)) {
                this.recreateLobby(gameId);
                this.rejoinGame(gameId, connection, nickName);
                if (this.isRestartedGameReadyToStart(gameId)) {
                    this.sendToAllPlayersMoveMessage(gameId);
                } else {
                    this.sendWaitMessageToPlayer(gameId, nickName);
                }
            } else {
                connection.send(new ErrorJoinMessage(
                        "We are sorry but in this game you are not a player. Try another nickname."));
            }
        }
    }

    private void setGameStarted(String gameId) {
        this.mapOfUnavailableLobbies.get(gameId).setGameStarted();
    }

    private boolean doesMatchContainPlayer(String nickName, String gameId) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ActivableTile.class, new AdapterActivableTile())
                .registerTypeAdapter(ActionToken.class, new AdapterActionToken()).serializeNulls().create();
        try {
            Reader reader = new FileReader(gameId + ".json");
            Model recreatedModel = gson.fromJson(reader, Model.class);
            reader.close();
            for (Player player : recreatedModel.getMatch().getPlayerList()) {
                if (player.getNickname().equals(nickName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void handleReconnectionOnNotStartedGame(SocketClientConnection connection, String gameId, String nickName) {
        if (this.isGameNotFull(gameId)) {
            if (this.isNicknameUnique(gameId, nickName)) {
                try {
                    this.addToLobby(gameId, connection, nickName);
                    connection.send(new ConfirmJoinMessage(gameId));
                    if (this.tryToStartGame(gameId)) {
                        this.setGameStarted(gameId);
                        this.sendChooseLeaderCards(gameId);
                    }
                } catch (Exception e) {
                    connection.send(new ErrorJoinMessage(e.getMessage()));
                }
            } else {
                connection.send(new ErrorJoinMessage(
                        "We are sorry but there is already a player with this nickname! Try a different one."));
            }
        } else {
            connection.send(new ErrorJoinMessage(
                    "We are sorry but the game you are trying to join is full! Try a different one."));
        }
    }

    public void handleReconnectionOnStartedGame(SocketClientConnection connection, String gameId, String nickName) {
        if (this.isPlayerPresentAndDisconnected(gameId, nickName)) {
            this.rejoinGame(gameId, connection, nickName);
            this.sendModelBackToPlayer(gameId, nickName);
        } else {
            connection.send(new ErrorJoinMessage(
                    "We are sorry but this game is already started and you are not a player of this game or there is already a player with this name but it is connected! Try another nickname."));
        }
    }

}
