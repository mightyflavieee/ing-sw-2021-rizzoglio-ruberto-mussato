package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinGameListener implements ActionListener {
    private ClientGUI clientGUI;

    public JoinGameListener(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.clientGUI.createOrJoinGame();

        // todo andare nella waiting room (da ClientGUI forse?)
        // this.newGameHandler.goToWaitingRoom();

    }
}
