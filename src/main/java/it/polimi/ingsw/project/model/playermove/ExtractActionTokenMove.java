package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.actionTokens.ActionToken;
import it.polimi.ingsw.project.view.View;

public class ExtractActionTokenMove extends Move{
    private ActionToken actionToken;
    public ExtractActionTokenMove( ActionToken actionToken) {
        this.actionToken = actionToken;
    }
    @Override
    public boolean isFeasibleMove(Match match){
        //TODO
        return false;
    }
    @Override
    public void performMove(Match match){
        //TODO
    }
    @Override
    public String toString(){
        //TODO
        return new String("Generic Move");
    }
}
