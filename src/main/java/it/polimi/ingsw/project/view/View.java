package it.polimi.ingsw.project.view;

import it.polimi.ingsw.project.messages.MoveMessage;
import it.polimi.ingsw.project.model.*;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.Observer;

public abstract class View extends Observable<PlayerMove> implements Observer<MoveMessage> {

    private final Player player;

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
