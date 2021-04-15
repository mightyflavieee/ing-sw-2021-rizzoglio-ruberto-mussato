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
import it.polimi.ingsw.project.utils.Utils;
import it.polimi.ingsw.project.view.RemoteView;
import it.polimi.ingsw.project.view.View;
import it.polimi.ingsw.project.view.gameMessage;

public class Server {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();

    public synchronized void lobby(ClientConnection c, String name) {
        waitingConnection.put(name, c);
        if (waitingConnection.size() == 4) {
            List<ClientConnection> listOfClientConnections = new ArrayList<ClientConnection>();
            waitingConnection.forEach((nickname, client) -> {
                listOfClientConnections.add(client);
            });
            List<Player> listOfPlayer = new ArrayList<Player>();
            waitingConnection.forEach((nickname, client) -> {
                listOfPlayer.add(new Player(nickname));
            });
            List<View> listOfViews = new ArrayList<View>();
            for (int i = 0; i < waitingConnection.size(); i++) {
                listOfViews.add(new RemoteView(listOfPlayer.get(i),
                        Utils.extractOpponentsName(listOfPlayer.get(i), listOfPlayer), listOfClientConnections.get(i)));
            }
            Model model = new Model(listOfPlayer);
            Controller controller = new Controller(model);
            for (View view : listOfViews) {
                model.addObserver(view);
                view.addObserver(controller);
            }
            waitingConnection.clear();
            listOfClientConnections.forEach((ClientConnection connection) -> {
                connection.asyncSend(model.getPlayerCopy());
            });
            Collections.shuffle(listOfClientConnections);
            listOfClientConnections.get(0).asyncSend(gameMessage.moveMessage);
            listOfClientConnections.remove(0);
            listOfClientConnections.forEach((ClientConnection connection) -> {
                connection.asyncSend(gameMessage.waitMessage);
            });
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
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while (iterator.hasNext()) {
            if (waitingConnection.get(iterator.next()) == socketClientConnection) {
                iterator.remove();
            }
        }
    }
}
