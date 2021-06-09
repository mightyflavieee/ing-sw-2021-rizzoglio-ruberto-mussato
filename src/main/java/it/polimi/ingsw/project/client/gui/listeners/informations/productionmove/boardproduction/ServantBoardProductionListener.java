package it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.boardproduction;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ServantBoardProductionListener implements ActionListener {
    private GUI gui;
    private boolean isManufacturingSelection;

    public ServantBoardProductionListener(GUI gui, boolean isManufacturingSelection) {
        this.gui = gui;
        this.isManufacturingSelection = isManufacturingSelection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isManufacturingSelection) {
            this.gui.getInformationsGUI().getProductionMoveHandler().setBoardOrPerkManufacturedResource(ResourceType.Servant);
            this.gui.getInformationsGUI().getMainPhaseHandler().goToBoardRequiredResourcesButtons();
            this.gui.getInformationsGUI().getjTextArea().setText("Select the two resources you want to use:");
        } else {
            Map<ResourceType, Integer> requiredResources = this.gui.getInformationsGUI().getProductionMoveHandler().getBoardRequiredResources();
            if (requiredResources.containsKey(ResourceType.Servant)) {
                requiredResources.put(ResourceType.Servant, 2);
            } else {
                requiredResources.put(ResourceType.Servant, 1);
            }
            this.gui.getInformationsGUI().getProductionMoveHandler().setBoardRequireResources(requiredResources);

            // todo enable dei bottoni
            switch (this.gui.getInformationsGUI().getProductionMoveHandler().getProductionType()) {
                case Board:
                    break;
                case BoardAndDevCard:
                    break;
                case BoardAndLeaderCard:
                    break;
                case BoardAndDevCardAndLeaderCard:
                    break;
            }
        }
    }
}
