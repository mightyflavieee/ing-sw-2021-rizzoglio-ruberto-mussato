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
        return player.getNickname().equals(match.getCurrentPlayer().getNickname());
    }

    public boolean isFeasibleMove(PlayerMove playerMove, int i){
        return playerMove.isFeasibleMove(this.match, i);
    }

    public void performMove(PlayerMove playerMove, int i){
        playerMove.performMove(this.match, i);
        this.match.end();
    }


    public void updateTurn(){
        match.updatePlayer();
        notify(new MoveMessage(this.match.clone())); //è il messaggio che verrà inviato a l player
    }
    public void notifyPartialMove(){
        notify(new MoveMessage(this.match.clone())); //è il messaggio che verrà inviato a l player
    }
    public Match getMatchCopy(){
        return this.match.clone();
    }
}
