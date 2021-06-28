package it.polimi.ingsw.project.client.gui.listeners.market;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to make you select a stone when you collected it in the market move
 */
public class StoneHandListener implements ActionListener {
    private final ResourceInHandGUI resourceInHandGUI;
    private final InformationsGUI informationsGUI;
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