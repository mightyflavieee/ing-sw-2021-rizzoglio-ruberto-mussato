public class VictoryPointsObserver implements Observer{
    private Match match;
    private int victoryPoints;
    @Override
    public void update() {
        match.addVictoryPoints(victoryPoints);
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
