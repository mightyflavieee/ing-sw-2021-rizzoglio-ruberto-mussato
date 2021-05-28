package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResourceInHandGUI extends JInternalFrame {
    //todo gestisce le risorse che ricevo dal market
    JButton coin, stone, shield, servant;

    public ResourceInHandGUI() {
        this.setTitle("Resources in Hand");
        this.setLayout(new GridLayout(1,4));
        this.coin = new JButton();
        this.coin.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Coin.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(coin);
        this.stone = new JButton();
        this.stone.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Stone.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(stone);
        this.shield = new JButton();
        this.shield.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Shield.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(shield);
        this.servant = new JButton();
        this.servant.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/Servant.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.add(servant);
        this.setVisible(true);
        this.pack();
    }



    public void refresh(List<Resource> resourceList){

    }
}
