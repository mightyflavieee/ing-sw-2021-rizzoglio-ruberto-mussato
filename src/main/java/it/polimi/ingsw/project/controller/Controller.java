package it.polimi.ingsw.project.controller;
import it.polimi.ingsw.project.model.PlayerMove;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.observer.Observer;

import java.util.Collections;

public class Controller implements Observer<PlayerMove> {

    private final Model model;

    public Controller(Model model){
        super();
        this.model = model;
    }

    private synchronized void performMove(PlayerMove move){
        //da finire
        if(!model.isPlayerTurn(move.getPlayer())){
        //    move.getView().reportError(gameMessage.wrongTurnMessage);
            return;
        }
        if(!model.isFeasibleMove(move)){
        //    move.getView().reportError(gameMessage.occupiedCellMessage);
            return;
        }
        model.performMove(move);
        model.updateTurn();
    }

    @Override
    public void update(PlayerMove message) {
        performMove(message);
    }


    // DA ELIMINARE (quando si ripensa observer)
    @Override
    public void update() {}

}
