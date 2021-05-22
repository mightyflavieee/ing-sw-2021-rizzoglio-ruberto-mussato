package it.polimi.ingsw.project.messages;

import java.io.Serializable;

public class JoinGameMessage implements Serializable {
    private String nickName;
    private String gameId;

    public JoinGameMessage(String gameId, String nickName) {
        this.gameId = gameId;
        this.nickName = nickName;
    }

    public String getGameId() {
        return this.gameId;
    }

    public String getNickName() {
        return this.nickName;
    }
}
