package it.polimi.ingsw.project.messages;

import java.io.Serializable;

public class ConfirmJoinMessage implements Serializable {
    private String gameId;

    public String getGameId() {
        return gameId;
    }

    public ConfirmJoinMessage(String gameId) {
        this.gameId = gameId;
    }
}
