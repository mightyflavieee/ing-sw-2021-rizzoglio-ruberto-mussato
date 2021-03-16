public class PapalFavourSlot {
    private int victoryPoints;
    private PapalSlotStatus status;
    public int getVictoryPoints(){
        return  victoryPoints;
    }

    public PapalSlotStatus getStatus() {
        return status;
    }
    public void UpdateStatus(PapalSlotStatus status){
        this.status = status;
    }
}
