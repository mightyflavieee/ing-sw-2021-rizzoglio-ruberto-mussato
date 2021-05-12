package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Board board = new Board();
        board.createFaithMap(new Match(playerList));
        board.moveForward();
        assertEquals(1, board.getFaithMap().getMarkerPosition());
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
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Board board = new Board();
        board.createFaithMap(new Match(playerList));
        LeaderCard leaderCard = new LeaderCard("prova", null, 1, null, null, null);
        board.getLeaderCards().add(leaderCard);
        assertEquals(1,board.getLeaderCards().size());
        board.performDiscardLeaderCardMove("prova");
        assertEquals(0,board.getLeaderCards().size());
        assertEquals(1, board.getFaithMap().getMarkerPosition());
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