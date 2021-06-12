package it.polimi.ingsw.project.client.gui.listeners.market;

import it.polimi.ingsw.project.client.TakeMarketResourceBuilder;
import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandlerGUI;
import it.polimi.ingsw.project.model.resource.Resource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DiscardButtonListener implements ActionListener {
    private ResourceInHandlerGUI resourceInHandlerGUI;
    private ResourceInHandGUI resourceInHandGUI;
    private TakeMarketResourceBuilder takeMarketResourceBuilder;
    private GUI gui;

    public DiscardButtonListener(ResourceInHandlerGUI resourceInHandlerGUI, ResourceInHandGUI resourceInHandGUI, GUI gui) {
        this.resourceInHandlerGUI = resourceInHandlerGUI;
        this.resourceInHandGUI = resourceInHandGUI;
        this.takeMarketResourceBuilder = gui.getTakeMarketResourceBuilder();
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Resource> resourceList = new ArrayList<>();
        for(int i = 0; i< resourceInHandlerGUI.getResourceNum(); i++) {
            resourceList.add(new Resource(resourceInHandlerGUI.getResourceType()));
        }
        this.takeMarketResourceBuilder.addResourcesToDiscard(resourceList);
        this.resourceInHandlerGUI.removeResource();
        this.resourceInHandGUI.refresh();
        if(this.takeMarketResourceBuilder.setHandClear()){
            this.gui.sendMarketMove();
        }
    }
}
