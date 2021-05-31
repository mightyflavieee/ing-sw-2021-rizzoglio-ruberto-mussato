package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.LeaderCardChoserGUI;
import it.polimi.ingsw.project.client.gui.listeners.*;
import it.polimi.ingsw.project.messages.ResponseMessage;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.playermove.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ClientGUI extends Client {

    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;
    private GUI gui;

    private boolean createGame; // true create, false join
    private int numPlayers;

    public ClientGUI(String ip, int port) {
        super(ip, port);
        this.numPlayers = 0;

    }

    public ClientGUI getInstance() {
        return this;
    }

    @Override
    public void setMatch(Match match) {
        gui.setMatch(match, this.myNickname);
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

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public void reBuildGame(String errorMessage) {
        JFrame jFrame;
        jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridLayout(2, 1));
        JLabel matchIDLabel = new JLabel("Wrong ID! Insert a right ID:");
        jFrame.add(matchIDLabel);
        JTextField idMatchField = new JTextField();
        idMatchField.addActionListener(new ReInsertMatchIdListener(getInstance(), idMatchField, jFrame));
        jFrame.add(idMatchField);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void chooseLeaderCards(List<LeaderCard> possibleLeaderCards) {
        new LeaderCardChoserGUI(possibleLeaderCards.stream().map(LeaderCard::getId).collect(Collectors.toList()), this);

    }

    @Override
    public void reChooseLeaderCards(String errorMessage, List<LeaderCard> possibleLeaderCards) {

    }

    @Override
    public void showWaitMessageForOtherPlayers() {
    }

    public Thread asyncReadFromSocket() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        ResponseMessage inputObject = (ResponseMessage) socketIn.readObject();
                        inputObject.action(getInstance());
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

    private void buildGame() {

        JFrame jFrame;
        jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridLayout(13, 1));
        jFrame.add(new JLabel("What is your name?"));
        JTextField nicknameField = new JTextField();
        nicknameField.addActionListener(new InsertNameListener(getInstance(), nicknameField));
        jFrame.add(nicknameField);
        jFrame.add(new JLabel("How many players?"));
        JRadioButton oneRadioButton, twoRadioButton, threeRadioButton, fourRadioButton;
        oneRadioButton = new JRadioButton("1");
        twoRadioButton = new JRadioButton("2");
        threeRadioButton = new JRadioButton("3");
        fourRadioButton = new JRadioButton("4");
        jFrame.add(oneRadioButton);
        jFrame.add(twoRadioButton);
        jFrame.add(threeRadioButton);
        jFrame.add(fourRadioButton);
        jFrame.add(new JLabel("Do you want to join or to create a new game?"));
        JRadioButton createRadioButton;
        createRadioButton = new JRadioButton("Create Game");
        jFrame.add(createRadioButton);
        JRadioButton joinRadioButton;
        joinRadioButton = new JRadioButton("Join Game");
        jFrame.add(joinRadioButton);
        createRadioButton
                .addActionListener(new CreateButtonListener(getInstance(), createRadioButton, joinRadioButton));
        JLabel matchIDLabel = new JLabel("ID:");
        matchIDLabel.setVisible(false);
        jFrame.add(matchIDLabel);
        JTextField idMatchField = new JTextField();
        idMatchField.setEnabled(false);
        idMatchField.setVisible(false);
        idMatchField.addActionListener(new InsertMatchIdListener(getInstance(), idMatchField));
        jFrame.add(idMatchField);
        joinRadioButton.addActionListener(
                new JoinButtonListener(getInstance(), createRadioButton, joinRadioButton, idMatchField, matchIDLabel));

        oneRadioButton.addActionListener(new OneRadioButtonListener(getInstance(), oneRadioButton, twoRadioButton,
                threeRadioButton, fourRadioButton));
        twoRadioButton.addActionListener(new TwoRadioButtonListener(getInstance(), oneRadioButton, twoRadioButton,
                threeRadioButton, fourRadioButton));
        threeRadioButton.addActionListener(new ThreeRadioButtonListener(getInstance(), oneRadioButton, twoRadioButton,
                threeRadioButton, fourRadioButton));
        fourRadioButton.addActionListener(new FourRadioButtonListener(getInstance(), oneRadioButton, twoRadioButton,
                threeRadioButton, fourRadioButton));
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
        jFrame.add(submitButton);
        submitButton.addActionListener(new SubmitButtonListener(getInstance(), jFrame, previousButtons));
        jFrame.pack();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // while (true) {
        // System.out.println("What do you want to 'join' or 'create' a game?");
        // String request = stdin.nextLine();
        // boolean wasValid = false;
        // if (request.equals("join")) {
        // wasValid = joinGame();
        // } else if (request.equals("create")) {
        // wasValid = createGame();
        // } else {
        // System.out.println("Request not valid!");
        // }
        // if (wasValid) {
        // break;
        // }
        // }

    }

    public void createOrJoinGame() {
        if (this.createGame) {
            this.createGame();
        } else {
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

    // public Thread asyncCli() {// sends to server and shows the match
    // Thread t = new Thread(new Runnable() {
    // @Override
    // public void run() {
    // try {
    // while (isActive()) {
    // isLock();
    // if (!getMatch().isEmpty()) {
    // Request move = handleTurn();
    // if (move != null) {
    // socketOut.writeObject(move);
    // }
    // socketOut.flush();
    // }
    // setLock();
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // setActive(false);
    // }
    // }
    // });
    // t.start();
    // return t;
    // }

    // public Move handleTurn() {
    // // quando do come comando 0 entro SEMPRE in una funzione che mi permette di
    // // visualizzare le varie informazioni
    //
    // switch (this.match.getTurnPhase(myNickname)) {
    // case WaitPhase:
    // viewer();
    // break;
    // case InitialPhase:
    // case EndPhase:
    // return handleLeaderAction();
    // case MainPhase:
    // return handleMainPhase();
    //
    // }
    //
    // return null;
    // }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());

        try {
            Thread t0 = asyncReadFromSocket();
            // Thread t1 = asyncCli();
            this.buildGame();
            t0.join();
            // t1.join();

        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}
