package it.polimi.ingsw.project.model.playermove;

import java.io.Serializable;

import it.polimi.ingsw.project.observer.ObservableSocket;
import it.polimi.ingsw.project.server.Server;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class GameRequestMove extends ObservableSocket<GameRequestMove> implements Serializable {

    public void createGameOrJoin(Server server, SocketClientConnection connection) {
    }

}
