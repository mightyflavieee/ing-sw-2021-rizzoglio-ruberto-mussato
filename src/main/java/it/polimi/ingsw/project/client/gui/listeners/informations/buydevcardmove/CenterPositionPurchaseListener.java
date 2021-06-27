package it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.model.board.DevCardPosition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CenterPositionPurchaseListener implements ActionListener {
    private final GUI gui;

    public CenterPositionPurchaseListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().createBuyDevCardHandler(DevCardPosition.Center);
        this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
        this.gui.getCardContainerGUI().enableAllButtons();
    }
}
