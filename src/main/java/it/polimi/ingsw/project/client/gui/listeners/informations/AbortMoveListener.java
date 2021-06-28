package it.polimi.ingsw.project.client.gui.listeners.informations;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.TurnPhase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to make you cancel a move that you are doing when you are in its final part
 */
public class AbortMoveListener implements ActionListener {
    private final GUI gui;

    public AbortMoveListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getMainPhaseHandler().goToMainButtons();
        this.gui.disableButtonsHandler(TurnPhase.MainPhase);
        this.gui.getBoardGUI().refresh(this.gui.getBoardGUI().getBoardModel());
        this.gui.getCardContainerGUI().refresh();
    }
}
