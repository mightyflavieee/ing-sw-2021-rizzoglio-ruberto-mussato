package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.messages.ConfirmJoinMessage;
import it.polimi.ingsw.project.messages.ErrorJoinMessage;
import it.polimi.ingsw.project.server.Server;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class CreateRequestMove extends GameRequestMove {
    private Integer numberOfPlayers;
    private String nickName;

    public CreateRequestMove(Integer numberOfPlayers, String nickname) {
        this.numberOfPlayers = numberOfPlayers;
        this.nickName = nickname;
    }

    @Override
    public void createGameOrJoin(Server server, SocketClientConnection connection) {
        String gameId = server.createGame(this.numberOfPlayers);
        try {
            server.addToLobby(gameId, connection, this.nickName);
            connection.send(new ConfirmJoinMessage(gameId));
            if (server.tryToStartGame(gameId)) {
                server.startGame(gameId);
            }
        } catch (Exception e) {
            connection.send(new ErrorJoinMessage(e.getMessage()));
        }
    }
}
