package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.server.SocketClientConnection;

import java.io.Serializable;

//messaggio ricevuto dal server
public class Move implements Serializable, Request {
    public boolean isFeasibleMove(Match match) {
        // TODO
        return true;
    }

    public void performMove(Match match) {
        // TODO
    }

    public String toString() {
        return "Generic Move";
    }

    public boolean isMainMove() {
        return true;
    }

    @Override
    public void action(SocketClientConnection connection) {
        connection.notify(this);
    }

}