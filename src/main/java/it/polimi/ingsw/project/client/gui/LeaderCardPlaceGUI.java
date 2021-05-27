package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;

public class LeaderCardPlaceGUI extends JInternalFrame {
    private LeaderCardGui leaderCardGui1, leaderCardGui2;
    public LeaderCardPlaceGUI() {
        leaderCardGui1 = new LeaderCardGui("id1");
        leaderCardGui2 = new LeaderCardGui("id2");
        this.setTitle("My LeaderCards");
        this.setLayout(new GridLayout(1,2));
        this.add(leaderCardGui1);
        this.add(leaderCardGui2);
        this.setVisible(true);
    }
}
