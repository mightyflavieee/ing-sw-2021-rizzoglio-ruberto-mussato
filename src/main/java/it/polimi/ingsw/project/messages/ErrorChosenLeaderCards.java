package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;

public class ErrorChosenLeaderCards extends ErrorMessage implements ResponseMessage, Serializable {

    public ErrorChosenLeaderCards(String errorMessage) {
        setErrorMessage(errorMessage);
    }

    @Override
    public void action(Client client) {
        client.reChooseLeaderCards(getErrorMessage());
    }

}
