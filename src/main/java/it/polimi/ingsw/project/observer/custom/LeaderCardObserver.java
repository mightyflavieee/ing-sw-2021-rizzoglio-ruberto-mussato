package it.polimi.ingsw.project.observer.custom;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.observer.Observer;

public class LeaderCardObserver implements Observer<Perk> {
    private Board board;

    public LeaderCardObserver(Board board) {
        this.board = board;
    }


    @Override
    public void update(Perk message) {
        this.board.getActivePerks().add(message);
    }

}
