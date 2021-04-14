package it.polimi.ingsw.project.model;

import java.util.ArrayList;
import java.util.List;

public class VictoryPointsTile implements ActivableTile{
    private List<VictoryPointsObserver> observers;
    private int victoryPoints;

    public VictoryPointsTile(int victoryPoints) {
        this.observers = new ArrayList<>();
        this.victoryPoints = victoryPoints;
    }

    public int getVictorypoints() {
        return victoryPoints;
    }

    @Override
    public void activate() {
        observers.forEach(VictoryPointsObserver::update);
    }
    
    public void attach(VictoryPointsObserver observer){
        this.observers.add(observer);
        observer.setVictoryPoints(this.victoryPoints);
    }
    
    public void detach(VictoryPointsObserver observer){
        this.observers.remove(observer);
    }
}
