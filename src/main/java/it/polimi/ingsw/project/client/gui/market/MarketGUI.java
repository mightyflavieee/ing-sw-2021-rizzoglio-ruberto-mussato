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

    private JLabel sfondo; //se lo metto in inglese mi da problemi
    private final TrayGUI trayGui;
    private final ResourceInHandGUI resourceInHandGUI;
    public MarketGUI(GUI gui, Market market, InformationsGUI informationsGUI) {
      //  this.jPanel = new JPanel();
        this.setTitle("Market");
        //this.constructorHelper();
      //  this.backgroundHelper();
       // this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
       // this.setLayout(new OverlayLayout());
      //  this.setSize(50,100);

        this.resourceInHandGUI = new ResourceInHandGUI(informationsGUI, gui.getTakeMarketResourceBuilder());
        this.trayGui = new TrayGUI(this.resourceInHandGUI, gui, market);
//        this.add(trayGui);
//        this.add(resourceInHandGUI);
//        this.pack();
        //this.setPreferredSize(new Dimension(200,500));
        this.setLayout(new BorderLayout());
        this.add(trayGui,BorderLayout.CENTER);
        this.add(resourceInHandGUI,BorderLayout.SOUTH);
    }
//    public void doubleSize() {
//        jLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/plancia portabiglie.png").getImage().getScaledInstance(jLabel.getWidth() * 2, jLabel.getHeight() * 2, Image.SCALE_SMOOTH)));
//    }
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
