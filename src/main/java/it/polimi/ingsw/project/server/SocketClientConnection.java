package it.polimi.ingsw.project.server;

import it.polimi.ingsw.project.model.playermove.DisconnectRequestMove;
import it.polimi.ingsw.project.model.playermove.interfaces.Controllable;
import it.polimi.ingsw.project.model.playermove.interfaces.Request;
import it.polimi.ingsw.project.observer.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class SocketClientConnection extends Observable<Controllable> implements ClientConnection, Runnable{
    private static final long serialVersionUID = 3840280592475092704L;
    private final Socket socket;
    private ObjectOutputStream out;
    private final Server server;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public Socket getSocket() {
        return socket;
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
        }
    }

    private synchronized void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() {
        closeConnection();
        notify(new DisconnectRequestMove());
        System.out.println("Deregistering client...");
        System.out.println("Done!");
    }

    public void asyncSend(final Object message) {
        new Thread(() -> send(message)).start();
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
            while (isActive()) {
                Request receivedObject = (Request) socketIn.readObject();
                System.out.println(receivedObject);
                receivedObject.action(this);
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("An error occured!");
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
            e1.printStackTrace();
        } finally {
            this.close();
        }
    }

}
