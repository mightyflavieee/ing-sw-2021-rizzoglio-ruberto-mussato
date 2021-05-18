package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.playermove.DiscardLeaderCardMove;
import it.polimi.ingsw.project.model.playermove.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class ClientCLI {

    private String ip;
    private int port;
    private boolean active = true;
    private Match match;

    public ClientCLI(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.match = null;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Optional<Match> getMatch() {
        return Optional.ofNullable(match);
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if (inputObject instanceof String) {
                            System.out.println((String) inputObject);
                        } else if (inputObject instanceof Match) {
                            setMatch((Match) inputObject);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncCli(final Scanner stdin, final ObjectOutputStream socketOut) {//sends to server and shows the match
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        if(getMatch().isEmpty()) {
                            String inputLine = stdin.nextLine();
                            socketOut.writeObject(inputLine);
                        }
                        else {
                            Move move = handleTurn(stdin);
                            socketOut.writeObject(move);
                        }
                        socketOut.flush();
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Move handleTurn(final Scanner stdin){
        String inputLine;
        //example
        System.out.println("What do you want to do? \n 1-Discard a leader card");
        inputLine = stdin.nextLine();
        if(inputLine.equals("1")) {
            return new DiscardLeaderCardMove("prova");
        }
        return  null;
    }
    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        Scanner stdin = new Scanner(System.in);
        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncCli(stdin,socketOut);
            t1.start();
            t0.join();
            t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}
