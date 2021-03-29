public class VictoryPointsTile implements ActivableTile{
    private int victorypoints;

    public VictoryPointsTile(int victorypoints) {
        this.victorypoints = victorypoints;
    }

    @Override
    public void activate(Player player) {
    player.addVictoryPoints(victorypoints);
    }
    //è necessario avere un riferimento al player perché altrimenti non posso aggiungergli i punti
}
