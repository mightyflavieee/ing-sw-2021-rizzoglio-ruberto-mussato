package it.polimi.ingsw.project.client.gui.listeners.endgame;

import it.polimi.ingsw.project.client.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * used at the end of the game when the scoreboard is displayed
 */
public class StartNewGameListener implements ActionListener {
    private final ClientGUI clientGUI;

    public StartNewGameListener(ClientGUI clientGUI) { this.clientGUI = clientGUI; }

    /**
     * it makes you start a new game showing the login GUI
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.clientGUI.restart();
    }
}
