package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;

public class ActivateLeaderCardMove extends Move{
    private final String leaderCardID;

    public ActivateLeaderCardMove(String leaderCardID) {
        this.leaderCardID = leaderCardID;
    }

    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleActivateLeaderCardMove(this.leaderCardID);
    }

    @Override
    public void performMove(Match match){
        // performs move
        match.performActivateLeaderCardMove(this.leaderCardID);
        // adds LeaderCard victory points to player
        int victoryPointsToAdd = match.getCurrentPlayer().getBoard().fetchLeaderCardById(this.leaderCardID).getPoints();
        match.getCurrentPlayer().addVictoryPoints(victoryPointsToAdd);
    }

    @Override
    public String toString(){
        return "Activate Leader Card Move, Leader Card ID: " + this.leaderCardID;
    }

    public boolean isMainMove(){
        return false;
    }
}
