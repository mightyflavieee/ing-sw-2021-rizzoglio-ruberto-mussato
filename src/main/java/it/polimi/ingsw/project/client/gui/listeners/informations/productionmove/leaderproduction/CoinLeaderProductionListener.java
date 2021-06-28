package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.leaderproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoinLeaderProductionListener implements ActionListener {
    private final GUI gui;

    public CoinLeaderProductionListener(GUI gui) { this.gui = gui; }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getProductionMoveHandler().setPerkManufacturedResources(ResourceType.Coin);
        switch (this.gui.getInformationsGUI().getProductionMoveHandler().getProductionType()) {
            case LeaderCard:
            case BoardAndLeaderCard:
                this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                this.gui.getInformationsGUI().createSelectResourcesHandlerForProduction();
                this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
                this.gui.getInformationsGUI().showProductionInfo();
                break;
            case DevCardAndLeader:
            case BoardAndDevCardAndLeaderCard:
                if (this.gui.getInformationsGUI().getProductionMoveHandler().getDevCards() != null) {
                    this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                    this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                    this.gui.getInformationsGUI().createSelectResourcesHandlerForProduction();
                    this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
                    this.gui.getInformationsGUI().showProductionInfo();
                } else {
                    this.gui.getInformationsGUI().getjTextArea().setText("Select the Development Card!");
                    this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
                }
                break;
        }
    }
}
