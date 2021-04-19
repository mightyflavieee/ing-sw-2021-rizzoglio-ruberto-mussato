package it.polimi.ingsw.project.view;

import it.polimi.ingsw.project.model.*;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.Observer;

import java.util.List;

public abstract class View extends Observable<PlayerMove> implements Observer<MoveMessage> {

    private Player player;

    protected View(Player player){
        this.player = player;
    }

    protected Player getPlayer(){
        return player;
    }

    protected abstract void showMessage(Object message);

    void handleMove(Move move) {
        //stampo nel server la mossa che sto facendo, e poi la passo al controller
        System.out.println(move.toString());

        notify(new PlayerMove(this.player, this, move));
    }

    public void reportError(String message){
        showMessage(message);
    }

}
