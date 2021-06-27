package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectGameTypeListener implements ActionListener {
    private final ClientGUI clientGUI;
    private final NewGameHandler newGameHandler;
    private final boolean isCreatingGame;

    public SelectGameTypeListener(ClientGUI clientGUI, NewGameHandler newGameHandler, boolean isCreatingGame) {
        this.clientGUI = clientGUI;
        this.newGameHandler = newGameHandler;
        this.isCreatingGame = isCreatingGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.clientGUI.setCreateGame(isCreatingGame);
        boolean didSelect = false;
        for (JRadioButton radioButton : this.newGameHandler.getRadioButtons()) {
            didSelect = didSelect || radioButton.isSelected();
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
            if(!didSelect)
                return;
            this.clientGUI.createOrJoinGame();

            // todo andare nella waiting room (da ClientGUI forse?)
            // this.newGameHandler.goToWaitingRoom();

        } else {
            this.newGameHandler.goTOSelectJoinGameID();
        }
    }
}
