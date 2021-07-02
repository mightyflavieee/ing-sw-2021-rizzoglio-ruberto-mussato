package it.polimi.ingsw.project.messages;

import java.io.Serializable;

/**
 * it is the parent class of all the error messages
 */
public class ErrorMessage implements Serializable {
    private static final long serialVersionUID = 3840280511175092704L;

    protected String messageError = "Generic Error";

    public void setErrorMessage(String errorMessage) {
        if (errorMessage != null) {
            this.messageError = errorMessage;
        }
    }
}
