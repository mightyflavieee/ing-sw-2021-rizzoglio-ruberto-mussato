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

    public boolean isFeasibleMove(PlayerMove playerMove){
        return playerMove.isFeasibleMove(this.match);
    }

    public void performMove(PlayerMove playerMove){
        playerMove.performMove(this.match);
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
