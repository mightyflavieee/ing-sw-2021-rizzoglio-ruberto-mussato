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
                        "We are sorry but the game you are trying to join is full! Try a different one."));
            }
        } else {
            connection.send(new ErrorJoinMessage(
                    "We are sorry but we couldn't find the game you are trying to join. Check the id!"));
        }
    }
    @Override
    public String toString(){
        return "Join Request Move, Nickname: " + this.nickName + ", Game ID: " + this.gameId;
    }
}
