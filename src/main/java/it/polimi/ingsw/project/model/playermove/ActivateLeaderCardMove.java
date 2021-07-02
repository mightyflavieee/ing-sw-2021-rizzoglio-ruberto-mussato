package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;

public class ActivateLeaderCardMove extends Move{
    private final String leaderCardID;

    /**
     * it construct the move that is sent to the server
     * @param leaderCardID id of the leaderCard we want to activate
     */
    public ActivateLeaderCardMove(String leaderCardID) {
        this.leaderCardID = leaderCardID;
    }

    /**
     * checks if the move is actually feasible
     * @param match it is passed by the controller to check the correctness of the move
     * @return true if all checks are passed, false if not
     */
    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleActivateLeaderCardMove(this.leaderCardID);
    }

    /**
     * it performs the ActivateLeaderCard move on the match passed by the controller
     * @param match it is passed by the controller to perform the move
     */
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
