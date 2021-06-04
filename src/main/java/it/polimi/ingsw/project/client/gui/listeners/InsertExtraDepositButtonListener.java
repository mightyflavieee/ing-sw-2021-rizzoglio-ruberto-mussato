package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.board.ExtraDepositsGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandlerGUI;
import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.model.resource.Resource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InsertExtraDepositButtonListener implements ActionListener {
    private ResourceInHandlerGUI resourceInHandlerGUI;
    private ExtraDepositsGUI extraDepositsGUI;
    private ResourceInHandGUI resourceInHandGUI;
    private GUI gui;

    public InsertExtraDepositButtonListener(ResourceInHandlerGUI resourceInHandlerGUI, WarehouseGUI warehouseGUI, ResourceInHandGUI resourceInHandGUI, GUI gui) {
        this.resourceInHandlerGUI = resourceInHandlerGUI;
        this.extraDepositsGUI = warehouseGUI.getExtraDepositsGUI();
        this.resourceInHandGUI = resourceInHandGUI;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Resource> resourceList = new ArrayList<>();
        for(int i = 0; i< resourceInHandlerGUI.getResourceNum(); i++) {
            resourceList.add(new Resource(resourceInHandlerGUI.getResourceType()));
        }
        this.extraDepositsGUI.insertInExtraDeposit(resourceList);
        this.resourceInHandlerGUI.removeResource();
        this.resourceInHandGUI.refresh();
        if(this.gui.getTakeMarketResourceBuilder().setHandClear()){
            this.gui.sendMarketMove();
        }
    }
}