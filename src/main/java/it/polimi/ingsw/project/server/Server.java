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
import it.polimi.ingsw.project.messages.WaitForLeaderCardsMessage;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.actionTokens.ActionToken;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.faithMap.tile.ActivableTile;
import it.polimi.ingsw.project.utils.TypeAdapters.AdapterActionToken;
import it.polimi.ingsw.project.utils.TypeAdapters.AdapterActivableTile;
import it.polimi.ingsw.project.view.RemoteView;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private Map<String, Lobby> mapOfAvailableLobbies = new HashMap<String, Lobby>();
    private Map<String, Lobby> mapOfUnavailableLobbies = new HashMap<String, Lobby>();

    public synchronized void addToLobby(String matchId, SocketClientConnection connection, String name)
            throws Exception {
        Lobby currentLobby = mapOfAvailableLobbies.get(matchId);
        if (currentLobby.lenght() + 1 <= currentLobby.getMaxNumberOfPlayers()) {
            currentLobby.insertPlayer(name, connection);
        } else {
            throw new Exception("The lobby is already full.");
        }
    }

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

    public void rejoinGame(String matchId, SocketClientConnection connection, String nickName) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.insertPlayer(nickName, connection);
        currentLobby.getMapOfViews().get(nickName).setClientConnection(connection);
        currentLobby.getModel().setPlayerConnectionToTrue(nickName);
    }

    public void resendCardsToPlayer(String matchId, String nickname) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        List<LeaderCard> possibleCardsToChoose = currentLobby.getLeaderCardContainer().getMapOfExtractedCards()
                .get(nickname);
        if (currentLobby.getMapOfSocketClientConnections().containsKey(nickname)) {
            currentLobby.getMapOfSocketClientConnections().get(nickname).asyncSend(new ErrorChosenLeaderCards(
                    "There was a problem handling your choose, retry.", possibleCardsToChoose));
        }
    }

    public void sendChooseLeaderCards(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> {
            List<LeaderCard> listToSend = currentLobby.getLeaderCardContainer().getFourCardsForPlayer(nickname);
            connection.asyncSend(new LeaderCardsToChooseMessage(listToSend));
        });
    }

    public synchronized boolean allPlayersHasChosenCards(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        if (currentLobby.getchosenLeaderCardsByPlayer().size() == currentLobby.getMaxNumberOfPlayers()) {
            return true;
        } else {
            return false;
        }
    }

    public void sendModelBackToPlayer(String matchId, String nickname) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.getMapOfSocketClientConnections().get(nickname)
                .asyncSend(new MoveMessage(currentLobby.getModel().getMatchCopy()));
    }

    public void sendWaitMessageToPlayer(String matchId, String nickname) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.getMapOfSocketClientConnections().get(nickname).asyncSend(new WaitForLeaderCardsMessage());
    }

    public synchronized void addChosenCardsToPlayer(String matchId, String nickname,
            List<LeaderCard> chosenLeaderCards) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        if (currentLobby.leaderCardsChosenWereRight(nickname, chosenLeaderCards)) {
            currentLobby.addChosenLeaderCardsToPlayer(nickname, chosenLeaderCards);
            if (allPlayersHasChosenCards(matchId)) {
                initModel(matchId);
            } else {
                sendWaitMessageToPlayer(matchId, nickname);
            }
        } else {
            resendCardsToPlayer(matchId, nickname);
        }

    }

    public void initModel(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        List<SocketClientConnection> listOfClientConnections = new ArrayList<SocketClientConnection>();
        List<Player> listOfPlayer = new ArrayList<Player>();
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> {
            listOfPlayer.add(new Player(nickname));
            listOfClientConnections.add(connection);
        });
        Map<String, RemoteView> mapOfViews = new HashMap<>();
        for (int i = 0; i < currentLobby.lenght(); i++) {
            mapOfViews.put(listOfPlayer.get(i).getNickname(),
                    new RemoteView(listOfPlayer.get(i), listOfClientConnections.get(i)));
        }
        currentLobby.setMapOfViews(mapOfViews);
        Collections.shuffle(listOfPlayer);
        for (Player player : listOfPlayer) {
            player.setLeaderCards(currentLobby.getchosenLeaderCardsByPlayer().get(player.getNickname()));
        }
        currentLobby.setModel(new Model(listOfPlayer, matchId));
        currentLobby.setController(new Controller(currentLobby.getModel()));
        for (String nickname : currentLobby.getMapOfViews().keySet()) {
            currentLobby.getModel().addObserver(currentLobby.getMapOfViews().get(nickname));
            currentLobby.getMapOfViews().get(nickname).addObserver(currentLobby.getController());
        }
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> {
            connection.asyncSend(new MoveMessage(currentLobby.getModel().getMatchCopy()));
        });
    }

    public void recreateLobby(String gameId) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ActivableTile.class, new AdapterActivableTile())
                .registerTypeAdapter(ActionToken.class, new AdapterActionToken()).serializeNulls().create();
        try {
            Reader reader = new FileReader(gameId + ".json");
            Model recreatedModel = gson.fromJson(reader, Model.class);
            reader.close();
            recreatedModel.reAddObserversOnMatch();
            Lobby recreatedLobby = new Lobby(gameId, recreatedModel.extractNumberOfPlayers());
            recreatedLobby.setModel(recreatedModel);
            Map<String, SocketClientConnection> mapOfSocketClientConnection = new HashMap<String, SocketClientConnection>();
            for (Player player : recreatedLobby.getModel().getMatch().getPlayerList()) {
                final Socket newSocket = new Socket();
                newSocket.close();
                mapOfSocketClientConnection.put(player.getNickname(), new SocketClientConnection(newSocket, this));
            }
            recreatedLobby.setMapOfSocketClientConnections(mapOfSocketClientConnection);
            List<SocketClientConnection> listOfClientConnections = new ArrayList<SocketClientConnection>();
            List<Player> listOfPlayer = new ArrayList<Player>();
            recreatedLobby.getMapOfSocketClientConnections()
                    .forEach((String nickname, SocketClientConnection connection) -> {
                        listOfPlayer.add(new Player(nickname));
                        listOfClientConnections.add(connection);
                    });
            Map<String, RemoteView> mapOfViews = new HashMap<>();
            for (int i = 0; i < recreatedLobby.lenght(); i++) {
                mapOfViews.put(listOfPlayer.get(i).getNickname(),
                        new RemoteView(listOfPlayer.get(i), listOfClientConnections.get(i)));
            }

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
        if (this.mapOfAvailableLobbies.keySet().contains(id)) {
            return true;
        }
        return false;
    }

    public boolean isGameStarted(String id) {
        if (this.mapOfUnavailableLobbies.keySet().contains(id)) {
            return true;
        }
        return false;
    }

    public boolean isNicknameUnique(String gameId, String nickName) {
        Lobby currentLobby = this.mapOfAvailableLobbies.get(gameId);
        if (currentLobby.getMapOfSocketClientConnections().containsKey(nickName)) {
            return false;
        }
        return true;
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
            }
        }
    }

    public String createGame(Integer playersNumber) {
        while (true) {
            UUID uuid = UUID.randomUUID();
            String gameId = uuid.toString().substring(0, 5);
            if (!this.mapOfAvailableLobbies.containsKey(gameId) && !(new File(gameId + ".json").exists())) {
                this.mapOfAvailableLobbies.put(gameId, new Lobby(gameId, playersNumber));
                return gameId;
            }
        }
    }

    public boolean isGameNotFull(String gameId) {
        if (this.mapOfAvailableLobbies.get(gameId).getMaxNumberOfPlayers() == this.mapOfAvailableLobbies.get(gameId)
                .lenght()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean doesGameExisted(String gameId) {
        if (new File(gameId + ".json").exists()) {
            return true;
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
        if (playersConnectedCounter == currentLobby.getMaxNumberOfPlayers()) {
            return true;
        }
        return false;
    }

    public void sendToAllPlayersMoveMessage(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> {
            connection.asyncSend(new MoveMessage(currentLobby.getModel().getMatchCopy()));
        });
    }

    public void handleReconnectionAfterServerCrashed(SocketClientConnection connection, String gameId,
            String nickName) {
        if (connection.getServer().isGameStarted(gameId)) {
            if (connection.getServer().isPlayerPresentAndDisconnected(gameId, nickName)) {
                connection.getServer().rejoinGame(gameId, connection, nickName);
                if (connection.getServer().isRestartedGameReadyToStart(gameId)) {
                    connection.getServer().sendToAllPlayersMoveMessage(gameId);
                } else {
                    connection.getServer().sendWaitMessageToPlayer(gameId, nickName);
                }
            } else {
                connection.send(new ErrorJoinMessage(
                        "We are sorry but in this game you are not a player or there is already a player with this name but it is connected! Try another nickname."));
            }
        } else {
            connection.getServer().recreateLobby(gameId);
            connection.getServer().rejoinGame(gameId, connection, nickName);
            if (connection.getServer().isRestartedGameReadyToStart(gameId)) {
                connection.getServer().sendToAllPlayersMoveMessage(gameId);
            } else {
                connection.getServer().sendWaitMessageToPlayer(gameId, nickName);
            }
        }
    }

    public void handleReconnectionOnNotStartedGame(SocketClientConnection connection, String gameId, String nickName) {
        if (connection.getServer().isGameNotFull(gameId)) {
            if (connection.getServer().isNicknameUnique(gameId, nickName)) {
                try {
                    connection.getServer().addToLobby(gameId, connection, nickName);
                    connection.send(new ConfirmJoinMessage(gameId));
                    if (connection.getServer().tryToStartGame(gameId)) {
                        connection.getServer().sendChooseLeaderCards(gameId);
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
        if (connection.getServer().isPlayerPresentAndDisconnected(gameId, nickName)) {
            connection.getServer().rejoinGame(gameId, connection, nickName);
            connection.getServer().sendModelBackToPlayer(gameId, nickName);
        } else {
            connection.send(new ErrorJoinMessage(
                    "We are sorry but this game is already started and you are not a player of this game or there is already a player with this name but it is connected! Try another nickname."));
        }
    }

}
