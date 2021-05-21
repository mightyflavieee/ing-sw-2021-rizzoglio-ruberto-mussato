package it.polimi.ingsw.project.server;

import it.polimi.ingsw.project.model.InitializeGameMessage;
import it.polimi.ingsw.project.model.MoveMessage;
import it.polimi.ingsw.project.model.NickNameMessage;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.observer.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<Move> implements ClientConnection, Runnable {
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

    private String joinGame(ObjectInputStream in) throws Exception {
        while (true) {
            if (!this.socket.isClosed()) {
                send(new InitializeGameMessage("Which game do you want to join?"));
                String gameId;
                try {
                    InitializeGameMessage gameMessage = (InitializeGameMessage) in.readObject();
                    gameId = gameMessage.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception("error in parsing input Object");
                }
                if (server.isGamePresent(gameId)) {
                    if (server.isGameNotFull(gameId)) {
                        return gameId;
                    } else {
                        send(new InitializeGameMessage(
                                "The game is full. You can't join. Change game or create a new one."));
                    }
                } else {
                    send(new InitializeGameMessage("No Games are present with that id. Retry."));
                }
            } else {
                throw new Exception("Client is disconnected");
            }

        }
    }

    private String createGame(ObjectInputStream in) throws Exception {
        while (true) {
            if (!this.socket.isClosed()) {
                send(new InitializeGameMessage("How many people do you want in your game? (max 4)"));
                try {
                    Integer playersNumber;
                    try {
                        InitializeGameMessage gameMessage = (InitializeGameMessage) in.readObject();
                        playersNumber = Integer.parseInt(gameMessage.getMessage());
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    if (playersNumber > 4) {
                        throw new Exception("Insert an integer number less than equal 4.");
                    }
                    return server.createGame(playersNumber);
                } catch (Exception e) {
                    send(new InitializeGameMessage(e.getMessage()));
                }
            } else {
                throw new Exception("Client is disconnected");
            }

        }
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send(new InitializeGameMessage("Welcome!\nWhat is your name?"));
            InitializeGameMessage gameMessage = (InitializeGameMessage) in.readObject();
            String name = gameMessage.getMessage();
            send(new NickNameMessage(name));
            send(new InitializeGameMessage("Do you want to \'join\' or \'create\' a game?"));
            String decision;
            String gameId;
            while (true) {
                try {
                    gameMessage = (InitializeGameMessage) in.readObject();
                    decision = gameMessage.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new Exception(e.getMessage());
                }
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
                        send(new InitializeGameMessage(e.getMessage()));
                    }
                } else {
                    send(new InitializeGameMessage("Operation not permitted! Type 'create' or 'join'."));
                }
            }
            send(new InitializeGameMessage("Your game id is: " + gameId + ".\nWait for the other players."));
            if (server.tryToStartGame(gameId)) {
                server.startGame(gameId);
            }
            while (isActive()) {
                Move moveMessage = (Move) in.readObject();
                notify((Move) moveMessage);
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
            e1.printStackTrace();
        } finally {
            close();
        }
    }

}
