package it.polimi.ingsw.project.client.gui.market;

import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;

public class TransmutationButton extends JButton {
    private ResourceType resourceType;

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
