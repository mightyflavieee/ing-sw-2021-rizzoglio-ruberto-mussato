package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.actionTokens.ActionToken;
import it.polimi.ingsw.project.view.View;

public class ExtractActionTokenMove extends PlayerMove{
    private ActionToken actionToken;
    public ExtractActionTokenMove(Player player, View view, ActionToken actionToken) {
        super(player, view);
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
}
