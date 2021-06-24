package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.boardproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ShieldBoardProductionListener implements ActionListener {
    private GUI gui;
    private boolean isManufacturingSelection;

    public ShieldBoardProductionListener(GUI gui, boolean isManufacturingSelection) {
        this.gui = gui;
        this.isManufacturingSelection = isManufacturingSelection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isManufacturingSelection) {
            this.gui.getInformationsGUI().getProductionMoveHandler().setBoardOrPerkManufacturedResource(ResourceType.Shield);
            this.gui.getInformationsGUI().getMainPhaseHandler().goToBoardRequiredResourcesButtons();
            this.gui.getInformationsGUI().getjTextArea().setText("Select the two resources you want to use for the Board production:");
        } else {
            Map<ResourceType, Integer> requiredResources = this.gui.getInformationsGUI().getProductionMoveHandler().getBoardRequiredResources();
            if (requiredResources != null) {
                if (requiredResources.containsKey(ResourceType.Shield)) {
                    requiredResources.put(ResourceType.Shield, 2);
                } else {
                    requiredResources.put(ResourceType.Shield, 1);
                }
            } else {
                requiredResources = new HashMap<>();
                requiredResources.put(ResourceType.Shield, 1);
            }
            this.gui.getInformationsGUI().getProductionMoveHandler().setBoardRequireResources(requiredResources);

            ProductionType productionType = this.gui.getInformationsGUI().getProductionMoveHandler().getProductionType();
            switch (productionType) {
                case Board:
                    this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                    this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                    this.gui.getInformationsGUI().createSelectResourcesHandlerForProduction();
                    break;
                case BoardAndDevCard:
                    this.gui.getInformationsGUI().getjTextArea().setText("Select Development Card from the Map Tray for the production!");
                    this.gui.getBoardGUI().getMapTrayGUI().enableAllButtons();
                    break;
                case BoardAndLeaderCard:
                    this.gui.getInformationsGUI().getjTextArea().setText("Select Leader Card for the production!");
                    this.gui.getLeaderCardPlaceGUI().enableButtonsForProduction();
                    break;
                case BoardAndDevCardAndLeaderCard:
                    this.gui.getInformationsGUI().getjTextArea().setText("Select Development Card and Leader Card for the production!");
                    this.gui.getBoardGUI().getMapTrayGUI().enableAllButtons();
                    this.gui.getLeaderCardPlaceGUI().enableButtonsForProduction();
                    break;
            }

            // cheks if the user has selected the two resources for the Board production
            Map<ResourceType, Integer> boardRequiredResources = this.gui.getInformationsGUI().getProductionMoveHandler().getBoardRequiredResources();
            int count = 0;
            for (ResourceType resourceType : boardRequiredResources.keySet()) {
                if (boardRequiredResources.get(resourceType) == 2) {
                    this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
                    break;
                }
                count++;
            }
            if (count == 2) {
                this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
            }
        }
    }
}
