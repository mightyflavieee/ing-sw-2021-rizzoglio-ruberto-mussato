package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.developmentCard.Production;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.PerkType;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductionMoveTest {

    @Test
    // Board production with enough resources
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
        ResourceType boardManufacturedResource = ResourceType.Shield;
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 1);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(null, null, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), resourcesToEliminateChest, ProductionType.Board,
                boardManufacturedResource, null);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
        productionMove.performMove(match);
        Map<ResourceType, Integer> newCurrentChestResources = new HashMap<>();
        newCurrentChestResources.put(ResourceType.Stone, 2);
        newCurrentChestResources.put(ResourceType.Shield, 1);
        for (ResourceType type : newCurrentChestResources.keySet()) {
            assertTrue(player.getBoard().getChest().containsKey(type));
            assertEquals(newCurrentChestResources.get(type), player.getBoard().getChest().get(type));
        }
        for(ResourceType resourceType : player.getBoard().getWarehouse().mapAllContainedResources().keySet()){
            assertEquals(0,player.getBoard().getWarehouse().mapAllContainedResources().get(resourceType));
        }
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
        ResourceType boardManufacturedResource = ResourceType.Shield;
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 1);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(null, null, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), resourcesToEliminateChest,
                ProductionType.Board, boardManufacturedResource, null);
        // tests the move
        assertFalse(productionMove.isFeasibleMove(match));
    }

    @Test
    // DevelopmentCard production with enough resources
    // DEVCARD: required = Coin: 1; Stone: 3
    //          manufactured = Shield: 1; Servant: 2
    void enoughResourcesDevCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 3);
        manufacturedResources.put(ResourceType.Shield, 1);
        manufacturedResources.put(ResourceType.Servant, 2);
        Production production = new Production(resourcesRequired, manufacturedResources);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "test", 1, resourcesRequired);
        List<String> devCardIDs = new ArrayList<>();
        devCardIDs.add(devCard.getId());
        // adds the card to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        // adds necessary resources to the Warehouse and chest and ExtraDeposit
        player.getBoard().getWarehouse().createExtraDeposit(new Resource(ResourceType.Stone));
        LinkedHashMap<ResourceType, Integer> extraDeposit = player.getBoard().getWarehouse().getExtraDeposit();
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListFirstFloor.add(resource0);
        shelves.put(ShelfFloor.First, resourcesListFirstFloor);
        extraDeposit.put(ResourceType.Stone, 1);
        player.getBoard().getChest().put(ResourceType.Stone, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateExtraDeposit = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        resourcesToEliminateExtraDeposit.put(ResourceType.Stone, 1);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        requiredResources = player.getBoard().sumResourcesMaps(requiredResources, resourcesToEliminateExtraDeposit);
        ProductionMove productionMove = new ProductionMove(devCardIDs, null, requiredResources,
                resourcesToEliminateWarehouse, resourcesToEliminateExtraDeposit, resourcesToEliminateChest,
                ProductionType.DevCard, null, null);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
        productionMove.performMove(match);
        Map<ResourceType, Integer> newCurrentChestResources = new HashMap<>();
        newCurrentChestResources.put(ResourceType.Stone, 1);
        newCurrentChestResources.put(ResourceType.Shield, 1);
        newCurrentChestResources.put(ResourceType.Servant, 2);
        for (ResourceType type : newCurrentChestResources.keySet()) {
            assertTrue(player.getBoard().getChest().containsKey(type));
            assertEquals(newCurrentChestResources.get(type), player.getBoard().getChest().get(type));
        }
        for(ResourceType resourceType : player.getBoard().getWarehouse().mapAllContainedResources().keySet()){
            assertEquals(0, player.getBoard().getWarehouse().mapAllContainedResources().get(resourceType));
        }
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
        List<String> devCardIDs = new ArrayList<>();
        devCardIDs.add(devCard.getId());
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
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(devCardIDs, null, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), resourcesToEliminateChest,
                ProductionType.DevCard, null, null);
        // tests the move
        assertFalse(productionMove.isFeasibleMove(match));
    }

    @Test
    // LeaderCard production with enough resources
    void enoughResourcesLeaderCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("test", perk, 1, resourcesRequiredForActivation, null, null);
        List<String> leaderCardIDs = new ArrayList<>();
        leaderCardIDs.add(leaderCard.getId());
        // adds the LeaderCard to the Board
        player.getBoard().getLeaderCards().add(leaderCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListFirstFloor.add(resource0);
        shelves.put(ShelfFloor.First, resourcesListFirstFloor);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        List<ResourceType> perkManufacturedResource = new ArrayList<>();
        perkManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        Map<ResourceType, Integer> requiredResources = new HashMap<>(resourcesToEliminateWarehouse);
        ProductionMove productionMove = new ProductionMove(null, leaderCardIDs, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), new HashMap<>(),
                ProductionType.LeaderCard, null, perkManufacturedResource);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
        productionMove.performMove(match);
        Map<ResourceType, Integer> newCurrentChestResources = new HashMap<>();
        newCurrentChestResources.put(ResourceType.Shield, 1);
        assertTrue(player.getBoard().getChest().containsKey(ResourceType.Shield));
        assertEquals(newCurrentChestResources.get(ResourceType.Shield), player.getBoard().getChest().get(ResourceType.Shield));
        for(ResourceType resourceType : player.getBoard().getWarehouse().mapAllContainedResources().keySet()){
            assertEquals(0,player.getBoard().getWarehouse().mapAllContainedResources().get(resourceType));
        }
    }

    @Test
    // LeaderCard production with not enough resources to complete the move
    void notEnoughResourcesLeaderCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("test", perk, 1, resourcesRequiredForActivation, null, null);
        List<String> leaderCardIDs = new ArrayList<>();
        leaderCardIDs.add(leaderCard.getId());
        // adds the LeaderCard to the Board
        player.getBoard().getLeaderCards().add(leaderCard);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        List<ResourceType> perkManufacturedResource = new ArrayList<>();
        perkManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        Map<ResourceType, Integer> requiredResources = new HashMap<>(resourcesToEliminateWarehouse);
        ProductionMove productionMove = new ProductionMove(null, leaderCardIDs, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), new HashMap<>(),
                ProductionType.LeaderCard, null, perkManufacturedResource);
        // tests the move
        assertFalse(productionMove.isFeasibleMove(match));
    }

    @Test
    // Board and DevelopmentCard production with enough resources
    void enoughResourcesBoardAndDevCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
        manufacturedResources.put(ResourceType.Shield, 1);
        manufacturedResources.put(ResourceType.Servant, 2);
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        Production production = new Production(resourcesRequired, manufacturedResources);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "test", 1, resourcesRequired);
        List<String> devCardIDs = new ArrayList<>();
        devCardIDs.add(devCard.getId());
        // adds the card to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListSecondFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListSecondFloor.add(resource0);
        resourcesListSecondFloor.add(resource0);
        shelves.put(ShelfFloor.Second, resourcesListSecondFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        ResourceType boardManufacturedResource = ResourceType.Shield;
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 3);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(devCardIDs, null, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), resourcesToEliminateChest,
                ProductionType.BoardAndDevCard, boardManufacturedResource, null);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
        productionMove.performMove(match);
        Map<ResourceType, Integer> newCurrentChestResources = new HashMap<>();
        newCurrentChestResources.put(ResourceType.Shield, 2);
        newCurrentChestResources.put(ResourceType.Servant, 2);
        for (ResourceType type : newCurrentChestResources.keySet()) {
            assertTrue(player.getBoard().getChest().containsKey(type));
            assertEquals(newCurrentChestResources.get(type), player.getBoard().getChest().get(type));
        }
        for(ResourceType resourceType : player.getBoard().getWarehouse().mapAllContainedResources().keySet()){
            assertEquals(0,player.getBoard().getWarehouse().mapAllContainedResources().get(resourceType));
        }
    }

    @Test
    // Board and DevelopmentCard production with not enough resources to complete the move
    void notEnoughResourcesBoardAndDevCardProduction() {
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
        List<String> devCardIDs = new ArrayList<>();
        devCardIDs.add(devCard.getId());
        // adds the card to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        ResourceType boardManufacturedResource = ResourceType.Shield;
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 3);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(devCardIDs, null, requiredResources, resourcesToEliminateWarehouse,
                new HashMap<>(), resourcesToEliminateChest, ProductionType.BoardAndDevCard, boardManufacturedResource, null);
        // tests the move
        assertFalse(productionMove.isFeasibleMove(match));
    }

    @Test
    // Board and LeaderCard production with enough resources
    void enoughResourcesBoardAndLeaderCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("test", perk, 1, resourcesRequiredForActivation, null, null);
        List<String> leaderCardIDs = new ArrayList<>();
        leaderCardIDs.add(leaderCard.getId());
        // adds the LeaderCard to the Board
        player.getBoard().getLeaderCards().add(leaderCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListFirstFloor.add(resource0);
        resourcesListFirstFloor.add(resource0);
        shelves.put(ShelfFloor.First, resourcesListFirstFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        ResourceType boardManufacturedResource = ResourceType.Shield;
        List<ResourceType> perkManufacturedResource = new ArrayList<>();
        perkManufacturedResource.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 1);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(null, leaderCardIDs, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), resourcesToEliminateChest,
                ProductionType.BoardAndLeaderCard, boardManufacturedResource, perkManufacturedResource);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
        productionMove.performMove(match);
        Map<ResourceType, Integer> newCurrentChestResources = new HashMap<>();
        newCurrentChestResources.put(ResourceType.Shield, 1);
        newCurrentChestResources.put(ResourceType.Servant, 1);
        newCurrentChestResources.put(ResourceType.Stone, 2);
        for (ResourceType type : newCurrentChestResources.keySet()) {
            assertTrue(player.getBoard().getChest().containsKey(type));
            assertEquals(newCurrentChestResources.get(type), player.getBoard().getChest().get(type));
        }
        for(ResourceType resourceType : player.getBoard().getWarehouse().mapAllContainedResources().keySet()){
            assertEquals(0,player.getBoard().getWarehouse().mapAllContainedResources().get(resourceType));
        }
    }

    @Test
    // Board and LeaderCard production with not enough resources to complete the move
    void notEnoughResourcesBoardAndLeaderCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("test", perk, 1, resourcesRequiredForActivation, null, null);
        List<String> leaderCardIDs = new ArrayList<>();
        leaderCardIDs.add(leaderCard.getId());
        // adds the LeaderCard to the Board
        player.getBoard().getLeaderCards().add(leaderCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListSecondFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListSecondFloor.add(resource0);
        resourcesListSecondFloor.add(resource0);
        shelves.put(ShelfFloor.Second, resourcesListSecondFloor);
        player.getBoard().getChest().put(ResourceType.Shield, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        ResourceType boardManufacturedResource = ResourceType.Shield;
        List<ResourceType> perkManufacturedResource = new ArrayList<>();
        perkManufacturedResource.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 1);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(null, leaderCardIDs, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), resourcesToEliminateChest,
                ProductionType.BoardAndLeaderCard, boardManufacturedResource, perkManufacturedResource);
        // tests the move
        assertFalse(productionMove.isFeasibleMove(match));
    }

    @Test
    // Board, DevelopmentCard and LeaderCard production with enough resources
    // BOARD: required = Coin: 1; Stone: 1
    //        manufactured = Shield: 1
    // DEVCARD: required = Coin: 1; Stone: 2
    //          manufactured = Shield: 1; Servant: 2
    // LEADERCARD: required = Coin: 1
    //             manufactured = Servant: 1
    void enoughResourcesBoardAndDevCardAndLeaderCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        manufacturedResources.put(ResourceType.Shield, 1);
        manufacturedResources.put(ResourceType.Servant, 2);
        Production production = new Production(resourcesRequired, manufacturedResources);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "DEV_id", 1, resourcesRequired);
        List<String> devCardIDs = new ArrayList<>();
        devCardIDs.add(devCard.getId());
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("LEADER_id", perk, 1, resourcesRequiredForActivation, null, null);
        List<String> leaderCardIDs = new ArrayList<>();
        leaderCardIDs.add(leaderCard.getId());
        // adds the DevelopmentCard and the LeaderCard to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        player.getBoard().getLeaderCards().add(leaderCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListThirdFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListThirdFloor.add(resource0);
        resourcesListThirdFloor.add(resource0);
        resourcesListThirdFloor.add(resource0);
        shelves.put(ShelfFloor.Third, resourcesListThirdFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        ResourceType boardManufacturedResource = ResourceType.Shield;
        List<ResourceType> perkManufacturedResource = new ArrayList<>();
        perkManufacturedResource.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 3);
        resourcesToEliminateChest.put(ResourceType.Stone, 3);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(devCardIDs, leaderCardIDs, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), resourcesToEliminateChest,
                ProductionType.BoardAndDevCardAndLeaderCard, boardManufacturedResource, perkManufacturedResource);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
        productionMove.performMove(match);
        Map<ResourceType, Integer> newCurrentChestResources = new HashMap<>();
        newCurrentChestResources.put(ResourceType.Shield, 2);
        newCurrentChestResources.put(ResourceType.Servant, 3);
        for (ResourceType type : newCurrentChestResources.keySet()) {
            assertTrue(player.getBoard().getChest().containsKey(type));
            assertEquals(newCurrentChestResources.get(type), player.getBoard().getChest().get(type));
        }
        for(ResourceType resourceType : player.getBoard().getWarehouse().mapAllContainedResources().keySet()){
            assertEquals(0,player.getBoard().getWarehouse().mapAllContainedResources().get(resourceType));
        }
    }

    @Test
    // Board, DevelopmentCard and LeaderCard production with not enough resources to complete the move
    void notEnoughResourcesBoardAndDevCardAndLeaderCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        manufacturedResources.put(ResourceType.Shield, 1);
        manufacturedResources.put(ResourceType.Servant, 2);
        Production production = new Production(resourcesRequired, manufacturedResources);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "DEV_id", 1, resourcesRequired);
        List<String> devCardIDs = new ArrayList<>();
        devCardIDs.add(devCard.getId());
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("LEADER_id", perk, 1, resourcesRequiredForActivation, null, null);
        List<String> leaderCardIDs = new ArrayList<>();
        leaderCardIDs.add(leaderCard.getId());
        // adds the DevelopmentCard and the LeaderCard to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        player.getBoard().getLeaderCards().add(leaderCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListThirdFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListThirdFloor.add(resource0);
        resourcesListThirdFloor.add(resource0);
        resourcesListThirdFloor.add(resource0);
        shelves.put(ShelfFloor.Third, resourcesListThirdFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 1);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        ResourceType boardManufacturedResource = ResourceType.Shield;
        List<ResourceType> perkManufacturedResource = new ArrayList<>();
        perkManufacturedResource.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 3);
        resourcesToEliminateChest.put(ResourceType.Stone, 3);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(devCardIDs, leaderCardIDs, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), resourcesToEliminateChest,
                ProductionType.BoardAndDevCardAndLeaderCard, boardManufacturedResource, perkManufacturedResource);
        assertFalse(productionMove.isFeasibleMove(match));
    }

    @Test
    // Board, DevelopmentCard and LeaderCard production with enough resources
    // DEVCARD: required = Coin: 1; Stone: 2
    //          manufactured = Shield: 1; Servant: 2
    // LEADERCARD: required = Coin: 1
    //             manufactured = Servant: 1
    void enoughResourcesDevCardAndLeaderCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        manufacturedResources.put(ResourceType.Shield, 1);
        manufacturedResources.put(ResourceType.Servant, 2);
        Production production = new Production(resourcesRequired, manufacturedResources);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "DEV_id", 1, resourcesRequired);
        List<String> devCardIDs = new ArrayList<>();
        devCardIDs.add(devCard.getId());
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("LEADER_id", perk, 1, resourcesRequiredForActivation, null, null);
        List<String> leaderCardIDs = new ArrayList<>();
        leaderCardIDs.add(leaderCard.getId());
        // adds the DevelopmentCard and the LeaderCard to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        player.getBoard().getLeaderCards().add(leaderCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListThirdFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListThirdFloor.add(resource0);
        resourcesListThirdFloor.add(resource0);
        shelves.put(ShelfFloor.Second, resourcesListThirdFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 2);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        List<ResourceType> perkManufacturedResource = new ArrayList<>();
        perkManufacturedResource.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(devCardIDs, leaderCardIDs, requiredResources, resourcesToEliminateWarehouse,
                new HashMap<>(), resourcesToEliminateChest, ProductionType.DevCardAndLeader, null, perkManufacturedResource);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
        productionMove.performMove(match);
        Map<ResourceType, Integer> newCurrentChestResources = new HashMap<>();
        newCurrentChestResources.put(ResourceType.Shield, 1);
        newCurrentChestResources.put(ResourceType.Servant, 3);
        for (ResourceType type : newCurrentChestResources.keySet()) {
            assertTrue(player.getBoard().getChest().containsKey(type));
            assertEquals(newCurrentChestResources.get(type), player.getBoard().getChest().get(type));
        }
        for(ResourceType resourceType : player.getBoard().getWarehouse().mapAllContainedResources().keySet()){
            assertEquals(0,player.getBoard().getWarehouse().mapAllContainedResources().get(resourceType));
        }
    }

    @Test
    // Board, DevelopmentCard and LeaderCard production with enough resources
    // DEVCARD: required = Coin: 1; Stone: 2
    //          manufactured = Shield: 1; Servant: 2
    // LEADERCARD: required = Coin: 1
    //             manufactured = Servant: 1
    void notEnoughResourcesDevCardAndLeaderCardProduction() {
        // creates the Player and the Match
        Player player = new Player("flavio99");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the DevelopmentCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        manufacturedResources.put(ResourceType.Shield, 1);
        manufacturedResources.put(ResourceType.Servant, 2);
        Production production = new Production(resourcesRequired, manufacturedResources);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, production, "DEV_id", 1, resourcesRequired);
        List<String> devCardIDs = new ArrayList<>();
        devCardIDs.add(devCard.getId());
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("LEADER_id", perk, 1, resourcesRequiredForActivation, null, null);
        List<String> leaderCardIDs = new ArrayList<>();
        leaderCardIDs.add(leaderCard.getId());
        // adds the DevelopmentCard and the LeaderCard to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        player.getBoard().getLeaderCards().add(leaderCard);
        // adds necessary resources to the Warehouse and chest
        Map<ShelfFloor, List<Resource>> shelves = player.getBoard().getWarehouse().getShelves();
        List<Resource> resourcesListThirdFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        resourcesListThirdFloor.add(resource0);
        shelves.put(ShelfFloor.First, resourcesListThirdFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 1);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        List<ResourceType> perkManufacturedResource = new ArrayList<>();
        perkManufacturedResource.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        Map<ResourceType, Integer> requiredResources = player.getBoard().sumResourcesMaps(resourcesToEliminateWarehouse, resourcesToEliminateChest);
        ProductionMove productionMove = new ProductionMove(devCardIDs, leaderCardIDs, requiredResources,
                resourcesToEliminateWarehouse, new HashMap<>(), resourcesToEliminateChest,
                ProductionType.DevCardAndLeader, null, perkManufacturedResource);
        // tests the move
        assertFalse(productionMove.isFeasibleMove(match));
    }
    @Test
    void testToString(){
        //TODO
        List<String> devCardIDs = new ArrayList<>();
        devCardIDs.add("ciaoDevCard");
        List<String> leaderCardIDs = new ArrayList<>();
        leaderCardIDs.add("ciaoLeaderCard");
        Move move = new ProductionMove(devCardIDs,
                leaderCardIDs,
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                new HashMap<>(),
                ProductionType.Board,
                null,
                null);
        String string = move.toString();
        assertFalse(string.isEmpty());
        //assertFalse(string.isBlank());
    }
}