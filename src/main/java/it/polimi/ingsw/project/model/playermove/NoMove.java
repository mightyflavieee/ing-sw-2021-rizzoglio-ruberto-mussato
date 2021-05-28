package it.polimi.ingsw.project.model.playermove;


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
