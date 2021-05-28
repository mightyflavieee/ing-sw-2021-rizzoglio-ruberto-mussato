package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.market.MarbleType;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResourceInHandGUI extends JInternalFrame {
    //todo gestisce le risorse che ricevo dal market
    JButton coin, stone, shield, servant;
    JLabel intCoin, intStone, intShield, intServant;
    public ResourceInHandGUI() {
        this.setTitle("Resources in Hand");
        this.setLayout(new GridLayout(2,4));
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
        this.intCoin = new JLabel(String.valueOf(0));
        this.add(intCoin);
        this.intStone = new JLabel(String.valueOf(0));
        this.add(intStone);
        this.intShield = new JLabel(String.valueOf(0));
        this.add(intShield);
        this.intServant = new JLabel(String.valueOf(0));
        this.add(intServant);
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
        this.intCoin.setText(String.valueOf(coin));
        this.intStone.setText(String.valueOf(stone));
        this.intShield.setText(String.valueOf(shield));
        this.intServant.setText(String.valueOf(servant));
    }
}
