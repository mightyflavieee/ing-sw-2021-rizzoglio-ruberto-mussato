import java.util.List;

public class MoveAndShuffleActionToken implements ActionToken{
    private List<MoveAndShuffleActionTokenObserver> observers;
    @Override
    public void Action() {
        observers.forEach(MoveAndShuffleActionTokenObserver::update);
    }
    public void attach(MoveAndShuffleActionTokenObserver observer){
        this.observers.add(observer);
    }
    public void detach(MoveAndShuffleActionTokenObserver observer){
        this.observers.remove(observer);
    }
}
