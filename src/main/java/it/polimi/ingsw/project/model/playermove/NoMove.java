package it.polimi.ingsw.project.model.playermove;


/**
 * it is the move sent it by the player to skip the leaderCard phase (Initial Phase, and End Phase)
 */
public class NoMove extends Move{


    @Override
    public String toString() {
        return "No Move";
    }

    @Override
    public boolean isMainMove() {
        return false;
    }
}
