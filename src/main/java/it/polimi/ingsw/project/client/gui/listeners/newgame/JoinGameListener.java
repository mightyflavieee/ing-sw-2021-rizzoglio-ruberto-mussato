package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinGameListener implements ActionListener {
    private ClientGUI clientGUI;
    private NewGameHandler newGameHandler;

    public JoinGameListener(ClientGUI clientGUI, NewGameHandler newGameHandler) {
        this.clientGUI = clientGUI;
        this.newGameHandler = newGameHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!newGameHandler.getSelectJoinGameIDTextField().getText().isEmpty()) {
            this.clientGUI.setGameId(newGameHandler.getSelectJoinGameIDTextField().getText());
            this.clientGUI.createOrJoinGame();
            this.newGameHandler.goToWaitingRoom(this.clientGUI.getGameId());
        }
    }
}
