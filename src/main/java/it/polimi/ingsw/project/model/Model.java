package it.polimi.ingsw.project.model;

import java.util.List;

import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.observer.Observable;

public class Model extends Observable<MoveMessage> {
    private Match match;

    public Model(List<Player> listOfPlayers) {
        this.match = new Match(listOfPlayers);
    }

    public boolean isPlayerTurn(Player player) {
        return player.getNickname() == match.getCurrentPlayer().getNickname();
    }

    public boolean isFeasibleMove(PlayerMove playerMove){
        return playerMove.isFeasibleMove(this.match);
    }

    public void performMove(PlayerMove playerMove){
        playerMove.performMove(this.match);
        //Match.playGame non va bene
        //da modificare per MVC
       /*
        while (true) {
            boolean endGame = this.match.performMove(move);
            if (endGame) {
                this.match.playLastTurn();
                break;
            }
            this.updateTurn();
        }

        */
       /* board.setCell(row, column, player.getMarker());
        boolean hasWon = board.isGameOver(player.getMarker());
        //messaggio mandato al player
        notify(new MoveMessage(board.clone(), player));
        if(hasWon || board.isFull()){
            board.reset();
        }
        */
        this.match.soloGame();
        this.match.end();
        notify(new MoveMessage(playerMove.getPlayer(), this.match.clone())); //è il messaggio che verrà inviato a l player
    }


    public void updateTurn(){
        match.updatePlayer();
    }
    public Match getMatchCopy(){
        return this.match.clone();
    }
}
