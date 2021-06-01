package it.polimi.ingsw.project.client.gui.listeners;


import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertMatchIdListener implements ActionListener {
    private ClientGUI clientGUI;
    private JTextField idField;

    public InsertMatchIdListener(ClientGUI clientGUI, JTextField idField) {
        this.clientGUI = clientGUI;
        this.idField = idField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.idField.setEnabled(false);
        this.clientGUI.setLocalGameID(this.idField.getText());

    }
}
