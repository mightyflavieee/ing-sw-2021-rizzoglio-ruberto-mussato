package it.polimi.ingsw.project.client.gui.leadercardcontainer;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.listeners.LeaderCardActivateButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.LeaderCardDiscardButtonListener;

import javax.swing.*;
import java.awt.*;

public class LeaderMovePanel extends JPanel {
    private String id;
    private JButton discardButton, activateButton;
    private boolean isActivable;
    private LeaderCardDiscardButtonListener leaderCardDiscardButtonListener;
    private LeaderCardActivateButtonListener leaderCardActivateButtonListener;
    public LeaderMovePanel(String id, GUI gui)  {
        this.id = id;
        this.setVisible(true);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(1,2));
        this.discardButton = new JButton("Discard");
        this.leaderCardDiscardButtonListener = new LeaderCardDiscardButtonListener(this.id,gui);
        this.discardButton.addActionListener(this.leaderCardDiscardButtonListener);
        this.add(discardButton);
        this.activateButton = new JButton("Activate");
        this.leaderCardActivateButtonListener = new LeaderCardActivateButtonListener(this.id,gui);
        this.activateButton.addActionListener(this.leaderCardActivateButtonListener);
        this.add(activateButton);
        this.isActivable = true;
//        this.pack();
//        this.setAlwaysOnTop(true);
    }

    public void setActivationPossible(Boolean value){
        this.activateButton.setEnabled(value);
        this.isActivable = value;
    }

    public void disableButtons() {
        this.discardButton.setEnabled(false);
        this.activateButton.setEnabled(false);
    }

    public void enableButtons() {
        this.discardButton.setEnabled(true);
        this.activateButton.setEnabled(this.isActivable);
    }

    public void setID(String id) {
        this.leaderCardActivateButtonListener.setID(id);
        this.leaderCardDiscardButtonListener.setID(id);
    }
}
