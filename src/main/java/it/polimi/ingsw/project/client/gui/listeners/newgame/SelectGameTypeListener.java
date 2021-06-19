package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.gui.NewGameHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectGameTypeListener implements ActionListener {
    private NewGameHandler newGameHandler;
    private boolean isCreatingGame;

    public SelectGameTypeListener(NewGameHandler newGameHandler, boolean isCreatingGame) {
        this.newGameHandler = newGameHandler;
        this.isCreatingGame = isCreatingGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isCreatingGame) {

            // todo settare parametri per messaggio a server

            this.newGameHandler.goToWaitingRoom();
        } else {

            // todo settare parametri per messaggio a server

            this.newGameHandler.goTOSelectJoinGameID();
        }
    }
}
