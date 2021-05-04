package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ActivateLeaderCardMoveTest {

    @Test
    // (1) use case where there aren't any resources in the Warehouse (not enough for activation)
    void isFeasibleMoveNotEnoughResources() {
        // creates the LeaderCard
        Player player = new Player("jimmy");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the LeaderCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("test", null, 1, resourcesRequired);
        // adds the LeaderCard to the Board
        player.getBoard().getLeaderCards().add(leaderCard);
        // creates the move
        Move activateLeaderCard = new ActivateLeaderCardMove("test");
        // tests the move
        assertFalse(activateLeaderCard.isFeasibleMove(match));
    }

    @Test
    // (2) use case where there are resources in the Warehouse (enough for activation)
    void isFeasibleMoveEnoughResources() {
        Player player = new Player("thanos");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the LeaderCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("test", null, 1, resourcesRequired);
        // adds the LeaderCard to the Board (same card as before)
        player.getBoard().getLeaderCards().add(leaderCard);
        // adds resources to the Warehouse
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        List<Resource> resourcesListSecondFloor = new ArrayList<>();
        List<Resource> resourcesListThirdFloor = new ArrayList<>();
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
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
        // creates the move
        Move activateLeaderCard = new ActivateLeaderCardMove("test");
        // tests the move
        assertTrue(activateLeaderCard.isFeasibleMove(match));
    }

    @Test
    void performMove() {

    }
}