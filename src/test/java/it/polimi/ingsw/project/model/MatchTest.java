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

class MatchTest {

    @Test
    void getPlayerList() {
    }

    @Test
    void getMarket() {
    }

    @Test
    void getCardContainer() {
    }

    @Test
    void getActionTokenContainer() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void getIsLastTurn() {
    }

    @Test
    void notifyFaithMapsForCouncil() {
    }

    @Test
    void notifyFaithMapsForDiscard() {
    }

    @Test
    void addVictoryPoints() {
    }

    @Test
    void discardForActionToken() {
    }

    @Test
    void end() {
    }

    @Test
    void updatePlayer() {
    }

    @Test
    void testClone() {
    }

    @Test
    void isFeasibleDiscardLeaderCardMove() {
    }

    @Test
    void performDiscardLeaderCardMove() {
    }

    @Test
    void isFeasibleChangeShelvesMove() {
    }

    @Test
    void performChangeShelvesMove() {
    }

    @Test
    void isFeasibleBuyDevCardMove() {
    }

    @Test
    void performBuyDevCardMove() {
    }

    @Test
    void isFeasibleDevCardProductionMove() {
    }

    @Test
    void performDevCardProductionMove() {
    }

    @Test
    void isFeasibleExtractActionTokenMove() {
    }

    @Test
    void performExtractActionTokenMove() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        while(!(match.getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)){
            match.getActionTokenContainer().shuffle();
        }
        match.performExtractActionTokenMove();
        assertEquals(2, player.getBoard().getFaithMap().getBlackMarkerPosition());
    }

    @Test
    void isFeasibleActivateLeaderCardMove() {
    }

    @Test
    void performActivateLeaderCardMove() {
    }

    @Test
    void moveForwardBlack() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        match.moveForwardBlack();
        assertEquals(1, player.getBoard().getFaithMap().getBlackMarkerPosition());
    }
}