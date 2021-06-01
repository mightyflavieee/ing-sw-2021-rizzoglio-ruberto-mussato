package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;

public class LeaderMovePanel extends JPanel {
    private String id;
    private JButton discardButton, activateButton;
    public LeaderMovePanel(String id)  {
        this.id = id;
        this.setVisible(true);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(1,2));
        this.discardButton = new JButton("Discard");
        this.add(discardButton);
        this.activateButton = new JButton("Activate");
        this.add(activateButton);
//        this.pack();
//        this.setAlwaysOnTop(true);
    }
}
