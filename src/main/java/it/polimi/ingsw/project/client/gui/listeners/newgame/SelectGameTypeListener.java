package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * it is used to select if you want to join or create a game
 */
public class SelectGameTypeListener implements ActionListener {
    private final ClientGUI clientGUI;
    private final NewGameHandler newGameHandler;
    private final boolean isCreatingGame;

    /**
     * when the listener is created, the field isCreatingGame is put to true if it is listening to the create button
     * and it is put to false if it is listening to the join button
     */
    public SelectGameTypeListener(ClientGUI clientGUI, NewGameHandler newGameHandler, boolean isCreatingGame) {
        this.clientGUI = clientGUI;
        this.newGameHandler = newGameHandler;
        this.isCreatingGame = isCreatingGame;
    }

    /**
     * if you want to create a game it ensures that you selected the number of the players and store the information
     */
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


        } else {
            this.newGameHandler.goTOSelectJoinGameID();
        }
    }
}
