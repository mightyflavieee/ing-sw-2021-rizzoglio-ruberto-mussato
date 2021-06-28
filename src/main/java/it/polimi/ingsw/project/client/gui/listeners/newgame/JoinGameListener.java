package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to send a join request to the server at the beginning of the game
 */
public class JoinGameListener implements ActionListener {
    private final ClientGUI clientGUI;
    private final NewGameHandler newGameHandler;

    public JoinGameListener(ClientGUI clientGUI, NewGameHandler newGameHandler) {
        this.clientGUI = clientGUI;
        this.newGameHandler = newGameHandler;
    }

    /**
     * it sends a join request if you actually wrote an id
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!newGameHandler.getSelectJoinGameIDTextField().getText().isEmpty()) {
            this.clientGUI.setGameId(newGameHandler.getSelectJoinGameIDTextField().getText());
            this.clientGUI.createOrJoinGame();
            this.newGameHandler.goToWaitingRoom(this.clientGUI.getGameId());
        }
    }
}
