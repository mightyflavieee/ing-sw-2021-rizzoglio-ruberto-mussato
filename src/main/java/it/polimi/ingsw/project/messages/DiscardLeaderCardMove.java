package it.polimi.ingsw.project.messages;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.playermove.Move;


public class DiscardLeaderCardMove extends Move {
    private final String leaderCardID;

    /**
     * it construct the DiscardLeaderCardMove
     * @param leaderCardID it is the id of the selected leaderCard
     */
    public DiscardLeaderCardMove (String leaderCardID) {
        this.leaderCardID = leaderCardID;
    }

    /**
     * it checks if the discardAction is feasible
     * @param match passed to check on the actual match if the action is feasible
     * @return true if the id is present, false if not
     */
    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleDiscardLeaderCardMove(this.leaderCardID);
    }

    /**
     * it removes the LeaderCard with that id
     * @param match passed to perform the move on the actual match
     */
    @Override
    public void performMove(Match match){
        match.performDiscardLeaderCardMove(this.leaderCardID);
    }
    @Override
    public String toString(){
        return "Discard Leader Card Move, Leader CardID: " + this.leaderCardID;
    }


    public boolean isMainMove(){
        return false;
    }
}

