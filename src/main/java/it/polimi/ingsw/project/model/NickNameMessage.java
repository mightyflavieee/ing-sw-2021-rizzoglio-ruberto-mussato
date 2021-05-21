package it.polimi.ingsw.project.model;

import java.io.Serializable;

public class NickNameMessage implements Serializable { // messaggio che rimando al player che viene poi
    // visualizzato
    private final String message;

    public NickNameMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}