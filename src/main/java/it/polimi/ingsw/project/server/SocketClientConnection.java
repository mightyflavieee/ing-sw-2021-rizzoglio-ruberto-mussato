package it.polimi.ingsw.project.server;

import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.MoveList;
import it.polimi.ingsw.project.observer.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<MoveList> implements ClientConnection, Runnable {
    private Socket socket;
    private ObjectOutputStream out;
    private Server server;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public synchronized boolean isActive() {
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.close();
        }
    }

    @Override
    public synchronized void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    private String joinGame(Scanner in) throws Exception {
        while (true) {
            if (!this.socket.isClosed()) {
                send("Which game do you want to join?");
                String gameId = in.nextLine();
                if (server.isGamePresent(gameId)) {
                    if (server.isGameNotFull(gameId)) {
                        return gameId;
                    } else {
                        send("The game is full. You can't join. Change game or create a new one.");
                    }
                } else {
                    send("No Games are present with that id. Retry.");
                }
            } else {
                throw new Exception("Client is disconnected");
            }

        }
    }

    private String createGame(Scanner in) throws Exception {
        while (true) {
            if (!this.socket.isClosed()) {
                send("How many people do you want in your game? (max 4)");
                try {
                    Integer playersNumber = in.nextInt();
                    if (playersNumber > 4) {
                        throw new Exception();
                    }
                    return server.createGame(playersNumber);
                } catch (Exception e) {
                    send("Insert a integer number less than equal 4.");
                }
            } else {
                throw new Exception("Client is disconnected");
            }

        }
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String name = in.nextLine();
            send("Do you want to \'join\' or \'create\' a game?");
            String decision;
            String gameId;
            while (true) {
                decision = in.nextLine();
                if (decision.equals("join") || decision.equals("create")) {
                    if (decision.equals("join")) {
                        gameId = this.joinGame(in);
                    } else {
                        gameId = this.createGame(in);
                    }
                    try {
                        server.addToLobby(gameId, this, name);
                        break;
                    } catch (Exception e) {
                        send(e.getMessage());
                    }
                } else {
                    send("Operation not permitted! Type 'create' or 'join'.");
                }
            }
            send("Your game id is: " + gameId + ".\nWait for the other players.");
            if (server.tryToStartGame(gameId)) {
                server.startGame(gameId);
            }
            ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
            while (isActive()) {
                Object inputObject = socketIn.readObject();
                notify((MoveList) inputObject);
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        } catch (Exception e1) {
            System.err.println(e1.getMessage());
            e1.printStackTrace();
        } finally {
            close();
        }
    }

}
