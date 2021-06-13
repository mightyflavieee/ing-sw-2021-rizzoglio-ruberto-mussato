package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.leaderproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.TurnPhase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoBackLeaderProductionListener implements ActionListener {
    private GUI gui;

    public GoBackLeaderProductionListener(GUI gui) { this.gui = gui; }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.disableButtonsHandler(TurnPhase.MainPhase);
        this.gui.getInformationsGUI().getMainPhaseHandler().goToProductionButtons();
    }
}
