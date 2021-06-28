package it.polimi.ingsw.project.client.gui.listeners.warehouse;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to move down the shelf that you selected during the market move
 */
public class ArrowDownButtonListener implements ActionListener {
    private final WarehouseGUI warehouseGUI;
    private final InformationsGUI informationsGUI;

    public ArrowDownButtonListener(WarehouseGUI warehouseGUI, InformationsGUI informationsGUI) {
        this.warehouseGUI = warehouseGUI;
        this.informationsGUI = informationsGUI;
    }

    /**
     * moves the shelf if possible or shows an error message
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.warehouseGUI.getCanChangeShelves()) {
            if (this.warehouseGUI.getFloorToChange() != null) {
                if (this.warehouseGUI.getFloorToChange() != ShelfFloor.Third) {
                    switch (this.warehouseGUI.getFloorToChange()) {
                        case First:
                            if (this.warehouseGUI.getWarehouseModel().getShelves().get(ShelfFloor.Second).size() == 2) {
                                this.warehouseGUI.changeShelf(ShelfFloor.First, ShelfFloor.Third);
                            } else {
                                this.warehouseGUI.changeShelf(ShelfFloor.First, ShelfFloor.Second);
                            }
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
