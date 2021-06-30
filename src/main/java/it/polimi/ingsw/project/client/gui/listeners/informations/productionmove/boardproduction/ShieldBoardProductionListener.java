package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.boardproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * it is used to make you choose which resources you want to use for the board productions
 */
public class ShieldBoardProductionListener implements ActionListener {
    private final GUI gui;
    private final boolean isManufacturingSelection;

    public ShieldBoardProductionListener(GUI gui, boolean isManufacturingSelection) {
        this.gui = gui;
        this.isManufacturingSelection = isManufacturingSelection;
    }

    /**
     * saves your choice and goes on if you have selected all resources required
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isManufacturingSelection) {
            this.gui.getInformationsGUI().getProductionMoveHandler().setBoardManufacturedResource(ResourceType.Shield);
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
            // cheks if the user has selected the two resources for the Board production, if yes, proceeds
            // with the cards selection or the resources selection (based on the ProductionType)
            if (checkHasSelectedTwoResources(this.gui.getInformationsGUI().getProductionMoveHandler().getBoardRequiredResources())) {
                ProductionType productionType = this.gui.getInformationsGUI().getProductionMoveHandler().getProductionType();
                this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
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
            }
        }
    }

    private boolean checkHasSelectedTwoResources(Map<ResourceType, Integer> boardRequiredResources) {
        if (boardRequiredResources.size() == 2) {
            return true;
        } else {
            if (boardRequiredResources.size() == 1) {
                for (ResourceType resourceType : boardRequiredResources.keySet()) {
                    if (boardRequiredResources.get(resourceType) == 2) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
