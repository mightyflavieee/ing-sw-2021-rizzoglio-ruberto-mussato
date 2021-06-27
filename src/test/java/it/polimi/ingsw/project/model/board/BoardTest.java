package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.resource.Resource;
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
    void moveForward() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Board board = new Board();
        board.createFaithMapAndWarehouse(new Match(playerList), null);
        board.moveForward();
        assertEquals(1, board.getFaithMap().getMarkerPosition());
    }

    @Test
    void performDiscardLeaderCardMove() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Board board = new Board();
        board.createFaithMapAndWarehouse(new Match(playerList), null);
        LeaderCard leaderCard = new LeaderCard("prova", null, 1, null, null, null);
        board.getLeaderCards().add(leaderCard);
        assertEquals(1, board.getLeaderCards().size());
        board.performDiscardLeaderCardMove("prova");
        assertEquals(0, board.getLeaderCards().size());
        assertEquals(1, board.getFaithMap().getMarkerPosition());
    }

    @Test
    void getLeaderCardsToString() {
        Board board = new Board();
        assertFalse(board.getLeaderCardsToString().isEmpty());
    }

    @Test
    void getTrasmutation() {
        Board board = new Board();
        assertEquals(new ArrayList<ResourceType>(), board.getTransmutation());
    }

    @Test
    void getCurrentProductionCard() {
        Board board = new Board();
        assertEquals(3, board.getCurrentProductionCards().size());
    }

    @Test
    void calculateResourceVictoryPoints() {
        // creates the Player and the Match
        Player player = new Player("darkangelcraft_XDXD_6969");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        new Match(playerList);
        // adds resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        List<Resource> resourcesListSecondFloor = new ArrayList<>();
        List<Resource> resourcesListThirdFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        Resource resource1 = new Resource(ResourceType.Stone);
        Resource resource2 = new Resource(ResourceType.Stone);
        Resource resource3 = new Resource(ResourceType.Servant);
        Resource resource4 = new Resource(ResourceType.Servant);
        Resource resource5 = new Resource(ResourceType.Servant);
        resourcesListFirstFloor.add(resource0);
        resourcesListSecondFloor.add(resource1);
        resourcesListSecondFloor.add(resource2);
        resourcesListThirdFloor.add(resource3);
        resourcesListThirdFloor.add(resource4);
        resourcesListThirdFloor.add(resource5);
        shelves.put(ShelfFloor.First, resourcesListFirstFloor);
        shelves.put(ShelfFloor.Second, resourcesListSecondFloor);
        shelves.put(ShelfFloor.Third, resourcesListThirdFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 5);
        // tests method
        assertEquals(2, player.getBoard().calculateResourceVictoryPoints());
    }
}