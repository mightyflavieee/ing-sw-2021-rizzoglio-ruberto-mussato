package it.polimi.ingsw.project.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

public abstract class Client {

    protected final String ip;
    protected final int port;
    protected String gameId;
    private boolean active = true;
    protected Match match;
    protected String myNickname;
    protected ObjectOutputStream socketOut;
    protected ObjectInputStream socketIn;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.match = null;
        this.myNickname = "";
        this.gameId = "";
    }

    public void setSocketOut(ObjectOutputStream socketOut) {
        this.socketOut = socketOut;
    }

    public void setSocketIn(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    public ObjectOutputStream getSocketOut() {
        return this.socketOut;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    public abstract void setMatch(Match match);

    public void setNickname(String name) {
        this.myNickname = name;
    }

    public String getNickname() {
        return this.myNickname;
    }

    public Optional<Match> getMatch() {
        return Optional.ofNullable(match);
    }

    public synchronized boolean isActive() {
        return this.active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public abstract void setGameId(String gameId);

    public String getGameId() {
        return this.gameId;
    }

    public abstract void reBuildGame(String errorMessage); // todo la stringa è sempre uguale?

    public abstract void chooseLeaderCards(List<LeaderCard> possibleLeaderCards);

    public abstract void reChooseLeaderCards(String errorMessage, List<LeaderCard> possibleLeaderCards); // todo la
                                                                                                         // stringa è
                                                                                                         // sempre
                                                                                                         // uguale?

    public abstract void showWaitMessageForOtherPlayers();

    public abstract void chooseResources(Integer numberOfResourcesToChoose);

    public abstract void run() throws IOException;
}
