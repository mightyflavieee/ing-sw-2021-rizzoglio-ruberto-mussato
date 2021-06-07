package it.polimi.ingsw.project.model.playermove.interfaces;

import it.polimi.ingsw.project.server.SocketClientConnection;

public interface Request {
    public void action(SocketClientConnection connection);

    public String toString();

}
