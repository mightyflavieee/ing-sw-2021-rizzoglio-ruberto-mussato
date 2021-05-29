package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;

public class ErrorJoinMessage extends ErrorMessage implements Serializable, ResponseMessage {

    public ErrorJoinMessage(String errorMessage) {
        setErrorMessage(errorMessage);
    }

    @Override
    public void action(Client client) {
        client.reBuildGame(getErrorMessage());
    }

}
