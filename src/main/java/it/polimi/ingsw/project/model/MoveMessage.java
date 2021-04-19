package it.polimi.ingsw.project.model;


public class MoveMessage {  //messaggio che rimando al player che viene poi visualizzato
    private final Player player;
    private final Match match;


    MoveMessage(Player player, Match match) {
        this.player = player;
        this.match = match;
    }

    public Match getMatch() {
        return this.match;
    }

    public Player getPlayer() {
        return player;
    }
}
