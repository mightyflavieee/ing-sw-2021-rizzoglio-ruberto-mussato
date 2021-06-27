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
    //JPanel jPanel;

    private final TrayGUI trayGui;
    private final ResourceInHandGUI resourceInHandGUI;
    public MarketGUI(GUI gui, Market market, InformationsGUI informationsGUI) {
      //  this.jPanel = new JPanel();
        this.setTitle("Market");
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);

        this.resourceInHandGUI = new ResourceInHandGUI(informationsGUI, gui.getTakeMarketResourceBuilder());
        this.trayGui = new TrayGUI(this.resourceInHandGUI, gui, market);
        this.setLayout(new BorderLayout());
        this.add(trayGui,BorderLayout.CENTER);
        this.add(resourceInHandGUI,BorderLayout.SOUTH);
    }
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

    public void refreshSize(int width, int height) {
        this.trayGui.refreshSize(width/4,height/3);
    }

    public boolean isTransmutationChosable() {
        return this.trayGui.isTransmutationChosable();
    }

    public List<ResourceType> getTransmutationPerks() {
        return this.trayGui.getTransmutationPerks();
    }

    public void setChosedTransmutationPerk(ResourceType resourceType) {
        this.trayGui.setChosedTransmutationPerk(resourceType);
    }
}
