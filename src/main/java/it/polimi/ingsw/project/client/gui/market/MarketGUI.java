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

    private final TrayGUI trayGui;
    private final ResourceInHandGUI resourceInHandGUI;
    public MarketGUI(GUI gui, Market market, InformationsGUI informationsGUI) {
        this.setTitle("Market");
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        this.resourceInHandGUI = new ResourceInHandGUI(informationsGUI, gui.getTakeMarketResourceBuilder());
        this.trayGui = new TrayGUI(this.resourceInHandGUI, gui, market);
        this.setLayout(new BorderLayout());
        this.add(trayGui,BorderLayout.CENTER);
        this.add(resourceInHandGUI,BorderLayout.SOUTH);
    }

    /**
     * updates the local and displayed model
     */
    public void setMarket(Market market, Player mePlayer){
        this.trayGui.setMarket(market,mePlayer);
    }


    public ResourceInHandGUI getResourceInHandGUI() {
        return this.resourceInHandGUI;
    }

    public Market getMarket() {
        return  this.trayGui.getMarket();
    }

    public void disableButtons() {
        this.trayGui.disableButtons();
    }

    public void enableButtons() {
        this.trayGui.enableButtons();
    }

    /**
     changes the size of the displayed pictures in the market
     */
    public void refreshSize(int width, int height) {
        this.trayGui.refreshSize(width/4,height/3);
    }

    /**
     * returns true if you have two transmutation perks activated (corner case)
     */
    public boolean isTransmutationChosable() {
        return this.trayGui.isTransmutationChosable();
    }

    public List<ResourceType> getTransmutationPerks() {
        return this.trayGui.getTransmutationPerks();
    }

    /**
     * sets the transmutation perk that you choose in the case that you have two transmutation perks available
     */
    public void setChosenTransmutationPerk(ResourceType resourceType) {
        this.trayGui.setChosedTransmutationPerk(resourceType);
    }
}
