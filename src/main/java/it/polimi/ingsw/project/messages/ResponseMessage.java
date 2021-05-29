package it.polimi.ingsw.project.messages;

import it.polimi.ingsw.project.client.Client;

public interface ResponseMessage {
    public void action(Client client);
}
