package it.polimi.ingsw.project.client.gui.listeners.selectresources;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.board.ExtraDepositsGUI;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to select resources from the extra deposit
 */
public class ExtraDepositGUISelectResourceListener implements ActionListener {
    private final ExtraDepositsGUI extraDepositsGUI;
    private final InformationsGUI informationsGUI;
    private final ResourceType resourceType;
    private final int numOfExtraDeposit;
    private final int buttonNumber;

    public ExtraDepositGUISelectResourceListener (ExtraDepositsGUI extraDepositsGUI,
                                                  InformationsGUI informationsGUI,
                                                  ResourceType resourceType,
                                                  int numOfExtraDeposit,
                                                  int buttonNumber) {
        this.extraDepositsGUI = extraDepositsGUI;
        this.informationsGUI = informationsGUI;
        this.resourceType = resourceType;
        this.numOfExtraDeposit = numOfExtraDeposit;
        this.buttonNumber = buttonNumber;
    }

    /**
     * it removes a resources from the extra deposit and increase the number of resources selected in the informations gui
     * it also shows a no resource picture where you took the resource
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.extraDepositsGUI.isClickable()) {
            if (this.numOfExtraDeposit == 1) {
                this.extraDepositsGUI.getFirstExtraDepositButtons().get(buttonNumber).setIcon(Utils.readIcon("warehouse/warehouse_no_resource.png",10,10));
            }
            if (this.numOfExtraDeposit == 2) {
                this.extraDepositsGUI.getSecondExtraDepositButtons().get(buttonNumber).setIcon(Utils.readIcon("warehouse/warehouse_no_resource.png",10,10));
            }
            this.informationsGUI.updateSelectResourcesHandler(this.resourceType, "ExtraDeposit");
            if (this.informationsGUI.getProductionMoveHandler() == null) {
                this.informationsGUI.showDevCardPurchaseInfo();
            } else {
                this.informationsGUI.showProductionInfo();
            }
        }
    }
}
