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
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public synchronized void closeConnection() {
        send("Connection closed!");
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
        Scanner in;
        String name;
        ObjectInputStream socketIn;
        Object inputObject;
        try {
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            name = read;
            server.lobby(this, name);
            socketIn = new ObjectInputStream(socket.getInputStream()); //forse va nel while
            while (isActive()) {
                inputObject = socketIn.readObject();
                notify((MoveList) inputObject);
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            System.err.println("Error!" + e.getMessage());
        } finally {
            close();
        }
    }
}
