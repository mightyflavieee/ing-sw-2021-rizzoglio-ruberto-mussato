public class PapalCouncilObserver implements Observer{
    private Match match;
    private int numTile;
    @Override
    public void update() {
        match.notifyFaithMapsForCouncil(numTile);
    }

    public void setNumTile(int numTile) {
        this.numTile = numTile;
    }
}
