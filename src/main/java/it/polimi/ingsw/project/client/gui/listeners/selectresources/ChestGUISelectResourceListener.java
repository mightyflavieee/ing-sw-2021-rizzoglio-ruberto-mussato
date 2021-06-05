package it.polimi.ingsw.project.client.gui.listeners.selectresources;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.board.ChestGUI;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChestGUISelectResourceListener implements ActionListener {
    private ChestGUI chestGUI;
    private InformationsGUI informationsGUI;
    private ResourceType resourceType;


    public ChestGUISelectResourceListener(ChestGUI chestGUI, InformationsGUI informationsGUI, ResourceType resourceType) {
        this.chestGUI = chestGUI;
        this.informationsGUI = informationsGUI;
        this.resourceType = resourceType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int previousValue = Integer.parseInt(this.chestGUI.getNumberOfResouces().get(this.resourceType).getText());
        if (previousValue != 0) {
            this.chestGUI.getNumberOfResouces().get(this.resourceType).setText(String.valueOf(previousValue-1));
            this.informationsGUI.updateSelectResourcesHandler(this.resourceType, false);
            this.informationsGUI.showDevCardPurchaseInfo();
        }
    }
}