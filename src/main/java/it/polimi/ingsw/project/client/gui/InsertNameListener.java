package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertNameListener implements ActionListener {
    private ClientGUI clientGUI;
    private JTextField nicknameField;

    public InsertNameListener(ClientGUI clientGUI, JTextField nicknameField) {
        this.clientGUI = clientGUI;
        this.nicknameField = nicknameField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nicknameField.setEnabled(false);
        clientGUI.setNickname(nicknameField.getText());

    }
}
