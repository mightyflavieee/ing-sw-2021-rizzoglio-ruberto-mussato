package it.polimi.ingsw.project.controller;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.observer.Observer;
import it.polimi.ingsw.project.utils.gameMessage;

public class Controller implements Observer<PlayerMove> {

    private final Model model;

    public Controller(Model model){
        super();
        this.model = model;
    }

    private synchronized void performMove(PlayerMove playerMove){
        //TODO tipi di messaggi
        if (!model.isPlayerTurn(playerMove.getPlayer())) {
            playerMove.getView().reportError(gameMessage.wrongTurnMessage);
            return;
        }
        if(!model.isRightTurnPhase(playerMove)){
            playerMove.getView().reportError(gameMessage.wrongPhaseMessage);
            return;
        }
        // it performs moves one by one

            if (!model.isFeasibleMove(playerMove)) {
                model.notifyPartialMove();
                //  playerMove.getView().reportError(gameMessage.occupiedCellMessage);
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
