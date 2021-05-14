package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void isPlayerTurn() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList);
        assertTrue(model.isPlayerTurn(player));
    }

    @Test
    void isFeasibleMove() {
    }

    @Test
    void performMove() {
    }

    @Test
    void updateTurn() {
    }

    @Test
    void notifyPartialMove() {
    }

    @Test
    void getMatchCopy() {
    }
    @Test
    void isFeasibleLorenzoMove(){
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList);
        while(!(model.getMatch().getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)){
            model.getMatch().getActionTokenContainer().shuffle();
        }
    }
    @Test
    void performLorenzoMove(){
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList);
        while(!(model.getMatch().getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)){
            model.getMatch().getActionTokenContainer().shuffle();
        }
        model.getMatch().performExtractActionTokenMove();
        assertTrue(2==player.getBoard().getFaithMap().getBlackMarkerPosition());
    }
}