import java.util.ArrayList;
import java.util.List;

public class PapalCouncilTile implements ActivableTile{
    private List<PapalCouncilObserver> observers;
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
        observers.forEach(PapalCouncilObserver::update);
    }
    public void attach(PapalCouncilObserver observer){
        this.observers.add(observer);
        observer.setNumTile(this.numTile);
    }

    public void detach(PapalCouncilObserver observer){
        this.observers.remove(observer);
    }
}
