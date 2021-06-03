package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoneHandListener implements ActionListener {
    private ResourceInHandGUI resourceInHandGUI;
    private InformationsGUI informationsGUI;
    public StoneHandListener(ResourceInHandGUI resourceInHandGUI, InformationsGUI informationsGUI) {
        this.resourceInHandGUI = resourceInHandGUI;
        this.informationsGUI = informationsGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resourceInHandGUI.decreaseStone();
        this.informationsGUI.addStone();

    }
}