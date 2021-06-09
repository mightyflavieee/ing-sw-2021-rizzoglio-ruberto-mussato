package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.productiontypes;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.playermove.ProductionType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DevLeaderCardsProductionListener implements ActionListener {
    private GUI gui;

    public DevLeaderCardsProductionListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().createProductionMoveHandler(ProductionType.DevCardAndLeader);
        this.gui.getBoardGUI().getMapTrayGUI().enableAllButtons();

        // todo this.gui.getLeaderCardPlaceGUI().enableCardsButtons();
    }
}
