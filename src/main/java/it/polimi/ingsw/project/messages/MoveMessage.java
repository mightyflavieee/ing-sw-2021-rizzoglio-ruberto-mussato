package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.model.Match;

public class MoveMessage implements Serializable, ResponseMessage { // messaggio che rimando al player che viene poi
                                                                    // visualizzato
    private final Match match;

    public MoveMessage(Match match) {
        this.match = match;
    }

    @Override
    public void action(Client client) {
        client.setMatch(this.match);
    }

}
