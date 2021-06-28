package it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.board.DevCardPosition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to make you choose in which position put the bought development card
 */
public class LeftPositionPurchaseListener implements ActionListener {
    private final GUI gui;

    public LeftPositionPurchaseListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().createBuyDevCardHandler(DevCardPosition.Left);
        this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
        this.gui.getCardContainerGUI().enableAllButtons();
    }
}
