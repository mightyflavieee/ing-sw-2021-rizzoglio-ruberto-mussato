package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.model.playermove.interfaces.Request;
import it.polimi.ingsw.project.server.SocketClientConnection;

/**
 * it is the parent class of all the GameRequestMove sent by the player at the beginning of the client
 */
public class GameRequestMove implements Serializable, Request {
    private static final long serialVersionUID = 3840280592434342704L;

    @Override
    public void action(SocketClientConnection connection) {

    }

    @Override
    public String toString() {
        return "Generic Game Request Move";
    }

}
