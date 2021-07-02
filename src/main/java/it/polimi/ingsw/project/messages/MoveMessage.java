package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.model.Match;

/**
 * message sent back to the player on each move completed after the game has started
 */
public class MoveMessage implements Serializable, ResponseMessage { // messaggio che rimando al player che viene poi
                                                                    // visualizzato
    private final Match match;
    private static final long serialVersionUID = 3840280592412192704L;

    public MoveMessage(Match match) {
        this.match = match;
    }

    @Override
    public void action(Client client) {
        client.setMatch(this.match);
    }

}
