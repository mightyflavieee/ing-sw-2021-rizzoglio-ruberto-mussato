package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.messages.ConfirmJoinMessage;
import it.polimi.ingsw.project.messages.ErrorJoinMessage;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class CreateRequestMove extends GameRequestMove {
    private Integer numberOfPlayers;
    private String nickName;

    public CreateRequestMove(Integer numberOfPlayers, String nickname) {
        this.numberOfPlayers = numberOfPlayers;
        this.nickName = nickname;
    }

    @Override
    public void action(SocketClientConnection connection) {
        String gameId = connection.getServer().createGame(this.numberOfPlayers);
        try {
            connection.getServer().addToLobby(gameId, connection, this.nickName);
            connection.send(new ConfirmJoinMessage(gameId));
            if (connection.getServer().tryToStartGame(gameId)) {
                connection.getServer().startGame(gameId);
            }
        } catch (Exception e) {
            connection.send(new ErrorJoinMessage(e.getMessage()));
        }
    }
}
