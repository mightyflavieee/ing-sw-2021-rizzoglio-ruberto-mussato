package it.polimi.ingsw.project.observer;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.project.model.playermove.GameRequestMove;
import it.polimi.ingsw.project.model.playermove.Move;

public class ObservableSocket<T> extends Observable<T> {
    private final List<Observer<GameRequestMove>> socketObservers = new ArrayList<>();

    public void addCreateJoinGameObserver(Observer<GameRequestMove> observer) {
        synchronized (socketObservers) {
            socketObservers.add(observer);
        }
    }

    public void removeCreateJoinGameObserver(Observer<GameRequestMove> observer) {
        synchronized (socketObservers) {
            socketObservers.remove(observer);
        }
    }

    public void notifyAll(Object message) {
        if (message instanceof Move) {
            synchronized (super.observers) {
                for (Observer<T> observer : super.observers) {
                    T move = (T) message;
                    observer.update(move);
                }
            }
        } else if (message instanceof GameRequestMove) {
            synchronized (socketObservers) {
                for (Observer<GameRequestMove> observer : socketObservers) {
                    GameRequestMove gameMessage = (GameRequestMove) message;
                    observer.update(gameMessage);
                }
            }
        }
    }

}
