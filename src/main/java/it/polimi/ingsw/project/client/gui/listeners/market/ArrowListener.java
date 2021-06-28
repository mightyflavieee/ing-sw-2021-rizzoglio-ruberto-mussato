package it.polimi.ingsw.project.client.gui.listeners.market;

import it.polimi.ingsw.project.client.gui.TakeMarketResourceBuilder;
import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.market.MarbleTrayGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * it is used when you want to insert a marble in the market
 */
public class ArrowListener implements ActionListener {
    private final MarbleTrayGUI marbleTrayGui;
    private final int axis;
    private final int position;
    private final ResourceInHandGUI resourceInHandGUI;
    private final GUI gui;
    private final TakeMarketResourceBuilder takeMarketResourceBuilder;
    public ArrowListener(MarbleTrayGUI marbleTrayGui, int axis, int position, ResourceInHandGUI resourceInHandGUI, GUI gui) {
        this.marbleTrayGui = marbleTrayGui;
        this.axis = axis;
        this.position = position;
        this.resourceInHandGUI = resourceInHandGUI;
        this.gui = gui;
        this.takeMarketResourceBuilder = gui.getTakeMarketResourceBuilder();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean hasFaith = false;
        List<Resource> resourceList = this.marbleTrayGui.insertMarble(axis,position);
        this.marbleTrayGui.refresh();
        this.resourceInHandGUI.refresh(resourceList);
        this.marbleTrayGui.disableButtons();
        for(int i = 0; i < resourceList.size(); i++){
            if(resourceList.get(i).getType() == ResourceType.Faith){
                hasFaith = true;
                resourceList.remove(i);
                break;
            }
        }
        this.takeMarketResourceBuilder.setHasRedMarble(hasFaith);
        if(resourceList.size()==0){
            this.gui.sendMarketMove();
        }else {
            this.gui.showMarketInformations(hasFaith);
        }
    }
}
