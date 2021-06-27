package it.polimi.ingsw.project.client.gui.listeners.market;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServantHandListener implements ActionListener {
    private final ResourceInHandGUI resourceInHandGUI;
    private final InformationsGUI informationsGUI;

    public ServantHandListener(ResourceInHandGUI resourceInHandGUI, InformationsGUI informationsGUI) {
        this.resourceInHandGUI = resourceInHandGUI;
        this.informationsGUI = informationsGUI;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resourceInHandGUI.decreaseServant();
        this.informationsGUI.addServant();

    }
}
