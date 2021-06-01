package it.polimi.ingsw.project.messages;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

    protected String messageError = "Generic Error";

    public void setErrorMessage(String errorMessage) {
        if (errorMessage != null) {
            this.messageError = errorMessage;
        }
    }
}
