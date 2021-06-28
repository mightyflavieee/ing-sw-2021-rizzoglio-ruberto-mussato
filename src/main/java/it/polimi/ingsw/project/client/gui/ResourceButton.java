package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;

/**
 * resource button used for the selection of the resources at the beginning of the game
 */
public class ResourceButton extends JButton {
    private ResourceType resourceType;

    public ResourceButton() {
    }

    public ResourceButton(ResourceType resourceType) {
        this.resourceType = resourceType;
        this.setIcon(Utils.readIcon("resourcetype/" + resourceType + ".png",
                30 ,30));
    }

    public ResourceType getResourceType() {
        return resourceType;

    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        this.setIcon(Utils.readIcon("resourcetype/" + resourceType + ".png",
                30 ,30));
    }
}
