package it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.model.board.DevCardPosition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightPositionPurchaseListener implements ActionListener {
    private GUI gui;

    public RightPositionPurchaseListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().createBuyDevCardHandler(DevCardPosition.Right);
        this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
        this.gui.getCardContainerGUI().enableAllButtons();
    }
}