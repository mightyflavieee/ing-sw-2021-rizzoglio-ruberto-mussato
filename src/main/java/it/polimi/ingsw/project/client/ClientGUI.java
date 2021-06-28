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

    }

    @Override
    public void reBuildGame(String errorMessage) {

        // todo change to fit new structure with NewGameHandler

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
    }

    @Override
    public void showWaitMessageForOtherPlayers() {

        // todo change to fit new structure with NewGameHandler

        this.newGameHandler.goToWaitingRoom(this.gameId);

    }

    @Override
    public void chooseResources(Integer numberOfResourcesToChoose) {
        this.newGameHandler.goToResourceSelector(numberOfResourcesToChoose);
    }

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

    public Thread asyncReadFromSocket() {
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
        //this.jFrame.pack();

        this.jFrame.setPreferredSize(new Dimension(400,680));
        this.jFrame.pack();

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
