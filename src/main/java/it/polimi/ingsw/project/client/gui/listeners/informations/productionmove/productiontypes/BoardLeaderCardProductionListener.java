package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.productiontypes;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.playermove.ProductionType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardLeaderCardProductionListener implements ActionListener {
    private GUI gui;

    public BoardLeaderCardProductionListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().createProductionMoveHandler(ProductionType.BoardAndLeaderCard);
        this.gui.getInformationsGUI().getjTextArea().setText("Select the resource you want to produce with the board:");
        this.gui.getInformationsGUI().getMainPhaseHandler().goToBoardProductionButtons();
    }
}