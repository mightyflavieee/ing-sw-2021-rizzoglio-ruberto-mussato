package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.messages.ErrorJoinMessage;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class JoinAfterDisconnectionRequestMove extends GameRequestMove {
    private String nickName;
    private String gameId;

    public JoinAfterDisconnectionRequestMove(String nickname, String gameId) {
        this.nickName = nickname;
        this.gameId = gameId;
    }

    @Override
    public void action(SocketClientConnection connection) {
        if (connection.getServer().doesGameExistedAndHasNotRestarted(this.gameId)) {
            if (connection.getServer().isGameStarted(this.gameId)) {
                if (connection.getServer().isPlayerPresentAndDisconnected(this.gameId, this.nickName)) {
                    connection.getServer().rejoinGame(this.gameId, connection, this.nickName);
                    if (connection.getServer().isRestartedGameReadyToStart(this.gameId)) {
                        connection.getServer().sendToAllPlayersMoveMessage(this.gameId);
                    } else {
                        connection.getServer().sendWaitMessageToPlayer(this.gameId, this.nickName);
                    }
                } else {
                    connection.send(new ErrorJoinMessage(
                            "We are sorry but in this game you are not a player or there is already a player with this name but it is connected! Try another nickname."));
                }
            } else {
                connection.getServer().recreateLobby(this.gameId);
                connection.getServer().rejoinGame(this.gameId, connection, this.nickName);
                if (connection.getServer().isRestartedGameReadyToStart(this.gameId)) {
                    connection.getServer().sendToAllPlayersMoveMessage(this.gameId);
                } else {
                    connection.getServer().sendWaitMessageToPlayer(this.gameId, this.nickName);
                }
            }
        } else {
            connection.send(new ErrorJoinMessage(
                    "We are sorry but we couldn't find the game you are trying to join. Check the id!"));
        }

    }

    @Override
    public String toString() {
        return "Join After Disconnection Request Move, Nickname: " + this.nickName + ", Game ID: " + this.gameId;
    }

}
