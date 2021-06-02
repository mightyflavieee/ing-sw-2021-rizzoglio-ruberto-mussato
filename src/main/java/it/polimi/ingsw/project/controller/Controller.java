package it.polimi.ingsw.project.controller;

import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.observer.Observer;

public class Controller implements Observer<PlayerMove> {

    private final Model model;

    public Controller(Model model) {
        super();
        this.model = model;
    }

    public Match getMatchCopy() {
        return this.model.getMatchCopy();
    }

    private synchronized void performMove(PlayerMove playerMove) {
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

    @Override
    public void update(PlayerMove message) {
        performMove(message);
    }

}
