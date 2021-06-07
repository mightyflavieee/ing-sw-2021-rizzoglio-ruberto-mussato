package it.polimi.ingsw.project.server;

import it.polimi.ingsw.project.model.playermove.interfaces.Controllable;
import it.polimi.ingsw.project.observer.Observer;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer<Controllable> observer);

    void asyncSend(Object message);
}