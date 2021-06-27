package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.productiontypes;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.playermove.ProductionType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardDevLeaderCardsProdListener implements ActionListener {
    private final GUI gui;

    public BoardDevLeaderCardsProdListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().createProductionMoveHandler(ProductionType.BoardAndDevCardAndLeaderCard);
        this.gui.getInformationsGUI().getjTextArea().setText("Select the resource you want to produce with the board:");
        this.gui.getInformationsGUI().getMainPhaseHandler().goToBoardProductionButtons();
    }
}
