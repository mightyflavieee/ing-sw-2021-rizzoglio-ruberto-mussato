package it.polimi.ingsw.project.client.gui.listeners.warehouse;

import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArrowUpButtonListener implements ActionListener {
    private WarehouseGUI warehouseGUI;

    public ArrowUpButtonListener(WarehouseGUI warehouseGUI) { this.warehouseGUI = warehouseGUI; }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.warehouseGUI.getCanChangeShelves()) {
            if (this.warehouseGUI.getFloorToChange() != null) {
                if (this.warehouseGUI.getFloorToChange() != ShelfFloor.First) {
                    switch (this.warehouseGUI.getFloorToChange()) {
                        case Second:
                            this.warehouseGUI.changeShelf(ShelfFloor.Second, ShelfFloor.First);
                            this.warehouseGUI.setFloorToChange(null);
                            break;
                        case Third:
                            if (this.warehouseGUI.getWarehouseModel().getShelves().get(ShelfFloor.Second).size() == 2) {
                                this.warehouseGUI.changeShelf(ShelfFloor.Third, ShelfFloor.First);
                            } else {
                                this.warehouseGUI.changeShelf(ShelfFloor.Third, ShelfFloor.Second);
                            }
                            this.warehouseGUI.setFloorToChange(null);
                            break;
                    }
                } else {
                    this.warehouseGUI.getInformationsGUI().getjTextArea().setText("Cannot move up the first shelf floor!");
                }
            } else {
                this.warehouseGUI.getInformationsGUI().getjTextArea().setText("Select a floor before hitting the button!");
            }
        }
    }
}
