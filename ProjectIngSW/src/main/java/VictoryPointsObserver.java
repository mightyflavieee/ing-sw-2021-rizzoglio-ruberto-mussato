public class VictoryPointsObserver implements Observer{
    private Match match;
    private VictoryPointsTile myVictoryPointsTile;
    @Override
    public void update() {
        match.addVictoryPoints(myVictoryPointsTile.getVictorypoints());
    }

    public void setMyVictoryPointsTile(VictoryPointsTile myVictoryPointsTile) {
        this.myVictoryPointsTile = myVictoryPointsTile;
    }
}
