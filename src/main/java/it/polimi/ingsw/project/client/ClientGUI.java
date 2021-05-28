package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.client.gui.InsertNameListener;
import it.polimi.ingsw.project.messages.ResponseMessage;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.playermove.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ClientGUI {

    private String ip;
    private String gameId;
    private int port;
    private boolean active = true;
    private Match match;
    private Scanner stdin;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    private String myNickname; // da inizializzare
    private boolean lock;
    private JFrame jFrame;
    private JPanel jPanel;

    public ClientGUI(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.match = null;
        this.lock = true;
    }

    public synchronized void isLock() throws InterruptedException {
        if (this.lock) {
            wait();
        }
    }

    public ClientGUI getInstance() {
        return this;
    }

    public synchronized void unLock() {
        this.lock = false;
        notifyAll();
    }

    public synchronized void setLock() {
        this.lock = true;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setNickname(String name) {
        this.myNickname = name;
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

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return this.gameId;
    }

    public Thread asyncReadFromSocket() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        ResponseMessage inputObject = (ResponseMessage) socketIn.readObject();
                      //  inputObject.action(getInstance());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    setActive(false);
                }
            }
        });
        t.start();
        return t;

    }

    public Thread buildGame() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                jPanel = new JPanel();
                jPanel.setLayout(new GridLayout(4,1));
                jPanel.add(new JLabel("What is your name?"));
                JTextField jTextField = new JTextField();
                jTextField.addActionListener(new InsertNameListener(getInstance(),jTextField));
                jPanel.add(jTextField);
                jFrame.add(jPanel);
                jFrame.pack();
//                while (true) {
//                    System.out.println("What do you want to 'join' or 'create' a game?");
//                    String request = stdin.nextLine();
//                    boolean wasValid = false;
//                    if (request.equals("join")) {
//                        wasValid = joinGame();
//                    } else if (request.equals("create")) {
//                        wasValid = createGame();
//                    } else {
//                        System.out.println("Request not valid!");
//                    }
//                    if (wasValid) {
//                        break;
//                    }
//                }
            }
        });

        t.start();
        return t;
    }

    private boolean joinGame() { // returns true if the joining request was created successfully
        System.out.println("Which game do you want to join? Type 'exit' to leave.");
        String gameId;
        gameId = stdin.nextLine();
        if (gameId.equals("exit")) {
            return false;
        }
        try {
            socketOut.writeObject(new JoinRequestMove(this.myNickname, gameId));
            socketOut.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setActive(false);
        }
        return true;

    }

    private boolean createGame() { // returns true if the creation request was created successfully
        Integer playersNumber;
        while (true) {
            System.out.println("How many people do you want in your game? (max 4) Type 'exit' to leave.");
            try {
                try {
                    String request = stdin.nextLine();
                    if (request.equals("exit")) {
                        return false;
                    }
                    playersNumber = Integer.parseInt(request);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                if (playersNumber > 4) {
                    throw new Exception("Insert an integer number less than equal 4.");
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            socketOut.writeObject(new CreateRequestMove(playersNumber, this.myNickname));
            socketOut.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setActive(false);
        }
        return true;
    }

//    public Thread asyncCli() {// sends to server and shows the match
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (isActive()) {
//                        isLock();
//                        if (!getMatch().isEmpty()) {
//                            Request move = handleTurn();
//                            if (move != null) {
//                                socketOut.writeObject(move);
//                            }
//                            socketOut.flush();
//                        }
//                        setLock();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    setActive(false);
//                }
//            }
//        });
//        t.start();
//        return t;
//    }

//    public Move handleTurn() {
//        // quando do come comando 0 entro SEMPRE in una funzione che mi permette di
//        // visualizzare le varie informazioni
//
//        switch (this.match.getTurnPhase(myNickname)) {
//            case WaitPhase:
//                viewer();
//                break;
//            case InitialPhase:
//            case EndPhase:
//                return handleLeaderAction();
//            case MainPhase:
//                return handleMainPhase();
//
//        }
//
//        return null;
//    }






    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());
        stdin = new Scanner(System.in);
        jFrame = new JFrame();
        jFrame.setVisible(true);
        try {
            Thread t0 = asyncReadFromSocket();
            //Thread t1 = asyncCli();
            Thread t2 = buildGame();
            t0.join();
           // t1.join();
            t2.join();
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
