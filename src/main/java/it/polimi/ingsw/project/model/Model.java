package it.polimi.ingsw.project.model;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.project.messages.MoveMessage;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.observer.Observable;

public class Model extends Observable<MoveMessage> implements Serializable{
    private final Match match;

    public Model(List<Player> listOfPlayers) {
        this.match = new Match(listOfPlayers);
    }

    public Match getMatch() {
        return match;
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
    }


    public void updateTurn(){
        if(match.getPlayerList().size()==1 && match.getCurrentPlayer().getTurnPhase() == TurnPhase.EndPhase){
            match.performExtractActionTokenMove();
        }
        match.updatePlayer();
        notify(new MoveMessage(this.match.clone())); //è il messaggio che verrà inviato a l player
    }
    public void notifyPartialMove(){
        notify(new MoveMessage(this.match.clone())); //è il messaggio che verrà inviato a l player
    }
    public Match getMatchCopy(){
        return this.match.clone();
    }

    public boolean isRightTurnPhase(PlayerMove playerMove){
        return this.match.isRightTurnPhase(playerMove);
    }
}
