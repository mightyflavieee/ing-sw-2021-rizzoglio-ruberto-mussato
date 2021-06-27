package it.polimi.ingsw.project.client.gui.listeners.login;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InsertNameListener implements ActionListener {
    private final ClientGUI clientGUI;
    private final JTextField nicknameField;
    private final List<JComponent> successiveButtons;

    public InsertNameListener(ClientGUI clientGUI, JTextField nicknameField, List<JComponent> successiveButtons) {
        this.clientGUI = clientGUI;
        this.nicknameField = nicknameField;
        this.successiveButtons = successiveButtons;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nicknameField.setEnabled(false);
        clientGUI.setNickname(nicknameField.getText());
        this.successiveButtons.forEach(x -> x.setVisible(true));

    }
}
