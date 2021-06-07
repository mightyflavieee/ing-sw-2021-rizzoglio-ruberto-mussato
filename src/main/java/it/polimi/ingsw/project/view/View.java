package it.polimi.ingsw.project.view;

import it.polimi.ingsw.project.messages.MoveMessage;
import it.polimi.ingsw.project.messages.ResponseMessage;
import it.polimi.ingsw.project.model.*;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.model.playermove.interfaces.Controllable;
import it.polimi.ingsw.project.model.playermove.interfaces.MoveHandler;
import it.polimi.ingsw.project.model.playermove.interfaces.Request;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.observer.Observer;

public abstract class View extends Observable<MoveHandler> implements Observer<MoveMessage> {

    private final Player player;

    protected View(Player player) {
        this.player = player;
    }

    protected Player getPlayer() {
        return player;
    }

    protected abstract void showMessage(MoveMessage message);

    void handleMove(Controllable requestedMove) {
        // stampo nel server la mossa che sto facendo, e poi la passo al controller
        requestedMove.notifyMoveToController(this.player, this, requestedMove);
    }

}
