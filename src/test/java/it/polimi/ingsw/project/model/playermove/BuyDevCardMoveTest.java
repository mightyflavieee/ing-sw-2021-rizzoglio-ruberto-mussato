package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.CardContainer;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.developmentCard.Production;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BuyDevCardMoveTest {

    @Test
    void notEnoughResourceForBuy() {
        // creates the Player and the Match
        Player player = new Player("peppe");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        Production production = new Production(resourcesRequired, resourcesRequired);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "test", 1, resourcesRequired);
        // creates the container and inserts the card into it
        CardContainer cardContainer = new CardContainer();
        cardContainer.addCardToContainer(devCard);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        Move buyDevCardMove = new BuyDevCardMove("test", DevCardPosition.Left, resourcesToEliminateWarehouse, resourcesToEliminateChest);
        // tests the move
        assertFalse(buyDevCardMove.isFeasibleMove(match));
    }

    @Test
    void enoughResourceForBuy() {
        // creates the Player and the Match
        Player player = new Player("darkangelcraft_XDXD_6969");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        Production production = new Production(resourcesRequired, resourcesRequired);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "test", 1, resourcesRequired);
        // Inserts the card into the CardContainer
        match.getCardContainer().addCardToContainer(devCard);
        // adds resources to the Warehouse and chest
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
        player.getBoard().getChest().put(ResourceType.Stone, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        Move buyDevCardMove = new BuyDevCardMove("test", DevCardPosition.Left, resourcesToEliminateWarehouse, resourcesToEliminateChest);
        // tests the move
        assertTrue(buyDevCardMove.isFeasibleMove(match));
    }

    @Test
    void performMove() {
        // creates the Player and the Match
        Player player = new Player("darkangelcraft_XDXD_6969");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        Production production = new Production(resourcesRequired, resourcesRequired);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "test", 1, resourcesRequired);
        // Inserts the card into the CardContainer
        match.getCardContainer().addCardToContainer(devCard);
        // adds resources to the Warehouse and chest
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
        player.getBoard().getChest().put(ResourceType.Stone, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        Move buyDevCardMove = new BuyDevCardMove("test", DevCardPosition.Left, resourcesToEliminateWarehouse, resourcesToEliminateChest);
        // tests the move
        buyDevCardMove.performMove(match);
        assertEquals(devCard, player.getBoard().getMapTray().get(DevCardPosition.Left).get(0));
    }
}