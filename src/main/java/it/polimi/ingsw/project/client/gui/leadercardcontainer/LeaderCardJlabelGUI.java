package it.polimi.ingsw.project.client.gui.leadercardcontainer;

import javax.swing.*;
import java.awt.*;

public class LeaderCardJlabelGUI extends JLabel {
    private String id;
    private LeaderCardChoserGUI leaderCardChoserGUI;

    public LeaderCardJlabelGUI(String id) { //used for the leadercard place
        this.id = id;
        this.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/leadercards/"+ this.id + ".png").getImage().getScaledInstance(210, 350, Image.SCALE_SMOOTH)));
        //  this.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/plancia portabiglie.png").getImage().getScaledInstance(380, 500, Image.SCALE_SMOOTH)));
        //  this.addActionListener(new LeaderCardGUIListener(this));
        this.setVisible(true);
    }

//    public LeaderCardButtonGUI(String id, LeaderCardChoserGUI leaderCardChoserGUI) { //used at the beginning of the game when you chose 2 leadercards
//        this.id = id;
//        this.leaderCardChoserGUI = leaderCardChoserGUI;
//        this.setVisible(true);
//        this.refresh();
//    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
        this.refresh();
    }

    private void refresh() {
        this.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/leadercards/"+ this.id + ".png").getImage().getScaledInstance(230, 348, Image.SCALE_SMOOTH)));

    }

}
