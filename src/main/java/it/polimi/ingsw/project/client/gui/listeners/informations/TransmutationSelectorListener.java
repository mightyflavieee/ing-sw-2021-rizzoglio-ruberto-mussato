package it.polimi.ingsw.project.client.gui.listeners.informations;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.ResourceButton;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to make you choose between two transmutation perk at the beginning of the market move if you have two transmutation perk available
 */
public class TransmutationSelectorListener implements ActionListener {
    private final ResourceButton resourceButton;
    private final GUI gui;

    public TransmutationSelectorListener(ResourceButton resourceButton, GUI gui) {
        this.resourceButton = resourceButton;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ResourceType resourceType = resourceButton.getResourceType();
        this.gui.setChosedTransmutationPerk(resourceType);
    }
}
