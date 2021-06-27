package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectNicknameListener implements ActionListener {
    private final ClientGUI clientGUI;
    private final NewGameHandler newGameHandler;

    public SelectNicknameListener(ClientGUI clientGUI, NewGameHandler newGameHandler) {
        this.clientGUI = clientGUI;
        this.newGameHandler = newGameHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nickname = this.newGameHandler.getNicknameTextField().getText();
        if(!nickname.isEmpty()) {
            this.clientGUI.setNickname(nickname);
            this.newGameHandler.goToSelectGameType();
        }
    }
}
