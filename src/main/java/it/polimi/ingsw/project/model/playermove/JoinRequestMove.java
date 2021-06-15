package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.messages.ErrorJoinMessage;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class JoinRequestMove extends GameRequestMove {
    private String nickName;
    private String gameId;

    public JoinRequestMove(String nickname, String gameId) {
        this.nickName = nickname;
        this.gameId = gameId;
    }

    @Override
    public void action(SocketClientConnection connection) {
        if (connection.getServer().isGameStarted(this.gameId)) {
            connection.getServer().handleReconnectionOnStartedGame(connection, this.gameId, this.nickName);
        } else if (connection.getServer().isGamePresent(this.gameId)) {
            connection.getServer().handleReconnectionOnNotStartedGame(connection, this.gameId, this.nickName);
        } else if (connection.getServer().doesGameExisted(this.gameId)) {
            connection.getServer().handleReconnectionAfterServerCrashed(connection, this.gameId, this.nickName);
        } else {
            connection.send(new ErrorJoinMessage(
                    "We are sorry but we couldn't find the game you are trying to join. Check the id!"));
        }
    }

    @Override
    public String toString() {
        return "Join Request Move, Nickname: " + this.nickName + ", Game ID: " + this.gameId;
    }
}
