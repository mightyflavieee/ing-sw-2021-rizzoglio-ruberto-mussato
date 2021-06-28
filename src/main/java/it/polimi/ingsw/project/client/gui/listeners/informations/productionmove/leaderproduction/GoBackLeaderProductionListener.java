package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.leaderproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.TurnPhase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to make you cancel the production leader card move and its combinations
 */
public class GoBackLeaderProductionListener implements ActionListener {
    private final GUI gui;

    public GoBackLeaderProductionListener(GUI gui) { this.gui = gui; }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.disableLeaderButtonsHandler(TurnPhase.MainPhase);
        this.gui.getInformationsGUI().getMainPhaseHandler().goToProductionButtons();
    }
}
