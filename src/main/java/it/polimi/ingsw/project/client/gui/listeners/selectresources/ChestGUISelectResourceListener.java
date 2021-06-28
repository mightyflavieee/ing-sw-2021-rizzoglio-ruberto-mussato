package it.polimi.ingsw.project.client.gui.listeners.selectresources;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.board.ChestGUI;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to select resources from the chest
 */
public class ChestGUISelectResourceListener implements ActionListener {
    private final ChestGUI chestGUI;
    private final InformationsGUI informationsGUI;
    private final ResourceType resourceType;


    public ChestGUISelectResourceListener(ChestGUI chestGUI, InformationsGUI informationsGUI, ResourceType resourceType) {
        this.chestGUI = chestGUI;
        this.informationsGUI = informationsGUI;
        this.resourceType = resourceType;
    }

    /**
     * if you have more than 0 resources it decreases the number of resources in the chest and increases the number of resources selected,
     * it displays this change in the board and in the information gui
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.chestGUI.isClickable()) {
            int previousValue = Integer.parseInt(this.chestGUI.getNumberOfResouces().get(this.resourceType).getText());
            if (previousValue != 0) {
                this.chestGUI.getNumberOfResouces().get(this.resourceType).setText(String.valueOf(previousValue-1));
                this.informationsGUI.updateSelectResourcesHandler(this.resourceType, "Chest");
                if (this.informationsGUI.getProductionMoveHandler() == null) {
                    this.informationsGUI.showDevCardPurchaseInfo();
                } else {
                    this.informationsGUI.showProductionInfo();
                }
            }
        }
    }
}
