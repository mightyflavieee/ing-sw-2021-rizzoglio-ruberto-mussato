public class PapalCouncilObserver implements Observer{
    private Match match;
    private PapalCouncilTile myPapalCouncilTile;
    @Override
    public void update() {
        match.notifyFaithMapsForCouncil(myPapalCouncilTile.getNumTile());

    }

    public void setMyPapalCouncilTile(PapalCouncilTile myPapalCouncilTile) {
        this.myPapalCouncilTile = myPapalCouncilTile;
    }
}
