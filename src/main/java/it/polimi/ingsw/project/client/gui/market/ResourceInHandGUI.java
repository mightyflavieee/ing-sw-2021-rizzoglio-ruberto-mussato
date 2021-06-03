package it.polimi.ingsw.project.client.gui.market;

import it.polimi.ingsw.project.client.TakeMarketResourceBuilder;
import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.listeners.*;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResourceInHandGUI extends JInternalFrame {
    private JButton coinButton, stoneButton, shieldButton, servantButton;
    private JLabel coinLabel, stoneLabel, shieldLabel, servantLabel;
    private int coinInt, stoneInt, shieldInt, servantInt;
    private TakeMarketResourceBuilder takeMarketResourceBuilder;
    public ResourceInHandGUI(InformationsGUI informationsGUI, TakeMarketResourceBuilder takeMarketResourceBuilder) {
        this.setTitle("Resources in Hand");
        this.setLayout(new GridLayout(2,4));
        this.coinButton = new JButton();
        this.coinButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Coin.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(coinButton);
        this.coinButton.addActionListener(new CoinHandListener(this,informationsGUI));
        this.stoneButton = new JButton();
        this.stoneButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Stone.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(stoneButton);
        this.stoneButton.addActionListener(new StoneHandListener(this,informationsGUI));
        this.shieldButton = new JButton();
        this.shieldButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Shield.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(shieldButton);
        this.shieldButton.addActionListener(new ShieldHandListener(this,informationsGUI));
        this.servantButton = new JButton();
        this.servantButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Servant.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(servantButton);
        this.servantButton.addActionListener(new ServantHandListener(this,informationsGUI));
        this.coinLabel = new JLabel(String.valueOf(0));
        this.add(coinLabel);
        this.stoneLabel = new JLabel(String.valueOf(0));
        this.add(stoneLabel);
        this.shieldLabel = new JLabel(String.valueOf(0));
        this.add(shieldLabel);
        this.servantLabel = new JLabel(String.valueOf(0));
        this.add(servantLabel);
        this.coinInt = 0;
        this.stoneInt = 0;
        this.shieldInt = 0;
        this.servantInt = 0;
        this.coinButton.setEnabled(false);
        this.stoneButton.setEnabled(false);
        this.shieldButton.setEnabled(false);
        this.servantButton.setEnabled(false);
        this.setVisible(true);
        this.pack();
        this.takeMarketResourceBuilder = takeMarketResourceBuilder;
    }

    public void refresh(List<Resource> resourceList) {
        int coin = 0, stone = 0, shield = 0, servant = 0;
        ResourceType type;
        for (Resource resource : resourceList) {
            type = resource.getType();
            switch (type) {
                case Coin:
                    coin++;
                    break;
                case Stone:
                    stone++;
                    break;
                case Shield:
                    shield++;
                    break;
                case Servant:
                    servant++;
                    break;
                default:
                    break;
            }

        }
        this.coinInt = coin;
        this.stoneInt = stone;
        this.shieldInt = shield;
        this.servantInt = servant;
        this.refresh();

    }
    public void refresh(){
        this.coinLabel.setText(String.valueOf(this.coinInt));
        this.stoneLabel.setText(String.valueOf(this.stoneInt));
        this.shieldLabel.setText(String.valueOf(this.shieldInt));
        this.servantLabel.setText(String.valueOf(this.servantInt));
        if(this.coinInt == 0){
            this.coinButton.setEnabled(false);
        }else{
            this.coinButton.setEnabled(true);
        }
        if(this.stoneInt == 0){
            this.stoneButton.setEnabled(false);
        }else{
            this.stoneButton.setEnabled(true);
        }
        if(this.shieldInt == 0){
            this.shieldButton.setEnabled(false);
        }else{
            this.shieldButton.setEnabled(true);
        }
        if(this.servantInt == 0){
            this.servantButton.setEnabled(false);
        }else{
            this.servantButton.setEnabled(true);
        }
        if(coinInt + stoneInt + servantInt + shieldInt == 0){
            takeMarketResourceBuilder.setMarketClear(true);
        }else{
            takeMarketResourceBuilder.setMarketClear(false);
        }
    }
    public void decreaseCoin(){
        this.coinInt --;
        refresh();
        this.stoneButton.setEnabled(false);
        this.shieldButton.setEnabled(false);
        this.servantButton.setEnabled(false);

    }

    public void decreaseStone() {
        this.stoneInt --;
        refresh();
        this.coinButton.setEnabled(false);
        this.shieldButton.setEnabled(false);
        this.servantButton.setEnabled(false);
    }

    public void decreaseShield() {
        this.shieldInt --;
        refresh();
        this.coinButton.setEnabled(false);
        this.stoneButton.setEnabled(false);
        this.servantButton.setEnabled(false);
    }

    public void decreaseServant() {
        this.servantInt --;
        refresh();
        this.coinButton.setEnabled(false);
        this.stoneButton.setEnabled(false);
        this.shieldButton.setEnabled(false);
    }
}
