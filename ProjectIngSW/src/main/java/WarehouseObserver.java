public class WarehouseObserver implements Observer{
    private Match match;

    @Override
    public void update() {

    }
    public void update(int numDiscardedResources){
        match.notifyFaithMapsForDiscard(numDiscardedResources);
    }
}
