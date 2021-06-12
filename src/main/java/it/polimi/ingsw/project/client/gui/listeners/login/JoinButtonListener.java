package it.polimi.ingsw.project.client.gui.listeners.login;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinButtonListener implements ActionListener {
    private ClientGUI clientGUI;
    private JRadioButton createRadioButton, joinRadioButton;
    private JTextField idField;
    private JLabel idLabel;

    public JoinButtonListener(ClientGUI clientGUI, JRadioButton createRadioButton, JRadioButton joinRadioButton, JTextField idField, JLabel idLabel) {
        this.clientGUI = clientGUI;
        this.createRadioButton = createRadioButton;
        this.joinRadioButton = joinRadioButton;
        this.idField = idField;
        this.idLabel = idLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.createRadioButton.setEnabled(false);
        this.joinRadioButton.setEnabled(false);
        this.clientGUI.setCreateGame(false);
        this.idField.setVisible(true);
        this.idField.setEnabled(true);
        this.idLabel.setVisible(true);
    }
}
