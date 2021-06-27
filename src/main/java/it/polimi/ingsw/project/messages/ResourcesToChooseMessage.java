package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;

public class ResourcesToChooseMessage implements ResponseMessage, Serializable {

    private final Integer numberOfResourcesToChoose;
    private static final long serialVersionUID = 3840280592475092111L;

    public ResourcesToChooseMessage(Integer numberOfResourcesToChoose) {
        this.numberOfResourcesToChoose = numberOfResourcesToChoose;
    }

    @Override
    public void action(Client client) {
        client.chooseResources(numberOfResourcesToChoose);
    }

}
