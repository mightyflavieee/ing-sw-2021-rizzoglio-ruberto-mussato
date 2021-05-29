package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;

public class LeaderCardGUI extends JButton {
    private String id;

    public LeaderCardGUI(String id) {
        this.id = id;
        this.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/leadercards/"+ this.id + ".png").getImage().getScaledInstance(230, 348, Image.SCALE_SMOOTH)));
      //  this.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/plancia portabiglie.png").getImage().getScaledInstance(380, 500, Image.SCALE_SMOOTH)));
        this.addActionListener(new LeaderCardGUIListener(this));
        this.setVisible(true);
    }
}
