package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.leaderproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShieldLeaderProductionListener implements ActionListener {
    private GUI gui;

    public ShieldLeaderProductionListener(GUI gui) { this.gui = gui; }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getProductionMoveHandler().setBoardOrPerkManufacturedResource(ResourceType.Shield);
        switch (this.gui.getInformationsGUI().getProductionMoveHandler().getProductionType()) {
            case LeaderCard:
            case BoardAndLeaderCard:
                this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                this.gui.getInformationsGUI().createSelectResourcesHandlerForProduction();
                break;
            case DevCardAndLeader:
            case BoardAndDevCardAndLeaderCard:
                if (this.gui.getInformationsGUI().getProductionMoveHandler().getDevCard() != null) {
                    this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                    this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                    this.gui.getInformationsGUI().createSelectResourcesHandlerForProduction();
                }
                break;
        }
    }
}
