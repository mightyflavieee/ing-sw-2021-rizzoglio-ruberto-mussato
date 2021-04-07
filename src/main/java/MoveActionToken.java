import java.util.List;

public class MoveActionToken implements ActionToken{
    private List<MoveActionTokenObserver> observers;
    @Override
    public void Action() {
        observers.forEach(MoveActionTokenObserver::update);
    }
    public void attach(MoveActionTokenObserver observer){
        this.observers.add(observer);
    }
    public void detach(MoveActionTokenObserver observer){
        this.observers.remove(observer);
    }
}
