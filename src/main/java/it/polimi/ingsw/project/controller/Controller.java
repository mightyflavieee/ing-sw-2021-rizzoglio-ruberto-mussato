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
        // it performs moves one by one
        for(int i = 0; i < playerMove.getSize(); i++) {

            if (!model.isFeasibleMove(playerMove, i)) {
                //  playerMove.getView().reportError(gameMessage.occupiedCellMessage);
                return;
            }
            model.performMove(playerMove, i);
        }
        // the turn is updated after all moves have been performed
        model.updateTurn(playerMove);
    }

    @Override
    public void update(PlayerMove message) {
        performMove(message);
    }



}
