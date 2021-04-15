package it.polimi.ingsw.project.observer;

public interface Observer<T> {
    public void update(T message);

    public void update();
}