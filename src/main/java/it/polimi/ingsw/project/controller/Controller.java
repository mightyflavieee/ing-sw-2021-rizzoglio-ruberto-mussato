package it.polimi.ingsw.project.controller;

import it.polimi.ingsw.project.model.playermove.interfaces.MoveHandler;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.observer.Observer;

public class Controller implements Observer<MoveHandler> {

    private final Model model;

    public Controller(Model model) {
        super();
        this.model = model;
    }

    private synchronized void performMove(MoveHandler handableMove) {
        handableMove.handleMove(this.model, handableMove);
    }

    @Override
    public void update(MoveHandler message) {
        performMove(message);
    }

}
