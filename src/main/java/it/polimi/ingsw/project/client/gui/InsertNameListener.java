package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertNameListener implements ActionListener {
    private ClientGUI clientGUI;
    private JTextField jTextField;

    public InsertNameListener(ClientGUI clientGUI, JTextField jTextField) {
        this.clientGUI = clientGUI;
        this.jTextField = jTextField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        jTextField.setEnabled(false);
        clientGUI.setNickname(jTextField.getText());

    }
}
