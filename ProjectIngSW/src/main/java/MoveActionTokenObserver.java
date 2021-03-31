public class MoveActionTokenObserver implements Observer{
    private FaithMap faithMap;
    @Override
    public void update() {
        faithMap.moveForwardBlack();
        faithMap.moveForwardBlack();
    }
}
