package it.polimi.ingsw.project.client.gui.listeners.informations;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransmutationSelectorListener implements ActionListener {
    private JButton jButton;
    private GUI gui;

    public TransmutationSelectorListener(JButton jButton, GUI gui) {
        this.jButton = jButton;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ResourceType resourceType = ResourceType.valueOf(jButton.getText());
        this.gui.setChosedTransmutationPerk(resourceType);
    }
}
