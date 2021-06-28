package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.leaderproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to make you choose which resources you want to get from the leader production
 */
public class StoneLeaderProductionListener implements ActionListener {
    private final GUI gui;

    public StoneLeaderProductionListener(GUI gui) { this.gui = gui; }

    /**
     * saves your choice and goes on based on the type of production move that you are doing
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getProductionMoveHandler().setPerkManufacturedResources(ResourceType.Stone);
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
