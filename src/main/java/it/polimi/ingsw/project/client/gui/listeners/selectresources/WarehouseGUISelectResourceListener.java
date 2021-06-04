package it.polimi.ingsw.project.client.gui.listeners.selectresources;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.board.ExtraDepositsGUI;
import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WarehouseGUISelectResourceListener implements ActionListener {
    private WarehouseGUI warehouseGUI;
    private InformationsGUI informationsGUI;
    private ResourceType resourceType;
    private ShelfFloor floor;
    private int buttonNumber;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        this.warehouseGUI.getShelvesButtons().get(this.floor).get(this.buttonNumber).setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        this.informationsGUI.updateSelectResourcesHandler(this.resourceType, true);
    }
}
