package it.polimi.ingsw.project.client.gui.listeners.warehouse;

import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to select the floor that you want to move before clicking on the arrow up and down buttons
 */
public class ResourceButtonListener implements ActionListener {
    private final WarehouseGUI warehouseGUI;
    private final ShelfFloor floorToChange;

    public ResourceButtonListener(WarehouseGUI warehouseGUI, ShelfFloor floorToChange) {
        this.warehouseGUI = warehouseGUI;
        this.floorToChange = floorToChange;
    }

    /**
     * sets the floor on which the resource is to selected to be moved
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.warehouseGUI.getCanChangeShelves()) {
            this.warehouseGUI.setFloorToChange(floorToChange);
        }
    }
}
