package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.client.gui.EndGameHandler;
import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;
import it.polimi.ingsw.project.messages.ChooseResourcesMove;
import it.polimi.ingsw.project.messages.CreateRequestMove;
import it.polimi.ingsw.project.messages.JoinRequestMove;
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

    public ClientGUI(String ip, int port) {
        super(ip, port);
        this.numPlayers = 0;
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();

        }
    }

    private ClientGUI getInstance() {
        return this;
    }

    public Optional<GUI> getGui() {
        return Optional.ofNullable(gui);
    }

    public Optional<Match> getMatch() {
        return Optional.ofNullable(match);
    }

    /**
     * creates a new gui if it is the first time that the match is received.
     * if not updates the gui
     */
    @Override
    public void setMatch(Match match) {
        this.match = match.clone();
        if(!this.getGui().isPresent()){
            this.jFrame.dispose();
            this.gui = new GUI(this, match, this.myNickname);
            this.gui.addObserver(this);
        }else {
            this.gui.setMatch(match);
        }
    }

    /**
     * used at the beginning of the game as a buffer for the first message sent to the server
     */
    public void setCreateGame(boolean createGame) {
        this.createGame = createGame;
    }

    /**
     * used at the beginning of the game as a buffer for the first message sent to the server
     */
    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * receives the game id from the server and goes to the waiting room displaying it
     */
    @Override
    public void setGameId(String gameId) {
        this.gameId = gameId;
        this.newGameHandler.goToWaitingRoom(gameId);

    }

    /**
     * goes again to the initial logic phase
     * it is used if there was an error during the communication
     */
    @Override
    public void reBuildGame(String errorMessage) {

        this.newGameHandler.goToSelectNickname();
    }

    /**
     * shows the available leadercards
     * it is used at the beginning of the game
     */
    @Override
    public void chooseLeaderCards(List<LeaderCard> possibleLeaderCards) {
        this.newGameHandler.goToLeaderCardChooser(possibleLeaderCards);
    }

    /**
     * shows again the available leadercards
     * it is used if the there was an error during the communication
     */
    @Override
    public void reChooseLeaderCards(String errorMessage, List<LeaderCard> possibleLeaderCards) {

        this.newGameHandler.goToLeaderCardChooser(possibleLeaderCards);
    }

    @Override
    public void showWaitMessageForOtherPlayers() {

        this.newGameHandler.goToWaitingRoom(this.gameId);

    }

    /**
     * shows the menu for the selection of the resources at the beginning of the game
     */
    @Override
    public void chooseResources(Integer numberOfResourcesToChoose) {
        this.newGameHandler.goToResourceSelector(numberOfResourcesToChoose);
    }

    /**
     * sends a message to the server
     */
    public void send(Request request){
        try {
            this.socketOut.reset();
            this.socketOut.writeObject(request);
            this.socketOut.flush();
            this.socketOut.reset();
        } catch (IOException e) {
            e.printStackTrace();
            setActive(false);
        }
    }

    private Thread asyncReadFromSocket() {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    ResponseMessage inputObject = (ResponseMessage) socketIn.readObject();
                    inputObject.action(getInstance());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                setActive(false);
            }
        });
        t.start();
        return t;

    }

    private void buildGame() {

        this.jFrame = new JFrame();
        jFrame.setLocation(600,100);
        this.jFrame.setTitle("Master of Renaissance");
        this.jFrame.setVisible(true);
        this.jFrame.setLayout(new GridLayout(1,1));
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.newGameHandler = new NewGameHandler(this);
        this.jFrame.add(this.newGameHandler);

        this.jFrame.setPreferredSize(new Dimension(400,680));
        this.jFrame.pack();

    }

    /**
     * sends a join request or a create request to the server based on the local variable that was previous set
     */
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

    /**
     * shows the scoreboard at the end of the game
     */
    public void endGame() {
        this.gui.getJFrame().dispose();
        EndGameHandler endGameHandler = new EndGameHandler(this.match, this);
        this.jFrame = new JFrame();
        this.jFrame.setTitle("Master of Renaissance");
        this.jFrame.setVisible(true);
        this.jFrame.setLayout(new GridLayout(1,1));
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.jFrame.add(endGameHandler);
        this.jFrame.pack();
        jFrame.setLocation(600,100);
    }

    /**
     * makes the game and the login to start again from the beginning
     * it is used at the of the game to start a new one
     */
    public void restart() {
        this.jFrame.dispose();
        buildGame();
    }


    /**
     * connects to the server, starts the thread to receive message from the server and starts the login process
     */
    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());

        try {
            Thread t0 = asyncReadFromSocket();
            this.buildGame();
            t0.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    /**
     * calls the send method
     */
    @Override
    public void update(Move message) {
        this.send(message);
    }

    /**
     * send the list of chosen resources at the beginning of the game
     */
    public void sendListOfChosenResources(List<ResourceType> resourceTypeList) {
        send(new ChooseResourcesMove(this.myNickname, this.gameId, resourceTypeList) );
    }
}
