package it.polimi.ingsw.project.messages;

import it.polimi.ingsw.project.client.ClientCLI;

public interface ResponseMessage {
    public void action(ClientCLI cli);
}
