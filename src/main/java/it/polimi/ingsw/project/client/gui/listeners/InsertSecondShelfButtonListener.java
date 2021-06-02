package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.ResourceInHandGUI;
import it.polimi.ingsw.project.client.gui.ResourceInHandlerGUI;
import it.polimi.ingsw.project.client.gui.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.resource.Resource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InsertSecondShelfButtonListener implements ActionListener {
    private ResourceInHandlerGUI resourceInHandlerGUI;
    private WarehouseGUI warehouseGUI;
    private ResourceInHandGUI resourceInHandGUI;


    public InsertSecondShelfButtonListener(ResourceInHandlerGUI resourceInHandlerGUI, WarehouseGUI warehouseGUI,ResourceInHandGUI resourceInHandGUI) {
        this.resourceInHandlerGUI = resourceInHandlerGUI;
        this.warehouseGUI = warehouseGUI;
        this.resourceInHandGUI = resourceInHandGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Resource> resourceList = new ArrayList<>();
        for(int i = 0; i< resourceInHandlerGUI.getResourceNum(); i++) {
            resourceList.add(new Resource(resourceInHandlerGUI.getResourceType()));
        }
        this.warehouseGUI.insertInShelf(ShelfFloor.Second,resourceList);
        this.resourceInHandlerGUI.removeResource();
        this.resourceInHandGUI.refresh();
    }
}