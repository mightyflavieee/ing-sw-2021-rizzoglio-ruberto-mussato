package it.polimi.ingsw.project.client.gui.listeners.informations;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.market.TransmutationButton;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransmutationSelectorListener implements ActionListener {
    private TransmutationButton transmutationButton;
    private GUI gui;

    public TransmutationSelectorListener(TransmutationButton transmutationButton, GUI gui) {
        this.transmutationButton = transmutationButton;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ResourceType resourceType = transmutationButton.getResourceType();
        this.gui.setChosedTransmutationPerk(resourceType);
    }
}
