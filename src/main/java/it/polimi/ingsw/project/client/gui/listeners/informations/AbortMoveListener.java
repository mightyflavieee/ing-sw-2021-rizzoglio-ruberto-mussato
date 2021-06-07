package it.polimi.ingsw.project.client.gui.listeners.informations;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.TurnPhase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AbortMoveListener implements ActionListener {
    private GUI gui;

    public AbortMoveListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getMainPhaseHandler().goToMainButtons();
        this.gui.disableButtonsHandler(TurnPhase.MainPhase);

        // todo flush delle cose inserite dall'utente fino a quel punto
    }
}
