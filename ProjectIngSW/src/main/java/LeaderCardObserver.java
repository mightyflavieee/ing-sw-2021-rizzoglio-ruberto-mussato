public class LeaderCardObserver implements Observer {
    private Board board;

    public LeaderCardObserver(Board board) {
        this.board = board;
    }

    @Override
    public void update() {}

    // overloading to update activePerks list in Board
    public void update(Perk perk) {
        this.board.getActivePerks().add(perk);
    }
}
