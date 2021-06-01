package it.polimi.ingsw.project.messages;


import it.polimi.ingsw.project.client.Client;

public class ErrorJoinMessage extends ErrorMessage implements ResponseMessage {

    public ErrorJoinMessage(String errorMessage) {
        setErrorMessage(errorMessage);
    }

    @Override
    public void action(Client client) {
        client.reBuildGame(super.messageError);
    }

}
