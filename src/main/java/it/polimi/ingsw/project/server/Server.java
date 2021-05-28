package it.polimi.ingsw.project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.polimi.ingsw.project.controller.Controller;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.observer.custom.CreateJoinGameObserver;
import it.polimi.ingsw.project.utils.Utils;
import it.polimi.ingsw.project.view.RemoteView;
import it.polimi.ingsw.project.view.View;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, Lobby> mapOfAvailableLobbies = new HashMap<String, Lobby>();
    private Map<String, Lobby> mapOfUnavailableLobbies = new HashMap<String, Lobby>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<ClientConnection, ClientConnection>();

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

    public void startGame(String matchId) {
        Lobby currentLobby = mapOfUnavailableLobbies.get(matchId);
        List<ClientConnection> listOfClientConnections = new ArrayList<ClientConnection>();
        List<Player> listOfPlayer = new ArrayList<Player>();
        currentLobby.getListOfPlayerConnections().forEach((playerConnection) -> {
            listOfPlayer.add(new Player(playerConnection.getName()));
            listOfClientConnections.add(playerConnection.getConnection());
        });
        List<View> listOfViews = new ArrayList<View>();
        for (int i = 0; i < currentLobby.getListOfPlayerConnections().size(); i++) {
            listOfViews.add(new RemoteView(listOfPlayer.get(i),
                    Utils.extractOpponentsName(listOfPlayer.get(i), listOfPlayer), listOfClientConnections.get(i)));
        }
        Collections.shuffle(listOfPlayer);
        Model model = new Model(listOfPlayer);
        Controller controller = new Controller(model);
        for (View view : listOfViews) {
            model.addObserver(view);
            view.addObserver(controller);
        }
        listOfClientConnections.forEach((ClientConnection connection) -> {
            connection.asyncSend(model.getMatchCopy());
        });
    }

    public synchronized boolean isGamePresent(String id) {
        if (this.mapOfAvailableLobbies.keySet().contains(id)) {
            return true;
        } else {
            return false;
        }

    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                socketConnection.addCreateJoinGameObserver(new CreateJoinGameObserver(this, socketConnection));
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }

    public synchronized void deregisterConnection(SocketClientConnection socketClientConnection) {
        ClientConnection opponent = playingConnection.get(socketClientConnection);
        if (opponent != null) {
            opponent.closeConnection();
        }
        playingConnection.remove(socketClientConnection);
        playingConnection.remove(opponent);
    }

    public String createGame(Integer playersNumber) {
        while (true) {
            UUID uuid = UUID.randomUUID();
            String gameId = uuid.toString().substring(0, 5);
            if (!this.mapOfAvailableLobbies.containsKey(gameId)) {
                List<PlayerConnection> listPlayerConnections = new ArrayList<PlayerConnection>();
                this.mapOfAvailableLobbies.put(gameId, new Lobby(gameId, playersNumber, listPlayerConnections));
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
