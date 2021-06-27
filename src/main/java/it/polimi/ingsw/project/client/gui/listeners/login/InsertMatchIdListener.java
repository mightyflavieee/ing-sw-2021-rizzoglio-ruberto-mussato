package it.polimi.ingsw.project.client.gui.listeners.login;


import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertMatchIdListener implements ActionListener {
    private final ClientGUI clientGUI;
    private final JTextField idField;
    private final JButton submitButton;

    public InsertMatchIdListener(ClientGUI clientGUI, JTextField idField,JButton submitButton) {
        this.clientGUI = clientGUI;
        this.idField = idField;
        this.submitButton = submitButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.idField.setEnabled(false);
        this.clientGUI.setLocalGameID(this.idField.getText());
        this.submitButton.setVisible(true);

    }
}
