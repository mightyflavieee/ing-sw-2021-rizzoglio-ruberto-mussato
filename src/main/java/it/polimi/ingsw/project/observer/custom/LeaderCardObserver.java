package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.Board;
import it.polimi.ingsw.project.model.Perk;
import it.polimi.ingsw.project.observer.Observer;

public class LeaderCardObserver implements Observer {
    private Board board;

    public LeaderCardObserver(Board board) {
        this.board = board;
    }

    @Override
    public void update() {}

    // overloading to update activePerks list in it.polimi.ingsw.project.model.Board
    public void update(Perk perk) {
        this.board.getActivePerks().add(perk);
    }
}
