import java.util.ArrayList;
import java.util.List;

public class VictoryPointsTile implements ActivableTile{
    private List<Observer> observers;
    private int victorypoints;

    public VictoryPointsTile(int victorypoints) {
        this.observers = new ArrayList<>();
        this.victorypoints = victorypoints;
    }

    public int getVictorypoints() {
        return victorypoints;
    }

    @Override
    public void activate() {
        observers.forEach(Observer::update);
    }
    public void attach(VictoryPointsObserver observer){
        this.observers.add(observer);
        observer.setMyVictoryPointsTile(this);
    }
    //overload nel caso in cui abbia bisogno di attaccare un altro tipo di observer
    public void attach(Observer observer){
        this.observers.add(observer);
    }
    public void detach(Observer observer){
        this.observers.remove(observer);
    }
}
