package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.playermove.GameRequestMove;
import it.polimi.ingsw.project.observer.Observer;
import it.polimi.ingsw.project.server.Server;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class CreateJoinGameObserver implements Observer<GameRequestMove> {
    private final Server server;
    private final SocketClientConnection connection;

    public CreateJoinGameObserver(Server server, SocketClientConnection connection) {
        this.server = server;
        this.connection = connection;
    }

    @Override
    public void update(GameRequestMove message) {
        message.createGameOrJoin(this.server, this.connection);
    }

}
