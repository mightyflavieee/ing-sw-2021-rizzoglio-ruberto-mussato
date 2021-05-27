package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.CardContainer;
import it.polimi.ingsw.project.model.LeaderCardContainer;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.Status;
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
        LeaderCard leaderCard = new LeaderCard("test", null, 1, resourcesRequired,
                null, null);
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
        LeaderCard leaderCard = new LeaderCard("test", null, 1, resourcesRequired,
                null, null);
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
        Player player = new Player("piero");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        // creates the Perk
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Warehouse);
        // creates the LeaderCard
        Map<ResourceType, Integer> resourcesRequired = new HashMap<>();
        resourcesRequired.put(ResourceType.Coin, 1);
        resourcesRequired.put(ResourceType.Stone, 2);
        LeaderCard leaderCard = new LeaderCard("test", perk, 1, resourcesRequired,
                null, null);
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
        activateLeaderCard.performMove(match);
        // tests the function
        assertEquals(Status.Active, player.getBoard().getLeaderCards().get(0).getStatus());
    }
    @Test
    void activateLeaderCardWithDevCard(){
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        CardContainer cardContainer = new CardContainer();
        Perk perk = new Perk(new Resource(ResourceType.Coin), PerkType.Warehouse);
        DevelopmentCard developmentCard = cardContainer.getCardContainer().get(CardLevel.One).get(CardColor.Gold).get(0);
        List<DevelopmentCard> developmentCardList = new ArrayList<>();
        developmentCardList.add(developmentCard);
        Map<CardColor, Integer> requiredDevCard = new HashMap<>();
        requiredDevCard.put(CardColor.Gold,1);
        Map<CardColor, CardLevel> requiredDevCardLevel = new HashMap<>();
        requiredDevCardLevel.put(CardColor.Gold,CardLevel.One);
        LeaderCard leaderCard = new LeaderCard("prova",perk,1,null,requiredDevCard,requiredDevCardLevel);
        player.getBoard().getLeaderCards().add(leaderCard);
        player.getBoard().getMapTray().put(DevCardPosition.Right,developmentCardList);



        Move activateLeaderCard = new ActivateLeaderCardMove("prova");
        activateLeaderCard.performMove(match);
        assertEquals(Status.Active, player.getBoard().getLeaderCards().get(0).getStatus());

    }
    @Test
    void notFeasibleLevel(){
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        LeaderCardContainer leaderCardContainer = new LeaderCardContainer();
        List<LeaderCard> leaderCardList = leaderCardContainer.getLeaderCards();
        LeaderCard leader = null;
        for (LeaderCard leadercard: leaderCardList) {
        if(leadercard.getId().equals("id13")){
            leader = leadercard;
            break;
        }
        }
        player.getBoard().getLeaderCards().add(leader);
        Move move = new ActivateLeaderCardMove(leader.getId());
        assertFalse(move.isFeasibleMove(match));
    }
    @Test
    void notFeasible() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        LeaderCardContainer leaderCardContainer = new LeaderCardContainer();
        List<LeaderCard> leaderCardList = leaderCardContainer.getLeaderCards();
        Move move;
        CardContainer cardContainer = new CardContainer();
        player.getBoard().getMapTray().put(DevCardPosition.Right, cardContainer.getCardContainer().get(CardLevel.One).get(CardColor.Gold));
        for (LeaderCard leadercard : leaderCardList) {
                player.getBoard().getLeaderCards().add(leadercard);
                 move = new ActivateLeaderCardMove(leadercard.getId());
                assertFalse(move.isFeasibleMove(match));

        }
    }


}