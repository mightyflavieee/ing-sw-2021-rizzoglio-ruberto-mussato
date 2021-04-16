package it.polimi.ingsw.project.model.actionTokens;

import java.util.List;

import it.polimi.ingsw.project.observer.custom.MoveActionTokenObserver;

public class MoveActionToken implements ActionToken {
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
