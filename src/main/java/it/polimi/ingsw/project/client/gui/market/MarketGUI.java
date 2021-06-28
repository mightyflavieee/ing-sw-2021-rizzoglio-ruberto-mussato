package it.polimi.ingsw.project.client.gui.market;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MarketGUI extends JInternalFrame {

    private final MarbleTrayGUI marbleTrayGui;
    private final ResourceInHandGUI resourceInHandGUI;
    public MarketGUI(GUI gui, Market market, InformationsGUI informationsGUI) {
        this.setTitle("Market");
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        this.resourceInHandGUI = new ResourceInHandGUI(informationsGUI, gui.getTakeMarketResourceBuilder());
        this.marbleTrayGui = new MarbleTrayGUI(this.resourceInHandGUI, gui, market);
        this.setLayout(new BorderLayout());
        this.add(marbleTrayGui,BorderLayout.CENTER);
        this.add(resourceInHandGUI,BorderLayout.SOUTH);
    }

    /**
     * updates the local and displayed model
     */
    public void setMarket(Market market, Player mePlayer){
        this.marbleTrayGui.setMarket(market,mePlayer);
    }


    public ResourceInHandGUI getResourceInHandGUI() {
        return this.resourceInHandGUI;
    }

    public Market getMarket() {
        return  this.marbleTrayGui.getMarket();
    }

    public void disableButtons() {
        this.marbleTrayGui.disableButtons();
    }

    public void enableButtons() {
        this.marbleTrayGui.enableButtons();
    }

    /**
     changes the size of the displayed pictures in the market
     */
    public void refreshSize(int width, int height) {
        this.marbleTrayGui.refreshSize(width/4,height/3);
    }

    /**
     * returns true if you have two transmutation perks activated (corner case)
     */
    public boolean isTransmutationChosable() {
        return this.marbleTrayGui.isTransmutationChosable();
    }

    public List<ResourceType> getTransmutationPerks() {
        return this.marbleTrayGui.getTransmutationPerks();
    }

    /**
     * sets the transmutation perk that you choose in the case that you have two transmutation perks available
     */
    public void setChosenTransmutationPerk(ResourceType resourceType) {
        this.marbleTrayGui.setChosenTransmutationPerk(resourceType);
    }
}
