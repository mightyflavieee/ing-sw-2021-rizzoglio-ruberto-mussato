package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.server.SocketClientConnection;

public interface Request {
    public void action(SocketClientConnection connection);
}
