package it.polimi.ingsw.project.client.gui.listeners.market;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to make you select a shield when you collected it in the market move
 */
public class ShieldHandListener implements ActionListener {
    private final ResourceInHandGUI resourceInHandGUI;
    private final InformationsGUI informationsGUI;

    public ShieldHandListener(ResourceInHandGUI resourceInHandGUI, InformationsGUI informationsGUI) {
        this.resourceInHandGUI = resourceInHandGUI;
        this.informationsGUI = informationsGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resourceInHandGUI.decreaseShield();
        this.informationsGUI.addShield();

    }
}
