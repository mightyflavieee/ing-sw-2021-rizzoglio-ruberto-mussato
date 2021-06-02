package it.polimi.ingsw.project.client.gui.listeners;

import javax.swing.*;

public class ResourceInHandlerGUI extends JInternalFrame {
    private JPanel jPanel;
  //  coinPanel, stonePanel, shieldPanel, servantPanel;
    private JButton firstShelfButton, secondShelfButton, thirdShelfButton, discardButton;
    private int coinInt, stoneInt, shieldInt, servantInt;
    public ResourceInHandlerGUI() {
        this.setTitle("Resource Selected:");
        this.setVisible(false);
    }
}
