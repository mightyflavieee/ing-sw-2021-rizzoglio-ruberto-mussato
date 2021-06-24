package it.polimi.ingsw.project.model.board.faithMap;

import java.io.Serializable;

public class PapalFavourSlot implements Serializable {
    private static final long serialVersionUID = 3840280111115092704L;
    private final int victoryPoints;
    private PapalSlotStatus status;

    public PapalFavourSlot(int victoryPoints) {
        this.victoryPoints = victoryPoints;
        this.status = PapalSlotStatus.Available;
    }

    public int getVictoryPoints(){
        return  victoryPoints;
    }
    public PapalSlotStatus getStatus() {
        return status;
    }
    public void updateStatus(PapalSlotStatus status){
        this.status = status;
    }
}
