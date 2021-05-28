package it.polimi.ingsw.project.server;

import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.Request;
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

    public Server getServer() {
        return server;
    }

    public synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
            out.reset();
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
                Request receivedObject = (Request) socketIn.readObject();
                receivedObject.action(this);
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
