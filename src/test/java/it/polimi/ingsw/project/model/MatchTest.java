package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
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
    void moveForwardBlack() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        match.moveForwardBlack();
        assertEquals(1, player.getBoard().getFaithMap().getBlackMarkerPosition());
    }
    @Test
    void endGameforBlack(){
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        for(int i = 1; i < 13 ; i++) {
            while (!(match.getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)) {
                match.getActionTokenContainer().shuffle();
            }
            match.performExtractActionTokenMove();
            assertEquals(2*i, player.getBoard().getFaithMap().getBlackMarkerPosition());
        }
        assertEquals(24, player.getBoard().getFaithMap().getBlackMarkerPosition());
        assertTrue(match.getisOver());

    }

    @Test
    void isFeasibleActivateLeaderCardMove() {
    }

    @Test
    void performActivateLeaderCardMove() {
    }


}