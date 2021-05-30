package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.client.gui.listeners.*;
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
import java.util.List;

public class ClientGUI extends Client{

    private String ip;
    private String gameId;
    private int port;
    private boolean active = true;
    private Match match;
    private Scanner stdin;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    private String myNickname; // da inizializzare
    private boolean lock ;

    private boolean createGame; //true create, false join
    private int numPlayers;

    public ClientGUI(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.match = null;
        this.lock = true;
        this.numPlayers = 0;
        this.myNickname = "";
        this.gameId = "";
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

    public void setCreateGame(boolean createGame) {
        this.createGame = createGame;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
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

    public void buildGame() {

                JFrame jFrame;
                jFrame = new JFrame();
                jFrame.setVisible(true);
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                JPanel jPanel;
                jPanel = new JPanel();
                jPanel.setLayout(new GridLayout(13,1));
                jPanel.add(new JLabel("What is your name?"));
                JTextField nicknameField = new JTextField();
                nicknameField.addActionListener(new InsertNameListener(getInstance(),nicknameField));
                jPanel.add(nicknameField);
                jPanel.add(new JLabel("How many players?"));
                JRadioButton oneRadioButton, twoRadioButton, threeRadioButton, fourRadioButton;
                oneRadioButton = new JRadioButton("1");
                twoRadioButton = new JRadioButton("2");
                threeRadioButton = new JRadioButton("3");
                fourRadioButton = new JRadioButton("4");
                jPanel.add(oneRadioButton);
                jPanel.add(twoRadioButton);
                jPanel.add(threeRadioButton);
                jPanel.add(fourRadioButton);
                jPanel.add(new JLabel("Do you want to join or to create a new game?"));
                JRadioButton createRadioButton;
                createRadioButton = new JRadioButton("Create Game");
                jPanel.add(createRadioButton);
                JRadioButton joinRadioButton;
                joinRadioButton = new JRadioButton("Join Game");
                jPanel.add(joinRadioButton);
                createRadioButton.addActionListener(new CreateButtonListener(getInstance(),createRadioButton,joinRadioButton));
                JLabel matchIDLabel = new JLabel("ID:");
                matchIDLabel.setVisible(false);
                jPanel.add(matchIDLabel);
                JTextField idMatchField = new JTextField();
                idMatchField.setEnabled(false);
                idMatchField.setVisible(false);
                idMatchField.addActionListener(new InsertMatchIdListener(getInstance(),idMatchField));
                jPanel.add(idMatchField);
                joinRadioButton.addActionListener(new JoinButtonListener(getInstance(),createRadioButton,joinRadioButton,idMatchField, matchIDLabel ));

                oneRadioButton.addActionListener(new OneRadioButtonListener(getInstance(),oneRadioButton,twoRadioButton,threeRadioButton,fourRadioButton));
                twoRadioButton.addActionListener(new TwoRadioButtonListener(getInstance(),oneRadioButton,twoRadioButton,threeRadioButton,fourRadioButton));
                threeRadioButton.addActionListener(new ThreeRadioButtonListener(getInstance(),oneRadioButton,twoRadioButton,threeRadioButton,fourRadioButton));
                fourRadioButton.addActionListener(new FourRadioButtonListener(getInstance(),oneRadioButton,twoRadioButton,threeRadioButton,fourRadioButton));
                JButton submitButton;
                List<JComponent> previousButtons = new ArrayList<>();
                previousButtons.add(nicknameField);
                previousButtons.add(createRadioButton);
                previousButtons.add(joinRadioButton);
                previousButtons.add(oneRadioButton);
                previousButtons.add(twoRadioButton);
                previousButtons.add(threeRadioButton);
                previousButtons.add(fourRadioButton);
                previousButtons.add(idMatchField);
                submitButton = new JButton("Submit");
                jPanel.add(submitButton);
                submitButton.addActionListener(new SubmitButtonListener(getInstance(),jFrame,previousButtons));
                jFrame.add(jPanel);
                jFrame.pack();
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
    public void createOrJoinGame() {
        if(this.createGame){
            this.createGame();
        }else{
            this.joinGame();
        }
    }
    private void joinGame() { // returns true if the joining request was created successfully

        try {
            socketOut.writeObject(new JoinRequestMove(this.myNickname, this.gameId));
            socketOut.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setActive(false);
        }

    }

    private void createGame() { // returns true if the creation request was created successfully

        try {
            socketOut.writeObject(new CreateRequestMove(this.numPlayers, this.myNickname));
            socketOut.flush();
            socketOut.reset();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setActive(false);
        }
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

        try {
            Thread t0 = asyncReadFromSocket();
            //Thread t1 = asyncCli();
            this.buildGame();
            t0.join();
           // t1.join();

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
