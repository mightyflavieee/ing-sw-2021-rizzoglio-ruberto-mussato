package it.polimi.ingsw.project.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    void moveForward() {
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