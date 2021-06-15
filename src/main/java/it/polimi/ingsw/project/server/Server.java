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
import it.polimi.ingsw.project.messages.ErrorChosenLeaderCards;
import it.polimi.ingsw.project.messages.LeaderCardsToChooseMessage;
import it.polimi.ingsw.project.messages.MoveMessage;
import it.polimi.ingsw.project.messages.WaitForLeaderCardsMessage;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
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
        for (int i = 0; i < currentLobby.getMapOfSocketClientConnections().size(); i++) {
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

    public void recreateLobby(String gameId) {
        Gson gson = new GsonBuilder().create();
        try {
            Reader reader = new FileReader(gameId + ".json");
            Model recreatedModel = gson.fromJson(reader, Model.class);
            Lobby recreatedLobby = new Lobby(gameId, recreatedModel.extractNumberOfPlayers());
            recreatedLobby.setModel(recreatedModel);

            Map<String, RemoteView> mapOfViews = new HashMap<>();
            for (Player player : recreatedLobby.getModel().getMatch().getPlayerList()) {
                Socket newSocket = new Socket();
                newSocket.close();
                mapOfViews.put(player.getNickname(),
                        new RemoteView(player, new SocketClientConnection(newSocket, this)));
            }
            recreatedLobby.setMapOfViews(mapOfViews);
            recreatedLobby.setController(new Controller(recreatedLobby.getModel()));
            for (String nickname : recreatedLobby.getMapOfViews().keySet()) {
                recreatedLobby.getModel().addObserver(recreatedLobby.getMapOfViews().get(nickname));
                recreatedLobby.getMapOfViews().get(nickname).addObserver(recreatedLobby.getController());
            }
            reader.close();
            this.mapOfUnavailableLobbies.put(gameId, recreatedLobby);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
