package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;

public class LeaderCardPlaceGUI extends JInternalFrame {
    private LeaderCardGUI leaderCardGUI1, leaderCardGUI2;
    public LeaderCardPlaceGUI() {
        leaderCardGUI1 = new LeaderCardGUI("id1");
        leaderCardGUI2 = new LeaderCardGUI("id2");
        this.setTitle("My LeaderCards");
        this.setLayout(new GridLayout(1,2));
        this.add(leaderCardGUI1);
        this.add(leaderCardGUI2);
        this.setVisible(true);
    }


}
