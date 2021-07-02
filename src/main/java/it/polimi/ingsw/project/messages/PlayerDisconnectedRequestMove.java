package it.polimi.ingsw.project.messages;

import it.polimi.ingsw.project.messages.GameRequestMove;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.interfaces.MoveHandler;

public class PlayerDisconnectedRequestMove extends GameRequestMove implements MoveHandler {
    private final Player disconnectedPlayer;

    /**
     * it construct the move with player that has just disconnected
     *
     * @param disconnectedPlayer the player that just disconnected
     */
    public PlayerDisconnectedRequestMove(Player disconnectedPlayer) {
        this.disconnectedPlayer = disconnectedPlayer;
    }

    /**
     * it calls the function on the model to make the player skip the turn for the disconnection
     * @param model it is passed by the controller to update the connection status on the disconnected player
     * @param requestedMove it is not used in this case
     */
    @Override
    public void handleMove(Model model, MoveHandler requestedMove) {
        model.playerSkipTurn(disconnectedPlayer);
    }
}
