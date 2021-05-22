package it.polimi.ingsw.project.messages;

import java.io.Serializable;

public class ErrorJoinMessage implements Serializable{
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorJoinMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
