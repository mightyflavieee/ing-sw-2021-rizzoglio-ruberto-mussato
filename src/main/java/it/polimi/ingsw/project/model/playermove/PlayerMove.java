package it.polimi.ingsw.project.model.playermove;

import java.io.Serializable;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.interfaces.MoveHandler;
import it.polimi.ingsw.project.view.View;

public class PlayerMove implements Serializable, MoveHandler {
    private final Player player;
    private final View view;
    private final Move move;

    public PlayerMove(Player player, View view, Move move) {
        this.player = player;
        this.view = view;
        this.move = move;
    }

    public Player getPlayer() {
        return player;
    }

    public View getView() {
        return view;
    }

    public void performMove(Match match) {
        this.move.performMove(match);
        this.updateHistory();
    }

    public boolean isFeasibleMove(Match match) {
        return this.move.isFeasibleMove(match);
    }

    public boolean isMainMove() {
        return this.move.isMainMove();
    }

    private void updateHistory() {
        this.player.updateHistory(this.move.toString());
    }

    @Override
    public void handleMove(Model model, MoveHandler requestedMove) {
        PlayerMove playerMove = (PlayerMove) requestedMove;
        if (!model.isPlayerTurn(playerMove.getPlayer())) {
            model.notifyPartialMove();
            return;
        }
        if (!model.isRightTurnPhase(playerMove)) {
            model.notifyPartialMove();
            return;
        }
        // it performs moves one by one

        if (!model.isFeasibleMove(playerMove)) {
            model.notifyPartialMove();
            // playerMove.getView().reportError(gameMessage.occupiedCellMessage);
            return;
        }
        model.performMove(playerMove);

        // the turn is updated after all moves have been performed
        model.updateTurn();

    }
}
