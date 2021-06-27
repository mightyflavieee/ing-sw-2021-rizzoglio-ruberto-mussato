package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;

public class ConfirmJoinMessage implements Serializable, ResponseMessage {
    private static final long serialVersionUID = 3840280592455322704L;
    private final String gameId;

    public ConfirmJoinMessage(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public void action(Client client) {
        client.setGameId(gameId);
    }

}
