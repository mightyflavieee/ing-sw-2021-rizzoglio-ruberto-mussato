package it.polimi.ingsw.project.model;


public class MoveMessage {  //messaggio che rimando al player che viene poi visualizzato
    private final Match match;


    MoveMessage(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return this.match;
    }
}
