package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.Board;

public class MoveMessage {  //messaggio che rimando al player che viene poi visualizzato

    private final Player player;


    MoveMessage(Board board, Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


}
