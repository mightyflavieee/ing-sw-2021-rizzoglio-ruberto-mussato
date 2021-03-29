import java.util.ArrayList;
import java.util.List;

public class PapalCouncilTile implements ActivableTile{
    private List<Observer> observers;
    private int numTile; // mi dice quale Council Tile sono

    public PapalCouncilTile(int numTile) {
        this.observers = new ArrayList<>();
        this.numTile = numTile;
    }

    public int getNumTile() {
        return numTile;
    }

    @Override
    public void activate() {
        observers.forEach(Observer::update);
    }
    public void attach(PapalCouncilObserver observer){
        this.observers.add(observer);
        observer.setMyPapalCouncilTile(this);
    }
    //overload nel caso in cui abbia bisogno di attaccare un altro tipo di observer
    public void attach(Observer observer){
        this.observers.add(observer);
    }
    public void detach(Observer observer){
        this.observers.remove(observer);
    }
}
