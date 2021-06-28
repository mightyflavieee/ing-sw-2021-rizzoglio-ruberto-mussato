package it.polimi.ingsw.project.client.gui.listeners.warehouse;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to move up the shelf that you selected during the market move
 */
public class ArrowUpButtonListener implements ActionListener {
    private final WarehouseGUI warehouseGUI;
    private final InformationsGUI informationsGUI;

    public ArrowUpButtonListener(WarehouseGUI warehouseGUI, InformationsGUI informationsGUI) {
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
                    this.informationsGUI.getResourceInHandler().refresh();
                } else {
                    this.warehouseGUI.getInformationsGUI().getjTextArea().setText("Cannot move up the first shelf floor!");
                }
            } else {
                this.warehouseGUI.getInformationsGUI().getjTextArea().setText("Select a floor before hitting the button!");
            }
        }
    }
}
