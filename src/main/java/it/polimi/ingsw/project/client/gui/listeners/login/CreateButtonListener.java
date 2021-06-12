package it.polimi.ingsw.project.client.gui.listeners.login;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateButtonListener implements ActionListener {
    private ClientGUI clientGUI;
    private JRadioButton createRadioButton, joinRadioButton;
    private JButton submitButton;

    public CreateButtonListener(ClientGUI clientGUI, JRadioButton createRadioButton, JRadioButton joinRadioButton, JButton submitButton) {
        this.clientGUI = clientGUI;
        this.createRadioButton = createRadioButton;
        this.joinRadioButton = joinRadioButton;
        this.submitButton = submitButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
     this.createRadioButton.setEnabled(false);
     this.joinRadioButton.setEnabled(false);
     this.clientGUI.setCreateGame(true);
     this.submitButton.setVisible(true);
    }
}
