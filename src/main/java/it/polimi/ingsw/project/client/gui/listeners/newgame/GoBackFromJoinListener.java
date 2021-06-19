package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.gui.NewGameHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoBackFromJoinListener implements ActionListener {
    private NewGameHandler newGameHandler;

    public GoBackFromJoinListener(NewGameHandler newGameHandler) {
        this.newGameHandler = newGameHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.newGameHandler.goToSelectGameType();
    }
}
