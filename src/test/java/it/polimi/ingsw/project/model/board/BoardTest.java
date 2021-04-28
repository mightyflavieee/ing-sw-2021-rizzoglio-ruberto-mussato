package it.polimi.ingsw.project.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testClone() {
        Board board = new Board();
        Board clonedBoard;
        clonedBoard = board.clone();
        assertNotSame(board, clonedBoard);
        assertEquals(board.getChest(), clonedBoard.getChest());
    }

    @Test
    void getMapTray() {
    }

    @Test
    void getChest() {
    }

    @Test
    void getWarehouse() {
    }

    @Test
    void getActivePerks() {
    }

    @Test
    void getLeaderCards() {
    }

    @Test
    void mapAllResources() {
    }

    @Test
    void getCurrentProductionCards() {
    }

    @Test
    void moveForward() {
    }

    @Test
    void papalCouncil() {
    }

    @Test
    void moveForwardBlack() {
    }

    @Test
    void isFeasibleDiscardLeaderCardMove() {
    }

    @Test
    void isFeasibleChangeShelvesMove() {
    }

    @Test
    void performChangeShelvesMove() {
    }

    @Test
    void performDiscardLeaderCardMove() {
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
}