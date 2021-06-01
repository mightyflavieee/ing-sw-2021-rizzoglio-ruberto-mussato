package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.LeaderCardActivateButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.LeaderCardDiscardButtonListener;

import javax.swing.*;
import java.awt.*;

public class LeaderMovePanel extends JPanel {
    private String id;
    private JButton discardButton, activateButton;
    public LeaderMovePanel(String id, GUI gui)  {
        this.id = id;
        this.setVisible(true);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(1,2));
        this.discardButton = new JButton("Discard");
        this.discardButton.addActionListener(new LeaderCardDiscardButtonListener(id,gui));
        this.add(discardButton);
        this.activateButton = new JButton("Activate");
        this.activateButton.addActionListener(new LeaderCardActivateButtonListener(id,gui));
        this.add(activateButton);
//        this.pack();
//        this.setAlwaysOnTop(true);
    }
    public void setEnabled(Boolean value){
        super.setEnabled(value);
        this.discardButton.setEnabled(value);
        this.activateButton.setEnabled(value);
    }
    public void setActivationPossible(Boolean value){
        this.activateButton.setEnabled(value);
    }
}
