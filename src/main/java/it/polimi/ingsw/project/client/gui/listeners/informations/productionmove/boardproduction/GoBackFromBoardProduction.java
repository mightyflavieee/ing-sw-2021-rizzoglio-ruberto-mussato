package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.boardproduction;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoBackFromBoardProduction implements ActionListener {
    private GUI gui;
    private boolean isManufacturingSelection;

    public GoBackFromBoardProduction(GUI gui, boolean isManufacturingSelection) {
        this.gui = gui;
        this.isManufacturingSelection = isManufacturingSelection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isManufacturingSelection) {
            this.gui.getInformationsGUI().getMainPhaseHandler().goToProductionButtons();
        } else {
            this.gui.getInformationsGUI().getMainPhaseHandler().goToBoardProductionButtons();
        }
    }
}
