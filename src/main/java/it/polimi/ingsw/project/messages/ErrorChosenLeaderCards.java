package it.polimi.ingsw.project.messages;

import it.polimi.ingsw.project.client.Client;

public class ErrorChosenLeaderCards extends ErrorMessage implements ResponseMessage {

    public ErrorChosenLeaderCards(String errorMessage) {
        setErrorMessage(errorMessage);
    }

    @Override
    public void action(Client client) {
        client.reChooseLeaderCards(getErrorMessage());
    }

}
