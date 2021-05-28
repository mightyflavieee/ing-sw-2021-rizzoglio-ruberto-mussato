package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;

public class LeaderMoveHandler extends JFrame {
    private LeaderCardGUI leaderCardGUI;
    private JButton discardButton, activateButton, exitButton;
    public LeaderMoveHandler(LeaderCardGUI leaderCardGUI)  {
        this.leaderCardGUI = leaderCardGUI;
        this.setVisible(true);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(4,1));
        this.add(new JLabel("What do you want to do?"));
        this.discardButton = new JButton("Discard LeaderCard");
        this.add(discardButton);
        this.activateButton = new JButton("Activate LeaderCard");
        this.add(activateButton);
        this.exitButton = new JButton("EXIT");
        this.add(exitButton);
        this.pack();
        this.setAlwaysOnTop(true);
    }
}
