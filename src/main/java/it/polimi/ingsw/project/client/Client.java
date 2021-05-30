package it.polimi.ingsw.project.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

public abstract class Client {

    private String ip;
    private int port;
    private String gameId;
    private boolean active = true;
    private Match match;
    private String myNickname;
    private ObjectOutputStream socketOut;
    private ObjectInputStream socketIn;

    public void setSocketOut(ObjectOutputStream socketOut) {
        this.socketOut = socketOut;
    }

    public void setSocketIn(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    public ObjectInputStream getSocketIn() {
        return this.socketIn;
    }

    public ObjectOutputStream getSocketOut() {
        return this.socketOut;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    public Client getInstance() {
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

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

    public abstract void reBuildGame(String errorMessage);

    public abstract void chooseLeaderCards(List<LeaderCard> possibleLeaderCards);

    public abstract void reChooseLeaderCards(String errorMessage);
}
