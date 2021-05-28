package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResourceInHandGUI extends JInternalFrame {
    //todo gestisce le risorse che ricevo dal market
    private JButton coinButton, stoneButton, shieldButton, servantButton;
    private JLabel coinLabel, stoneLabel, shieldLabel, servantLabel;
    private int coinInt, stoneInt, shieldInt, servantInt;
    public ResourceInHandGUI() {
        this.setTitle("Resources in Hand");
        this.setLayout(new GridLayout(2,4));
        this.coinButton = new JButton();
        this.coinButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Coin.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(coinButton);
        this.stoneButton = new JButton();
        this.stoneButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Stone.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(stoneButton);
        this.shieldButton = new JButton();
        this.shieldButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Shield.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(shieldButton);
        this.servantButton = new JButton();
        this.servantButton.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Servant.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(servantButton);
        this.coinLabel = new JLabel(String.valueOf(0));
        this.add(coinLabel);
        this.stoneLabel = new JLabel(String.valueOf(0));
        this.add(stoneLabel);
        this.shieldLabel = new JLabel(String.valueOf(0));
        this.add(shieldLabel);
        this.servantLabel = new JLabel(String.valueOf(0));
        this.add(servantLabel);
        this.setVisible(true);
        this.pack();
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
    }
}
