package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardDevCardProductionListener implements ActionListener {
    private GUI gui;

    public BoardDevCardProductionListener(GUI gui) { this.gui = gui; }

    @Override
    public void actionPerformed(ActionEvent e) {
        // todo this.gui.getInformationsGUI().createProductionMoveHandler();

        this.gui.getInformationsGUI().getjTextArea().setText("Select the resource you want to produce with the board:");
        this.gui.getInformationsGUI().getMainPhaseHandler().goToBoardProductionButtons();
    }
}
