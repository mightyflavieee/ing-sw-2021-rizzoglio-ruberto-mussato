import java.util.List;

public class DiscardActionToken implements  ActionToken{
    private CardColor cardColor;
    private List<Observer> observers;
    @Override
    public void Action() {
        observers.forEach(Observer::update);
    }
    public void attach(DiscardActionTokenObserver observer){
        this.observers.add(observer);
        observer.setCardColor(this.cardColor);
    }
    public void attach(Observer observer){
        this.observers.add(observer);
    }
    public void detach(Observer observer){
        this.observers.remove(observer);
    }
}
