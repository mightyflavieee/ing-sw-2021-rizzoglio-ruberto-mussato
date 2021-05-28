package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.ClientCLI;

public class ErrorJoinMessage implements Serializable, ResponseMessage {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorJoinMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void action(ClientCLI client) {
        System.out.println(this.errorMessage);
        Thread t0 = client.buildGame();
        t0.run();
    }

}
