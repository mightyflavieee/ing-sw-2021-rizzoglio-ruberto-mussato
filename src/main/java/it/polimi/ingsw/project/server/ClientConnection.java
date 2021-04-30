package it.polimi.ingsw.project.server;

import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.MoveList;
import it.polimi.ingsw.project.observer.Observer;

public interface ClientConnection{

    void closeConnection();

    void addObserver(Observer<MoveList> observer);

    void asyncSend(Object message);
}