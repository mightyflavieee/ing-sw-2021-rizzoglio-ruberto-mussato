package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.interfaces.Controllable;
import it.polimi.ingsw.project.model.playermove.interfaces.Request;
import it.polimi.ingsw.project.server.SocketClientConnection;
import it.polimi.ingsw.project.view.View;

import java.io.Serializable;

//messaggio ricevuto dal server
public class Move implements Serializable, Request, Controllable {
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

    @Override
    public void notifyMoveToController(Player player, View view, Controllable requestedMove) {
        view.notify(new PlayerMove(player, view, (Move) requestedMove));
    }

}