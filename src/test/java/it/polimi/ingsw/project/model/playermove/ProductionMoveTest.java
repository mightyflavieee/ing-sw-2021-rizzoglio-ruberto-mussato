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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        LeaderCard leaderCard = new LeaderCard("test", perk, 1, resourcesRequiredForActivation);
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
        List<ResourceType> boardManufacturedResource = new ArrayList<>();
        boardManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        ProductionMove productionMove = new ProductionMove(null, "test", resourcesToEliminateWarehouse,
                null, ProductionType.LeaderCard, boardManufacturedResource);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
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
        LeaderCard leaderCard = new LeaderCard("test", perk, 1, resourcesRequiredForActivation);
        // adds the LeaderCard to the Board
        player.getBoard().getLeaderCards().add(leaderCard);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        List<ResourceType> boardManufacturedResource = new ArrayList<>();
        boardManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
        ProductionMove productionMove = new ProductionMove(null, "test", resourcesToEliminateWarehouse,
                null, ProductionType.LeaderCard, boardManufacturedResource);
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
        resourcesListFirstFloor.add(resource0);
        shelves.put(ShelfFloor.First, resourcesListFirstFloor);
        player.getBoard().getChest().put(ResourceType.Stone, 3);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        List<ResourceType> boardManufacturedResource = new ArrayList<>();
        boardManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 3);
        ProductionMove productionMove = new ProductionMove("test", null, resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.BoardAndDevCard, boardManufacturedResource);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
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
        // adds the card to the Board
        player.getBoard().getMapTray().get(DevCardPosition.Left).add(devCard);
        // creates the move
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        List<ResourceType> boardManufacturedResource = new ArrayList<>();
        boardManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 3);
        ProductionMove productionMove = new ProductionMove("test", null, resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.BoardAndDevCard, boardManufacturedResource);
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
        LeaderCard leaderCard = new LeaderCard("test", perk, 1, resourcesRequiredForActivation);
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
        List<ResourceType> boardManufacturedResource = new ArrayList<>();
        boardManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 1);
        ProductionMove productionMove = new ProductionMove(null, "test", resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.BoardAndLeaderCard, boardManufacturedResource);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
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
        LeaderCard leaderCard = new LeaderCard("test", perk, 1, resourcesRequiredForActivation);
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
        List<ResourceType> boardManufacturedResource = new ArrayList<>();
        boardManufacturedResource.add(ResourceType.Shield);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 1);
        ProductionMove productionMove = new ProductionMove(null, "test", resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.BoardAndLeaderCard, boardManufacturedResource);
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
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("LEADER_id", perk, 1, resourcesRequiredForActivation);
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
        List<ResourceType> boardAndPerkManufacturedResources = new ArrayList<>();
        boardAndPerkManufacturedResources.add(ResourceType.Shield);
        boardAndPerkManufacturedResources.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 3);
        resourcesToEliminateChest.put(ResourceType.Stone, 3);
        ProductionMove productionMove = new ProductionMove("DEV_id", "LEADER_id", resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.BoardAndDevCardAndLeaderCard, boardAndPerkManufacturedResources);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
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
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("LEADER_id", perk, 1, resourcesRequiredForActivation);
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
        List<ResourceType> boardAndPerkManufacturedResources = new ArrayList<>();
        boardAndPerkManufacturedResources.add(ResourceType.Shield);
        boardAndPerkManufacturedResources.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 3);
        resourcesToEliminateChest.put(ResourceType.Stone, 3);
        ProductionMove productionMove = new ProductionMove("DEV_id", "LEADER_id", resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.BoardAndDevCardAndLeaderCard, boardAndPerkManufacturedResources);
        // tests the move
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
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("LEADER_id", perk, 1, resourcesRequiredForActivation);
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
        List<ResourceType> LeaderCardManufacturedResources = new ArrayList<>();
        LeaderCardManufacturedResources.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        ProductionMove productionMove = new ProductionMove("DEV_id", "LEADER_id", resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.BoardAndDevCardAndLeaderCard, LeaderCardManufacturedResources);
        // tests the move
        assertTrue(productionMove.isFeasibleMove(match));
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
        // creates the LeaderCard
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Production);
        Map<ResourceType, Integer> resourcesRequiredForActivation = new HashMap<>();
        resourcesRequiredForActivation.put(ResourceType.Coin, 1);
        resourcesRequiredForActivation.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("LEADER_id", perk, 1, resourcesRequiredForActivation);
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
        List<ResourceType> LeaderCardManufacturedResources = new ArrayList<>();
        LeaderCardManufacturedResources.add(ResourceType.Servant);
        resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
        resourcesToEliminateChest.put(ResourceType.Stone, 2);
        ProductionMove productionMove = new ProductionMove("DEV_id", "LEADER_id", resourcesToEliminateWarehouse,
                resourcesToEliminateChest, ProductionType.BoardAndDevCardAndLeaderCard, LeaderCardManufacturedResources);
        // tests the move
        assertFalse(productionMove.isFeasibleMove(match));
    }
}