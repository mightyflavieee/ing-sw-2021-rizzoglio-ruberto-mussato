package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.productiontypes;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.playermove.ProductionType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DevCardProductionListener implements ActionListener {
    private final GUI gui;

    public DevCardProductionListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().createProductionMoveHandler(ProductionType.DevCard);
        this.gui.getBoardGUI().getMapTrayGUI().enableAllButtons();
        this.gui.getInformationsGUI().getjTextArea().setText("Select Development Card from the Map Tray for the production!");
        this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
    }
}

