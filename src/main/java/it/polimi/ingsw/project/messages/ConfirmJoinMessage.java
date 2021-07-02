package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;

public class ConfirmJoinMessage implements Serializable, ResponseMessage {
    private static final long serialVersionUID = 3840280592455322704L;
    private final String gameId;

    /**
     * it is sent back by the server when a the player join or create a game
     * @param gameId the gameId generated
     */
    public ConfirmJoinMessage(String gameId) {
        this.gameId = gameId;
    }

    /**
     * @param client the client where we need to send the gameId
     */
    @Override
    public void action(Client client) {
        client.setGameId(gameId);
    }

}
