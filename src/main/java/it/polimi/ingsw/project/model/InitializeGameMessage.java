package it.polimi.ingsw.project.model;

import java.io.Serializable;

public class InitializeGameMessage implements Serializable { // messaggio che rimando al player che viene poi
                                                             // visualizzato
    private final String message;

    public InitializeGameMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
