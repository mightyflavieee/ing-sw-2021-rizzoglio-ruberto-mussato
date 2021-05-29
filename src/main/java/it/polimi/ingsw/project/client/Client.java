package it.polimi.ingsw.project.client;

import java.util.Optional;

import it.polimi.ingsw.project.model.Match;

public class Client {

    private String gameId;
    private boolean active = true;
    private Match match;
    private String myNickname; // da inizializzare
    private boolean lock = true;

    public synchronized void isLock() throws InterruptedException {
        if (this.lock) {
            wait();
        }
    }

    public Client getInstance() {
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

    public void buildGame() {

    }
}
