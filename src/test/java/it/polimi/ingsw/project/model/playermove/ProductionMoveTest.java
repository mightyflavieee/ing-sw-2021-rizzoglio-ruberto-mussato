package it.polimi.ingsw.project.model.playermove;

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

class ProductionMoveTest {

    @Test
    // Board only production with enough resources
    void enoughResourcesBoardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListFirstFloor.add(resource0);
        shelves.put(ShelfFloor.First, resourcesListFirstFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        List<ResourceType> boardManufacturedResource = new ArrayList<>();
        boardManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 1);
        ProductionMove productionMove = new ProductionMove(null, null, resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.Board, boardManufacturedResource);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
    }

    @Test
    // Board only production with not enough resources to complete the move
    void notEnoughResourcesBoardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListFirstFloor.add(resource0);
        shelves.put(ShelfFloor.First, resourcesListFirstFloor);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        List<ResourceType> boardManufacturedResource = new ArrayList<>();
        boardManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 1);
        ProductionMove productionMove = new ProductionMove(null, null, resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.Board, boardManufacturedResource);
        // tests the move
        assertFalse(productionMove.isFeasibleMove(match));
    }

    @Test
    // DevelopmentCard production with enough resources
    void enoughResourcesDevCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        Production production = new Production(resourcesRequired, resourcesRequired);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "test", 1, resourcesRequired);
        // adds the card to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListFirstFloor.add(resource0);
        shelves.put(ShelfFloor.First, resourcesListFirstFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        ProductionMove productionMove = new ProductionMove("test", null, resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.DevCard, null);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
    }

    @Test
    // DevelopmentCard production with not enough resources to complete the move
    void notEnoughResourcesDevCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        Production production = new Production(resourcesRequired, resourcesRequired);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "test", 1, resourcesRequired);
        // adds the card to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListFirstFloor.add(resource0);
        shelves.put(ShelfFloor.First, resourcesListFirstFloor);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        ProductionMove productionMove = new ProductionMove("test", null, resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.DevCard, null);
        // tests the move
        assertFalse(productionMove.isFeasibleMove(match));
    }

    @Test
    void performMove() {

    }
}