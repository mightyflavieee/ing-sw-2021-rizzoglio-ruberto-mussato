package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateButtonListener implements ActionListener {
    private ClientGUI clientGUI;
    private JRadioButton createRadioButton, joinRadioButton;

    public CreateButtonListener(ClientGUI clientGUI, JRadioButton createRadioButton, JRadioButton joinRadioButton) {
        this.clientGUI = clientGUI;
        this.createRadioButton = createRadioButton;
        this.joinRadioButton = joinRadioButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
     this.createRadioButton.setEnabled(false);
     this.joinRadioButton.setEnabled(false);
     this.clientGUI.setCreateGame(true);
    }
}
