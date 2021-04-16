package it.polimi.ingsw.project.model.board.faithMap;

public class PapalFavourSlot {
    private int victoryPoints;
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
