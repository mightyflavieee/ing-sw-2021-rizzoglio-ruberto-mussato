package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.controller.Controller;
import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.playermove.ExtractActionTokenMove;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.MoveList;
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
        MoveList moveList = new MoveList();
        Move extractActionTokenMove = new ExtractActionTokenMove();
        moveList.add(extractActionTokenMove);
        PlayerMove playerMove = new PlayerMove(player,
                null,moveList);
        while(!(model.getMatch().getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)){
            model.getMatch().getActionTokenContainer().shuffle();
        }
        assertTrue(model.isFeasibleMove(playerMove,0));
    }
    @Test
    void performLorenzoMove(){
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList);
        MoveList moveList = new MoveList();
        Move extractActionTokenMove = new ExtractActionTokenMove();
        moveList.add(extractActionTokenMove);
        PlayerMove playerMove = new PlayerMove(player,
                null,moveList);
        while(!(model.getMatch().getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)){
            model.getMatch().getActionTokenContainer().shuffle();
        }
        model.performMove(playerMove,0);
        assertTrue(2==player.getBoard().getFaithMap().getBlackMarkerPosition());
    }
}