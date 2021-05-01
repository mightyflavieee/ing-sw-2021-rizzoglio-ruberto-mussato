package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.playermove.DiscardLeaderCardMove;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testClone() {
    }

    @Test
    void getBoard() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void getNickname() {
    }

    @Test
    void getVictoryPoints() {
    }

    @Test
    void addVictoryPoints() {
    }

    @Test
    void moveForward() {Player player = new Player("pinco pallino");
        player.moveForward();
        assertEquals(1, player.getBoard().getFaithMap().getMarkerPosition());
    }

    @Test
    void moveForwardBlack() {
        Player player = new Player("pinco pallino");
        player.moveForwardBlack();
        assertEquals(1, player.getBoard().getFaithMap().getBlackMarkerPosition());
    }

    @Test
    void papalCouncil() {
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
    void isFeasibleActivateLeaderCardMove() {
    }

    @Test
    void performActivateLeaderCardMove() {
    }
}