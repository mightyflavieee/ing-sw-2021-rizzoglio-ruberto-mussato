package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;

public class ResourcesToChooseMessage implements ResponseMessage, Serializable {

    private final Integer numberOfResourcesToChoose;
    private static final long serialVersionUID = 3840280592475092111L;

    /**
     * it construct the move for the resources to choose
     * @param numberOfResourcesToChoose it is the number of the resources to choose
     */
    public ResourcesToChooseMessage(Integer numberOfResourcesToChoose) {
        this.numberOfResourcesToChoose = numberOfResourcesToChoose;
    }

    /**
     * it calls the function on the client to handle the selection of the resources of the beginning of the match
     * @param client it is needed to call the method to choose the resources
     */
    @Override
    public void action(Client client) {
        client.chooseResources(numberOfResourcesToChoose);
    }

}
