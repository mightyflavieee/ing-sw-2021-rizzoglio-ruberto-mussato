package it.polimi.ingsw.project.client.gui.listeners.selectresources;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to select resources from the warehouse (shelves)
 */
public class WarehouseGUISelectResourceListener implements ActionListener {
    private final WarehouseGUI warehouseGUI;
    private final InformationsGUI informationsGUI;
    private final ResourceType resourceType;
    private final ShelfFloor floor;
    private final int buttonNumber;

    public WarehouseGUISelectResourceListener(WarehouseGUI warehouseGUI,
                                              InformationsGUI informationsGUI,
                                              ResourceType resourceType,
                                              ShelfFloor floor,
                                              int buttonNumber) {
        this.warehouseGUI = warehouseGUI;
        this.informationsGUI = informationsGUI;
        this.resourceType = resourceType;
        this.floor = floor;
        this.buttonNumber = buttonNumber;
    }

    /**
     * it removes a resources from the warehouse and increase the number of resources selected in the informations gui
     * it also shows a no resource picture where you took the resource
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!this.warehouseGUI.getCanChangeShelves()) {
            if (this.warehouseGUI.isClickable()) {
                this.warehouseGUI.getShelvesButtons().get(this.floor).get(this.buttonNumber).setIcon(Utils.readIcon("warehouse/warehouse_no_resource.png",10,10));
                this.informationsGUI.updateSelectResourcesHandler(this.resourceType, "Warehouse");
                if (this.informationsGUI.getProductionMoveHandler() == null) {
                    this.informationsGUI.showDevCardPurchaseInfo();
                } else {
                    this.informationsGUI.showProductionInfo();
                }
            }
        }
    }
}
