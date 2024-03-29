package it.polimi.ingsw.project.client.gui.listeners.market;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandlerGUI;
import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.resource.Resource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * it is used to insert the resources that you selected during the market move in the third shelf
 */
public class InsertThirdShelfButtonListener implements ActionListener {
    private final ResourceInHandlerGUI resourceInHandlerGUI;
    private final WarehouseGUI warehouseGUI;
    private final ResourceInHandGUI resourceInHandGUI;
    private final GUI gui;
    public InsertThirdShelfButtonListener(ResourceInHandlerGUI resourceInHandlerGUI, WarehouseGUI warehouseGUI, ResourceInHandGUI resourceInHandGUI, GUI gui) {
        this.resourceInHandlerGUI = resourceInHandlerGUI;
        this.warehouseGUI = warehouseGUI;
        this.resourceInHandGUI = resourceInHandGUI;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Resource> resourceList = new ArrayList<>();
        for(int i = 0; i< resourceInHandlerGUI.getResourceNum(); i++) {
            resourceList.add(new Resource(resourceInHandlerGUI.getResourceType()));
        }
        this.warehouseGUI.insertInShelf(ShelfFloor.Third,resourceList);
        this.resourceInHandlerGUI.removeResource();
        this.resourceInHandGUI.refresh();
        if(this.gui.getTakeMarketResourceBuilder().setHandClear()){
            this.gui.sendMarketMove();
        }
    }
}