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

public class Model extends Observable<MoveMessage> {
    private static final long serialVersionUID = 3843380592475092704L;
    private final Match match;
    private final String matchUID;

    public Model(List<Player> listOfPlayers, String matchUID) {
        this.match = new Match(listOfPlayers);
        this.matchUID = matchUID;
    }

    public Model(Match match, String matchUID) {
        this.match = match;
        this.matchUID = matchUID;
    }

    public void reAddObserversOnMatch(){
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

    public void updateTurn() {
        if (match.getPlayerList().size() == 1 && match.getCurrentPlayer().getTurnPhase() == TurnPhase.EndPhase) {
            match.performExtractActionTokenMove();
        }
        match.updatePlayer();
        notify(new MoveMessage(this.match.clone())); // è il messaggio che verrà inviato a l player
    }

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

    public void notifyPartialMove() {
        notify(new MoveMessage(this.match.clone())); // è il messaggio che verrà inviato a l player
    }

    public Match getMatchCopy() {
        return this.match.clone();
    }

    public boolean isRightTurnPhase(PlayerMove playerMove) {
        return this.match.isRightTurnPhase(playerMove);
    }

    public void saveModelOnServer() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Model.class, new AdapterModel())
                .create();
        String modelToJson = gson.toJson(this);
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
