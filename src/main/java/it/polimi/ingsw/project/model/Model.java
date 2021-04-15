package it.polimi.ingsw.project.model;

import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.project.observer.Observable;

public class Model extends Observable<MoveMessage> {
    private Match match;
    private Player playerToSend = new Player();

    public Model(List<Player> listOfPlayers) {
        this.match = new Match(listOfPlayers);
    }

    public boolean isPlayerTurn(Player player) {
        return player.getNickname() == match.getCurrentPlayer().getNickname();
    }

    public Player getPlayerCopy() {
        return playerToSend.clone();
    }

    public boolean isFeasibleMove(PlayerMove move){
        //da fare
        return false;
    }

    public void performMove(PlayerMove move){
        // da fare
        //Match.playGame non va bene
        //da modificare per MVC
        while (true) {
            boolean endGame = this.match.performMove(move);
            if (endGame) {
                this.match.playLastTurn();
                break;
            }
            this.updatePlayer();
        }


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
