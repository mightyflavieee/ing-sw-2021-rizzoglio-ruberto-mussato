package it.polimi.ingsw.project.client.gui.listeners.endgame;

import it.polimi.ingsw.project.client.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartNewGameListener implements ActionListener {
    private ClientGUI clientGUI;

    public StartNewGameListener(ClientGUI clientGUI) { this.clientGUI = clientGUI; }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.clientGUI.restart();
    }
}
