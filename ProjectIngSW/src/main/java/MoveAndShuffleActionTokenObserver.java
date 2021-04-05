public class MoveAndShuffleActionTokenObserver implements Observer{
    private FaithMap faithMap;
    private ActionTokenContainer actionTokenContainer;
    @Override
    public void update() {
        faithMap.moveForwardBlack();
        actionTokenContainer.shuffle();
    }
}
