import java.util.List;

public class MoveAndShuffleActionToken implements ActionToken{
    private List<Observer> observers;
    @Override
    public void Action() {
        observers.forEach(Observer::update);
    }
    public void attach(Observer observer){
        this.observers.add(observer);
    }
    public void detach(Observer observer){
        this.observers.remove(observer);
    }
}
