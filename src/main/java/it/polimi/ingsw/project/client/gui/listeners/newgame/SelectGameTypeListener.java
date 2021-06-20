package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectGameTypeListener implements ActionListener {
    private ClientGUI clientGUI;
    private NewGameHandler newGameHandler;
    private boolean isCreatingGame;

    public SelectGameTypeListener(ClientGUI clientGUI, NewGameHandler newGameHandler, boolean isCreatingGame) {
        this.clientGUI = clientGUI;
        this.newGameHandler = newGameHandler;
        this.isCreatingGame = isCreatingGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JRadioButton radioButton : this.newGameHandler.getRadioButtons()) {
            if (radioButton.isSelected()) {
                switch (radioButton.getText()) {
                    case "Single Player":
                        this.clientGUI.setNumPlayers(1);
                        break;
                    case "2 Players":
                        this.clientGUI.setNumPlayers(2);
                        break;
                    case "3 Players":
                        this.clientGUI.setNumPlayers(3);
                        break;
                    case "4 Players":
                        this.clientGUI.setNumPlayers(4);
                        break;
                }
            }
        }
        if (isCreatingGame) {
            this.clientGUI.setCreateGame(true);
            this.clientGUI.createOrJoinGame();

            // todo andare nella waiting room (da ClientGUI forse?)
            // this.newGameHandler.goToWaitingRoom();

        } else {
            this.clientGUI.setCreateGame(false);
            this.newGameHandler.goTOSelectJoinGameID();
        }
    }
}
