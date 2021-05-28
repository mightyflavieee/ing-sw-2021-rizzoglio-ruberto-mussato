package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.ClientCLI;

public class ConfirmJoinMessage implements Serializable, ResponseMessage {
    private String gameId;

    public String getGameId() {
        return gameId;
    }

    public ConfirmJoinMessage(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public void action(ClientCLI client) {
        client.setGameId(gameId);
        System.out.println("Your gameid is: " + this.gameId);
        client.unLock();
    }

}
