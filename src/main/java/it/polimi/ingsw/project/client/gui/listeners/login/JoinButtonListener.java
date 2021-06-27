package it.polimi.ingsw.project.client.gui.listeners.login;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinButtonListener implements ActionListener {
    private final ClientGUI clientGUI;
    private final JRadioButton createRadioButton;
    private final JRadioButton joinRadioButton;
    private final JTextField idField;
    private final JLabel idLabel;

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
