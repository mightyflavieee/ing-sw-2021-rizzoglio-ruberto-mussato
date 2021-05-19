package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.playermove.DiscardLeaderCardMove;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.NoMove;

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
    private Scanner stdin;
    private String myNickname; //da inizializzare

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

    public Thread asyncCli( final ObjectOutputStream socketOut) {//sends to server and shows the match
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
                            Move move = handleTurn();
                            if(move!=null) {
                                socketOut.writeObject(move);
                            }
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

    public Move handleTurn(){
        //quando do come comando 0 entro SEMPRE in una funzione che mi permette di visualizzare le varie informazioni

        switch (this.match.getTurnPhase(myNickname)) {
            case WaitPhase:
                viewer();
                break;
            case InitialPhase:
            case EndPhase:
                return handleLeaderAction();
            case MainPhase:
                break;

        }

        return  null;
    }
    private Move handleLeaderAction(){
        //quando do come comando 0 entro SEMPRE in una funzione che mi permette di visualizzare le varie informazioni

        while(true) {
            System.out.println("Do you want to perform a Leader Card Action?\n" +
                    "0 - see informations" +
                    "1 - Discard Leader Card\n" +
                    "2 - Play Leader Card\n" +
                    "3 - no\n");
            String answer = stdin.nextLine();
            switch (answer) {
                case "0":
                    viewer();
                case "1":
                    //todo
                    System.out.println("Give the nickname");
                    return new DiscardLeaderCardMove(stdin.nextLine());
                case "2":
                case "3":
                    return new NoMove();
                default:
                    break;
            }
        }

    }
    private Move handleMainPhase(){
        //quando do come comando 0 entro SEMPRE in una funzione che mi permette di visualizzare le varie informazioni
        while(true) {
            System.out.println("What do you want to do?\n" +
                    "0 - see informations" +
                    "1 - Take Resources from Market\n" +
                    "2 - Buy one Development Card\n" +
                    "3 - Activate the Production");
            String answer = stdin.nextLine();
            switch (answer) {
                case "0":
                    viewer();
                case "1":
                case "2":
                case "3":
                default:
                    break;
            }
        }
    }

    public void viewer() {
        //todo

    }
    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        Scanner stdin = new Scanner(System.in);
        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncCli(socketOut);
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
