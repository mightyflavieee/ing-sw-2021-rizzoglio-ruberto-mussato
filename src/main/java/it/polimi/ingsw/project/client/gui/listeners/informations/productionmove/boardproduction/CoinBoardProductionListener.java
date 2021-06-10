package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.boardproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CoinBoardProductionListener implements ActionListener {
    private GUI gui;
    private boolean isManufacturingSelection;

    public CoinBoardProductionListener(GUI gui, boolean isManufacturingSelection) {
        this.gui = gui;
        this.isManufacturingSelection = isManufacturingSelection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isManufacturingSelection) {
            this.gui.getInformationsGUI().getProductionMoveHandler().setBoardOrPerkManufacturedResource(ResourceType.Coin);
            this.gui.getInformationsGUI().getMainPhaseHandler().goToBoardRequiredResourcesButtons();
            this.gui.getInformationsGUI().getjTextArea().setText("Select the two resources you want to use for the Board production:");
        } else {
            Map<ResourceType, Integer> requiredResources = this.gui.getInformationsGUI().getProductionMoveHandler().getBoardRequiredResources();
            if (requiredResources != null) {
                if (requiredResources.containsKey(ResourceType.Coin)) {
                    requiredResources.put(ResourceType.Coin, 2);
                } else {
                    requiredResources.put(ResourceType.Coin, 1);
                }
            } else {
                requiredResources = new HashMap<>();
                requiredResources.put(ResourceType.Coin, 1);
            }
            this.gui.getInformationsGUI().getProductionMoveHandler().setBoardRequireResources(requiredResources);

            // todo enable dei bottoni LeaderCard
            switch (this.gui.getInformationsGUI().getProductionMoveHandler().getProductionType()) {
                case Board:
                    this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                    this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                    this.gui.getInformationsGUI().showProductionInfo();
                    break;
                case BoardAndDevCard:
                    this.gui.getInformationsGUI().getjTextArea().setText("Select Development Card from the Map Tray for the production!");
                    this.gui.getBoardGUI().getMapTrayGUI().enableAllButtons();
                    break;
                case BoardAndLeaderCard:
                    this.gui.getInformationsGUI().getjTextArea().setText("Select Leader Card for the production!");

                    // qui mettere enable di bottoni leader cards
                    break;
                case BoardAndDevCardAndLeaderCard:
                    this.gui.getInformationsGUI().getjTextArea().setText("Select Development Card and Leader Card for the production!");
                    this.gui.getBoardGUI().getMapTrayGUI().enableAllButtons();

                    // qui mettere enable di bottoni leader cards
                    break;
            }

            this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
        }
    }
}
