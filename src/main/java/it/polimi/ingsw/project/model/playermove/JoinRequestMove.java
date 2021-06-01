package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.messages.ConfirmJoinMessage;
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
        if (connection.getServer().isGamePresent(this.gameId)) {
            if (connection.getServer().isGameNotFull(this.gameId)) {
                if (connection.getServer().isNicknameUnique(this.gameId, this.nickName)) {
                    try {
                        connection.getServer().addToLobby(this.gameId, connection, this.nickName);
                        connection.send(new ConfirmJoinMessage(this.gameId));
                        if (connection.getServer().tryToStartGame(this.gameId)) {
                            connection.getServer().sendChooseLeaderCards(this.gameId);
                        }
                    } catch (Exception e) {
                        connection.send(new ErrorJoinMessage(e.getMessage()));
                    }
                } else {
                    connection.send(new ErrorJoinMessage(
                            "We are sorry but there is already a player with this nickname! Try a different one."));
                }
            } else {
                connection.send(new ErrorJoinMessage(
                        "We are sorry but the game you are trying to join is full! Try a different one."));
            }
        } else if (connection.getServer().isGameStarted(this.gameId)) {
            if (connection.getServer().isPlayerPresentAndDisconnected(this.gameId, this.nickName)) {
                connection.getServer().rejoinGame(this.gameId, connection, this.nickName);
                connection.getServer().sendWaitMessageToPlayer(this.gameId, this.nickName);
            } else {
                connection.send(new ErrorJoinMessage(
                        "We are sorry but this game is already started and you are not a player of this game or there is already a player with this name but it is connected! Try another nickname."));
            }
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
