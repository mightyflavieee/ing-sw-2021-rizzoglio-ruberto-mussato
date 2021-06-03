package it.polimi.ingsw.project.client.gui.listeners.warehouse;

import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResourceButtonListener implements ActionListener {
    private WarehouseGUI warehouseGUI;
    private ShelfFloor floorToChange;

    public ResourceButtonListener(WarehouseGUI warehouseGUI, ShelfFloor floorToChange) {
        this.warehouseGUI = warehouseGUI;
        this.floorToChange = floorToChange;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.warehouseGUI.getCanChangeShelves()) {
            this.warehouseGUI.setFloorToChange(floorToChange);
        }
    }
}
