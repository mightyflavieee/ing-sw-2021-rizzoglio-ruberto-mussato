package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardLeaderCardProductionListener implements ActionListener {
    private GUI gui;

    public BoardLeaderCardProductionListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // todo this.gui.getInformationsGUI().createProductionMoveHandler();

        this.gui.getInformationsGUI().getMainPhaseHandler().goToBoardProductionButtons();
    }
}
