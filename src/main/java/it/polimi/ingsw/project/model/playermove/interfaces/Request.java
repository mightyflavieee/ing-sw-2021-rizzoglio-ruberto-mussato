package it.polimi.ingsw.project.model.playermove.interfaces;

import it.polimi.ingsw.project.server.SocketClientConnection;

public interface Request {
    void action(SocketClientConnection connection);

    String toString();

}
