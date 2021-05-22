package it.polimi.ingsw.project.messages;

import java.io.Serializable;

public class CreateGameMessage implements Serializable { // messaggio che rimando al player che viene poi
                                                         // visualizzato
    private Integer numberOfPlayers;
    private String nickName;

    public CreateGameMessage(Integer numberOfPlayers, String nickName) {
        this.numberOfPlayers = numberOfPlayers;
        this.nickName = nickName;
    }

    public Integer getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    public String getNickName() {
        return this.nickName;
    }
}
