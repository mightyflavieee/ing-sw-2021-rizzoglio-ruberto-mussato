package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.observer.Observable;

import java.util.List;

public class Model extends Observable<MoveMessage> {
    private Match match;
    private Player playerToSend = new Player();

    public boolean isPlayerTurn(Player player) {
        return player.getNickname() == match.getCurrentPlayer().getNickname();
    }

    public Player getPlayerCopy(){
        return playerToSend.clone();
    }

    public boolean isFeasibleMove(PlayerMove move){
        //da fare
        return false;
    }

    public void performMove(PlayerMove move){
        // da fare
        //Match.playGame non va bene
       /* board.setCell(row, column, player.getMarker());
        boolean hasWon = board.isGameOver(player.getMarker());
        notify(new MoveMessage(board.clone(), player));
        if(hasWon || board.isFull()){
            board.reset();
        }
        */
    }

    public void updateTurn(){
        match.updatePlayer();
    }

}
