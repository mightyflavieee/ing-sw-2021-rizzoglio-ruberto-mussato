package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;

import java.io.Serializable;

//messaggio ricevuto dal server
public class Move implements Serializable {
    public boolean isFeasibleMove(Match match){
        //TODO
        return false;
    }
    public void performMove(Match match){
        //TODO
    }
    public String toString(){
        return "Generic Move";
    }

    public boolean isMainMove(){
        return true;
    }

}