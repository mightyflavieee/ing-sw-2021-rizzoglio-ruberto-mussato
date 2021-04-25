package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;

public class ExtractActionTokenMove extends Move{
    @Override
    public boolean isFeasibleMove(Match match){
       return match.isFeasibleExtractActionTokenMove();
    }
    @Override
    public void performMove(Match match){
        match.performExtractActionTokenMove();
    }
    @Override
    public String toString(){
        return new String("ExtractActionToken Move");
    }
}
