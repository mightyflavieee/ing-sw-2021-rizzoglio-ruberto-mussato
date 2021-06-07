package it.polimi.ingsw.project.controller;

import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.model.playermove.interfaces.HandableMove;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.observer.Observer;

public class Controller implements Observer<HandableMove> {

    private final Model model;

    public Controller(Model model) {
        super();
        this.model = model;
    }

    public Match getMatchCopy() {
        return this.model.getMatchCopy();
    }

    private synchronized void performMove(HandableMove handableMove) {
        handableMove.handleMove(this.model, handableMove);
    }

    @Override
    public void update(HandableMove message) {
        performMove(message);
    }

}
