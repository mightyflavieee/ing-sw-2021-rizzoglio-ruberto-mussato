package it.polimi.ingsw.project.server;

import it.polimi.ingsw.project.messages.ConfirmJoinMessage;
import it.polimi.ingsw.project.messages.CreateGameMessage;
import it.polimi.ingsw.project.messages.ErrorJoinMessage;
import it.polimi.ingsw.project.messages.JoinGameMessage;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.observer.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

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

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
            while (isActive()) {
                Object receivedObject = socketIn.readObject();
                if (receivedObject instanceof Move) {
                    Move moveMessage = (Move) receivedObject;
                    notify((Move) moveMessage);
                } else if (receivedObject instanceof JoinGameMessage) {
                    JoinGameMessage join = (JoinGameMessage) receivedObject;
                    if (this.server.isGamePresent(join.getGameId())) {
                        if (this.server.isGameNotFull(join.getGameId())) {
                            try {
                                this.server.addToLobby(join.getGameId(), this, join.getNickName());
                                out.writeObject(new ConfirmJoinMessage(join.getGameId()));
                                out.flush();
                                if (this.server.tryToStartGame(join.getGameId())) {
                                    this.server.startGame(join.getGameId());
                                }
                            } catch (Exception e) {
                                out.writeObject(new ErrorJoinMessage(e.getMessage()));
                                out.flush();
                            }
                        } else {
                            out.writeObject(new ErrorJoinMessage(
                                    "We are sorry but the game you are trying to join is full! Try a different one."));
                            out.flush();
                        }
                    } else {
                        out.writeObject(new ErrorJoinMessage(
                                "We are sorry but we couldn't find the game you are trying to join. Check the id!"));
                        out.flush();
                    }

                } else if (receivedObject instanceof CreateGameMessage) {
                    CreateGameMessage create = (CreateGameMessage) receivedObject;
                    String gameId = this.server.createGame(create.getNumberOfPlayers());
                    try {
                        this.server.addToLobby(gameId, this, create.getNickName());
                        out.writeObject(new ConfirmJoinMessage(gameId));
                        out.flush();
                        if (this.server.tryToStartGame(gameId)) {
                            this.server.startGame(gameId);
                        }
                    } catch (Exception e) {
                        out.writeObject(new ErrorJoinMessage(e.getMessage()));
                    }
                }
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
