package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.boardproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.TurnPhase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to make you cancel the production move
 */
public class GoBackFromBoardProduction implements ActionListener {
    private final GUI gui;
    private final boolean isManufacturingSelection;

    public GoBackFromBoardProduction(GUI gui, boolean isManufacturingSelection) {
        this.gui = gui;
        this.isManufacturingSelection = isManufacturingSelection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isManufacturingSelection) {
            this.gui.getInformationsGUI().getMainPhaseHandler().goToProductionButtons();
            this.gui.disableButtonsHandler(TurnPhase.MainPhase);
        } else {
            this.gui.getInformationsGUI().getMainPhaseHandler().goToBoardProductionButtons();
        }
    }
}
