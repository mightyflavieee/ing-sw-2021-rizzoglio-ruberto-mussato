package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.client.gui.EndGameHandler;
import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;
import it.polimi.ingsw.project.messages.ResponseMessage;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.playermove.*;
import it.polimi.ingsw.project.model.playermove.interfaces.Request;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class ClientGUI extends Client implements Observer<Move> {

    private GUI gui;
    private JFrame jFrame;
    private boolean createGame; // true create, false join
    private int numPlayers;
    private NewGameHandler newGameHandler;
    private EndGameHandler endGameHandler;

    public ClientGUI(String ip, int port) {
        super(ip, port);
        this.numPlayers = 0;
    }

    public ClientGUI getInstance() {
        return this;
    }

    public Optional<GUI> getGui() {
        return Optional.ofNullable(gui);
    }

    public Optional<Match> getMatch() {
        return Optional.ofNullable(match);
    }

    @Override
    public void setMatch(Match match) {
        if(this.getGui().isEmpty()){
            this.jFrame.dispose();
            this.gui = new GUI(this, match, this.myNickname);
            this.gui.addObserver(this);
        }else {
            this.gui.setMatch(match);
        }
    }

    public void setCreateGame(boolean createGame) {
        this.createGame = createGame;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public void setGameId(String gameId) {
        this.gameId = gameId;
        this.newGameHandler.goToWaitingRoom(gameId);

        /*this.gameId = gameId;
        this.tempJFrame = new JFrame("Waiting room");
        JTextArea jTextArea = new JTextArea("Wait for the other players\nYour game ID is: " + gameId);
        jTextArea.setEditable(false);
        jTextArea.setVisible(true);
        this.tempJFrame.add(jTextArea);
        this.tempJFrame.setVisible(true);
        this.tempJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.tempJFrame.pack();*/
    }

    public void setLocalGameID(String gameId){
        this.gameId = gameId;
    }

    @Override
    public void reBuildGame(String errorMessage) {

        // todo change to fit new structure with NewGameHandler

        /*JFrame jFrame;
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
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);*/
        this.newGameHandler.goToSelectNickname();
    }

    @Override
    public void chooseLeaderCards(List<LeaderCard> possibleLeaderCards) {
        this.newGameHandler.goToLeaderCardChooser(possibleLeaderCards);
    }

    @Override
    public void reChooseLeaderCards(String errorMessage, List<LeaderCard> possibleLeaderCards) {
        //todo display error message

        this.newGameHandler.goToLeaderCardChooser(possibleLeaderCards);

        //this.chooseLeaderCards(possibleLeaderCards);
    }

    @Override
    public void showWaitMessageForOtherPlayers() {

        // todo change to fit new structure with NewGameHandler

//        this.jFrame = new JFrame();
//        this.jFrame.setTitle("Master of Renaissance");
//        JTextArea jTextArea = new JTextArea("Wait for the other players");
//        jTextArea.setEditable(false);
//        jTextArea.setVisible(true);
//        this.jFrame.add(jTextArea);
//        this.jFrame.setVisible(true);
//        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        this.jFrame.pack();
        this.newGameHandler.goToWaitingRoom(this.gameId);

    }

    @Override
    public void chooseResources(Integer numberOfResourcesToChoose) {
        this.newGameHandler.goToResourceSelector(numberOfResourcesToChoose);
    }

    public void send(Request request){
        try {
            this.socketOut.writeObject(request);
            this.socketOut.flush();
            this.socketOut.reset();
        } catch (IOException e) {
            e.printStackTrace();
            setActive(false);
        }
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

        this.jFrame = new JFrame();
        this.jFrame.setLocation(0,0);
        this.jFrame.setTitle("Master of Renaissance");
        this.jFrame.setVisible(true);
        this.jFrame.setLayout(new GridLayout(1,1));
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.newGameHandler = new NewGameHandler(this);
        this.jFrame.add(this.newGameHandler);
        this.jFrame.pack();

        /*JFrame jFrame;
        jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new GridLayout(13, 1));
        jFrame.add(new JLabel("What is your name?"));
        JTextField nicknameField = new JTextField();
        jFrame.add(nicknameField);
        JLabel numPlayerLabel = new JLabel("How many players?");
        jFrame.add(numPlayerLabel);
        JRadioButton oneRadioButton, twoRadioButton, threeRadioButton, fourRadioButton;
        oneRadioButton = new JRadioButton("1");
        twoRadioButton = new JRadioButton("2");
        threeRadioButton = new JRadioButton("3");
        fourRadioButton = new JRadioButton("4");
        jFrame.add(oneRadioButton);
        jFrame.add(twoRadioButton);
        jFrame.add(threeRadioButton);
        jFrame.add(fourRadioButton);
        JLabel createJoinLabel = new JLabel("Do you want to join or to create a new game?");
        jFrame.add(createJoinLabel);
        JRadioButton createRadioButton;
        createRadioButton = new JRadioButton("Create Game");
        jFrame.add(createRadioButton);
        JRadioButton joinRadioButton;
        joinRadioButton = new JRadioButton("Join Game");
        jFrame.add(joinRadioButton);
        JLabel matchIDLabel = new JLabel("ID:");
        matchIDLabel.setVisible(false);
        jFrame.add(matchIDLabel);
        JTextField idMatchField = new JTextField();
        idMatchField.setEnabled(false);
        idMatchField.setVisible(false);
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
        List<JComponent> previousButtons = new ArrayList<>(); //all the buttons before the submit
        previousButtons.add(nicknameField);
        previousButtons.add(createRadioButton);
        previousButtons.add(joinRadioButton);
        previousButtons.add(oneRadioButton);
        previousButtons.add(twoRadioButton);
        previousButtons.add(threeRadioButton);
        previousButtons.add(fourRadioButton);
        previousButtons.add(idMatchField);
        List<JComponent> successiveButtons = new ArrayList<>();//buttons after the nicknameField
        successiveButtons.add(createRadioButton);
        successiveButtons.add(joinRadioButton);
        successiveButtons.add(oneRadioButton);
        successiveButtons.add(twoRadioButton);
        successiveButtons.add(threeRadioButton);
        successiveButtons.add(fourRadioButton);
        successiveButtons.add(numPlayerLabel);
        successiveButtons.add(createJoinLabel);
        successiveButtons.forEach(x -> x.setVisible(false));
        submitButton = new JButton("Submit");
        submitButton.setVisible(false);
        jFrame.add(submitButton);
        submitButton.addActionListener(new SubmitButtonListener(getInstance(), jFrame, previousButtons));
        nicknameField.addActionListener(new InsertNameListener(getInstance(), nicknameField, successiveButtons));
        createRadioButton
                .addActionListener(new CreateButtonListener(getInstance(), createRadioButton, joinRadioButton,submitButton));
        idMatchField.addActionListener(new InsertMatchIdListener(getInstance(), idMatchField,submitButton));
        jFrame.pack();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);*/
    }

    public void createOrJoinGame() {
        if (this.createGame) {
            this.createGame();
        } else {
            this.joinGame();
        }
    }

    // sends JoinRequestMove, returns true if the joining request was created successfully
    private void joinGame() {
        this.send(new JoinRequestMove(this.myNickname, this.gameId));
    }

    // sends CreateRequestMove, returns true if the creation request was created successfully
    private void createGame() {
        this.send(new CreateRequestMove(this.numPlayers, this.myNickname));
    }

    public void endGame(boolean isSinglePlayer) {
        this.gui.getJFrame().dispose();
        this.endGameHandler = new EndGameHandler(this.match, this);
        this.jFrame = new JFrame();
        this.jFrame.setTitle("Master of Renaissance");
        this.jFrame.setVisible(true);
        this.jFrame.setLayout(new GridLayout(1,1));
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.jFrame.add(this.endGameHandler);
        this.jFrame.pack();
    }

    public void restart() {
        this.jFrame.dispose();
        buildGame();
    }

    /*// public Thread asyncCli() {// sends to server and shows the match
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
    // }*/

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

    @Override
    public void update(Move message) {
        this.send(message);
    }

    public void sendListOfChosenResources(List<ResourceType> resourceTypeList) {
        try {
            getSocketOut().writeObject(new ChooseResourcesMove(this.myNickname, this.gameId, resourceTypeList));
            getSocketOut().flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setActive(false);
        }
    }
}
