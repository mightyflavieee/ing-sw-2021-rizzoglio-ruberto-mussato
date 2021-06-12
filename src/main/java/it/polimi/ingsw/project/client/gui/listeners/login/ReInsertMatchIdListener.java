package it.polimi.ingsw.project.client.gui.listeners.login;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReInsertMatchIdListener implements ActionListener {
    private ClientGUI clientGUI;
    private JTextField idField;
    private JFrame menuFrame;

    public ReInsertMatchIdListener(ClientGUI clientGUI, JTextField idField,JFrame menuFrame) {
        this.clientGUI = clientGUI;
        this.idField = idField;
        this.menuFrame = menuFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.idField.setEnabled(false);
        this.clientGUI.setLocalGameID(this.idField.getText());
        menuFrame.dispose();
        this.clientGUI.createOrJoinGame();
    }
}
