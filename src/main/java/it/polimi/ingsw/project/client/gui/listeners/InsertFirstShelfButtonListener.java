package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.ResourceInHandlerGUI;
import it.polimi.ingsw.project.client.gui.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.resource.Resource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InsertFirstShelfButtonListener implements ActionListener {
    private ResourceInHandlerGUI resourceInHandlerGUI;
    private WarehouseGUI warehouseGUI;

    public InsertFirstShelfButtonListener(ResourceInHandlerGUI resourceInHandlerGUI, WarehouseGUI warehouseGUI) {
        this.resourceInHandlerGUI = resourceInHandlerGUI;
        this.warehouseGUI = warehouseGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Resource> resourceList = new ArrayList<>();
        for(int i = 0; i< resourceInHandlerGUI.getResourceNum(); i++) {
            resourceList.add(new Resource(resourceInHandlerGUI.getResourceType()));
        }
        this.resourceInHandlerGUI.removeResource();
        this.warehouseGUI.insertInShelf(ShelfFloor.First,resourceList);
    }
}
