package it.polimi.ingsw.project.messages;

public class ErrorMessage {

    protected String errorMessage = "Generic Error";

    public void setErrorMessage(String errorMessage) {
        if (errorMessage != null) {
            this.errorMessage = errorMessage;
        }
    }
}
