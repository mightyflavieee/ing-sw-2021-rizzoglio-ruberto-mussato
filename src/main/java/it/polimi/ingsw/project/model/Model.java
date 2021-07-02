package it.polimi.ingsw.project.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.project.messages.MoveMessage;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.utils.TypeAdapters.AdapterModel;

public class Model extends Observable<MoveMessage> implements Cloneable {
    private static final long serialVersionUID = 3843380592475092704L;
    private final Match match;
    private final String matchUID;

    /**
     * constructor for the normal start of a game
     * @param listOfPlayers list of players for the game
     * @param matchUID id of the game
     */
    public Model(List<Player> listOfPlayers, String matchUID) {
        this.match = new Match(listOfPlayers);
        this.matchUID = matchUID;
    }

    /**
     * constructor for the persistence start of a game after server crashed
     * @param match it is passed by the server after reading from json
     * @param matchUID id of the game
     */
    public Model(Match match, String matchUID) {
        this.match = match;
        this.matchUID = matchUID;
    }

    /**
     * function called for the persistence when a game is reloaded from the disk on the server
     */
    public void reAddObserversOnMatch() {
        this.match.readdObservers();
    }

    public int extractNumberOfPlayers() {
        return this.match.getPlayerList().size();
    }

    public Match getMatch() {
        return match;
    }

    public String getMatchUID() {
        return matchUID;
    }

    public boolean isPlayerTurn(Player player) {
        return player.getNickname().equals(match.getCurrentPlayer().getNickname());
    }

    public boolean isFeasibleMove(PlayerMove playerMove) {
        return playerMove.isFeasibleMove(this.match);
    }

    public void performMove(PlayerMove playerMove) {
        playerMove.performMove(this.match);
        this.match.end();
    }

    /**
     * it is called by the model after every move and it updates the turnPhase or updates also the player
     */
    public void updateTurn() {
        if (match.getPlayerList().size() == 1 && match.getCurrentPlayer().getTurnPhase() == TurnPhase.EndPhase) {
            match.performExtractActionTokenMove();
        }
        match.updatePlayer();
        notify(new MoveMessage(this.match.clone())); // è il messaggio che verrà inviato a l player
    }

    /**
     * called when a player disconnects and it skips his phase and revert it to waitPhase
     */
    public void playerSkipTurn(Player disconnectedPlayer) {
        this.setPlayerConnectionToFalse(disconnectedPlayer);
        if (disconnectedPlayer.getNickname().equals(this.match.getCurrentPlayer().getNickname())) {
            this.match.playerSkipTurn();
        }
        notify(new MoveMessage(this.match.clone()));
    }

    private void setPlayerConnectionToFalse(Player disconnectedPlayer) {
        match.setPlayerConnectionToFalse(disconnectedPlayer);
    }

    public void setPlayerConnectionToTrue(String disconnectedPlayerNickname) {
        match.setPlayerConnectionToTrue(disconnectedPlayerNickname);
    }

    /**
     * it returns to the players the match not updated when errors occurs
     */
    public void notifyPartialMove() {
        notify(new MoveMessage(this.match.clone())); // è il messaggio che verrà inviato a l player
    }

    /**
     * @return it returns a cloned copy of the match
     */
    public Match getMatchCopy() {
        return this.match.clone();
    }


    /**
     * it checks if the Phase is the right one for the move
     * @param playerMove move sent by the player
     * @return it returns true if the player that sent the move is in the right Phase for the move
     */
    public boolean isRightTurnPhase(PlayerMove playerMove) {
        return this.match.isRightTurnPhase(playerMove);
    }

    /**
     * it saves the json of the model on the server for the persistence setting all players to disconnected
     */
    public void saveModelOnServer() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Model.class, new AdapterModel()).create();
        Model toJsonModel = new Model(this.getMatchCopy(), matchUID);
        toJsonModel.match.setAllPlayersToDisconnected();
        String modelToJson = gson.toJson(toJsonModel);
        if (this.matchUID != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(this.matchUID + ".json"));
                writer.write(modelToJson);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
