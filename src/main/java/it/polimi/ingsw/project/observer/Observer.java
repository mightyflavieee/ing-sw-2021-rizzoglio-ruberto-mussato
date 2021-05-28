package it.polimi.ingsw.project.observer;

public interface Observer<T> {
    void update(T message);
}