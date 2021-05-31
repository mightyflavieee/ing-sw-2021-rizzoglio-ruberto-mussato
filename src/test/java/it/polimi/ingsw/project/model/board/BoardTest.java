package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    void moveForward() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Board board = new Board();
        board.createFaithMapAndWarehouse(new Match(playerList),null);
        board.moveForward();
        assertEquals(1, board.getFaithMap().getMarkerPosition());
    }

    @Test
    void performDiscardLeaderCardMove() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Board board = new Board();
        board.createFaithMapAndWarehouse(new Match(playerList),null);
        LeaderCard leaderCard = new LeaderCard("prova", null, 1, null, null, null);
        board.getLeaderCards().add(leaderCard);
        assertEquals(1,board.getLeaderCards().size());
        board.performDiscardLeaderCardMove("prova");
        assertEquals(0,board.getLeaderCards().size());
        assertEquals(1, board.getFaithMap().getMarkerPosition());
    }
    @Test
    void getLeaderCardsToString() {
        Board board = new Board();
        assertFalse(board.getLeaderCardsToString().isEmpty());
    }
    @Test
    void getTrasmutation(){
        Board board = new Board();
        assertNull(board.getTransmutation());
    }
    @Test
    void getCurrentProductionCard(){
        Board board = new Board();
        assertEquals(3, board.getCurrentProductionCards().size());

    }

}