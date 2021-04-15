package it.polimi.ingsw.project.view;

import it.polimi.ingsw.project.model.*;
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

    void handleMove(MoveType moveType, List<Resource> resources) {
        //print lato server che stampa la mossa fatta
        notify(new PlayerMove(player, moveType, resources, this));
    }

    public void reportError(String message){
        showMessage(message);
    }

}
