package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoBackFromProductionListener implements ActionListener {
    private final GUI gui;

    public GoBackFromProductionListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getMainPhaseHandler().goToMainButtons();
    }
}
