package it.polimi.ingsw.project.client.gui.listeners.warehouse;

import it.polimi.ingsw.project.client.gui.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArrowDownButtonListener implements ActionListener {
    private WarehouseGUI warehouseGUI;

    public ArrowDownButtonListener(WarehouseGUI warehouseGUI) { this.warehouseGUI = warehouseGUI; }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.warehouseGUI.getCanChangeShelves()) {
            if (this.warehouseGUI.getFloorToChange() != null) {
                if (this.warehouseGUI.getFloorToChange() != ShelfFloor.Third) {
                    switch (this.warehouseGUI.getFloorToChange()) {
                        case First:
                            this.warehouseGUI.changeShelf(ShelfFloor.First, ShelfFloor.Second);
                            this.warehouseGUI.setFloorToChange(null);
                            break;
                        case Second:
                            this.warehouseGUI.changeShelf(ShelfFloor.Second, ShelfFloor.Third);
                            this.warehouseGUI.setFloorToChange(null);
                            break;
                    }
                } else {
                    this.warehouseGUI.getInformationsGUI().getjTextArea().setText("Cannot move down the third shelf floor!");
                }
            } else {
                this.warehouseGUI.getInformationsGUI().getjTextArea().setText("Select a floor before hitting the button!");
            }
        }
    }
}
