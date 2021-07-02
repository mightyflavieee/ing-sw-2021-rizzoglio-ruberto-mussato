package it.polimi.ingsw.project.messages;

import it.polimi.ingsw.project.client.Client;

/**
 * interface for all messages from server to the client
 */
public interface ResponseMessage {
    void action(Client client);
}
