package it.polimi.ingsw.project.observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Observable<T> implements Serializable{
    private static final long serialVersionUID = 3840280592475043704L;
    private final List<Observer<T>> observers = new ArrayList<>();
    private String type = "Generic Type";

    public void addObserver(Observer<T> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer<T> observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notify(T message) {
        synchronized (observers) {
            for (Observer<T> observer : observers) {
                observer.update(message);
            }
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
