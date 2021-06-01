package it.polimi.ingsw.project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.project.controller.Controller;
import it.polimi.ingsw.project.messages.ErrorChosenLeaderCards;
import it.polimi.ingsw.project.messages.LeaderCardsToChooseMessage;
import it.polimi.ingsw.project.messages.MoveMessage;
import it.polimi.ingsw.project.messages.WaitForLeaderCardsMessage;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.utils.Utils;
import it.polimi.ingsw.project.view.RemoteView;
import it.polimi.ingsw.project.view.View;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
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
        List<ClientConnection> listOfClientConnections = new ArrayList<ClientConnection>();
        List<Player> listOfPlayer = new ArrayList<Player>();
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> {
            listOfPlayer.add(new Player(nickname));
            listOfClientConnections.add(connection);
        });
        List<View> listOfViews = new ArrayList<View>();
        for (int i = 0; i < currentLobby.getMapOfSocketClientConnections().size(); i++) {
            listOfViews.add(new RemoteView(listOfPlayer.get(i),
                    Utils.extractOpponentsName(listOfPlayer.get(i), listOfPlayer), listOfClientConnections.get(i)));
        }
        Collections.shuffle(listOfPlayer);
        for (Player player : listOfPlayer) {
            player.setLeaderCards(currentLobby.getchosenLeaderCardsByPlayer().get(player.getNickname()));
        }
        Model model = new Model(listOfPlayer);
        Controller controller = new Controller(model);
        for (View view : listOfViews) {
            model.addObserver(view);
            view.addObserver(controller);
        }
        currentLobby.getMapOfSocketClientConnections().forEach((String nickname, SocketClientConnection connection) -> {
            connection.asyncSend(new MoveMessage(model.getMatchCopy()));
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
                threadSocket.start();;
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    public String createGame(Integer playersNumber) {
        while (true) {
            UUID uuid = UUID.randomUUID();
            String gameId = uuid.toString().substring(0, 5);
            if (!this.mapOfAvailableLobbies.containsKey(gameId)) {
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

}
