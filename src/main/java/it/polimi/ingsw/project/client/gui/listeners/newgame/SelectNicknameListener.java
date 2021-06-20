package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectNicknameListener implements ActionListener {
    private ClientGUI clientGUI;
    private NewGameHandler newGameHandler;

    public SelectNicknameListener(ClientGUI clientGUI, NewGameHandler newGameHandler) {
        this.clientGUI = clientGUI;
        this.newGameHandler = newGameHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nickname = this.newGameHandler.getNicknameTextField().getText();
        this.clientGUI.setNickname(nickname);
        this.newGameHandler.goToSelectGameType();
    }
}
