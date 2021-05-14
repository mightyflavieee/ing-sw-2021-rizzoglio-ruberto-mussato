package it.polimi.ingsw.project.controller;

import it.polimi.ingsw.project.model.LeaderCardContainerBuilder;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.PerkType;
import it.polimi.ingsw.project.model.board.faithMap.PapalSlotStatus;
import it.polimi.ingsw.project.model.board.faithMap.tile.PapalCouncilTile;
import it.polimi.ingsw.project.model.market.Marble;
import it.polimi.ingsw.project.model.market.MarbleType;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.playermove.*;
import it.polimi.ingsw.project.*;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.server.ClientConnection;
import it.polimi.ingsw.project.server.Server;
import it.polimi.ingsw.project.server.SocketClientConnection;
import it.polimi.ingsw.project.view.RemoteView;
import it.polimi.ingsw.project.view.View;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Object ResouceType;

    @Test
    void update() {
    }

    @Test
    void lorenzoTest() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList);
        MoveList moveList = new MoveList();
        Move extractActionTokenMove = new ExtractActionTokenMove();
        moveList.add(extractActionTokenMove);
        PlayerMove playerMove = new PlayerMove(player, null, moveList);
        Controller controller = new Controller(model);
        while (!(model.getMatch().getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)) {
            model.getMatch().getActionTokenContainer().shuffle();
        }
        controller.update(playerMove);
        assertTrue(2 == player.getBoard().getFaithMap().getBlackMarkerPosition());

    }

    @Test
    void completeTest() {
        Player gianluca = new Player("Gianluca");
        Player flavio = new Player("Flavio");
        Player leo = new Player("Leo");
        List<Player> playerList = new ArrayList<>();
        playerList.add(gianluca);
        playerList.add(flavio);
        playerList.add(leo);
        Model model = new Model(playerList);
        Controller controller = new Controller(model);
        Perk perkLeaderCardGian1 = new Perk(new Resource(ResourceType.Servant), PerkType.Discount);
        Perk perkLeaderCardGian2 = new Perk(new Resource(ResourceType.Shield), PerkType.Discount);
        Perk perkLeaderCardFlavio1 = new Perk(new Resource(ResourceType.Stone), PerkType.Discount);
        Perk perkLeaderCardFlavio2 = new Perk(new Resource(ResourceType.Coin), PerkType.Discount);
        Perk perkLeaderCardLeo1 = new Perk(new Resource(ResourceType.Stone), PerkType.Warehouse);
        Perk perkLeaderCardLeo2 = new Perk(new Resource(ResourceType.Servant), PerkType.Warehouse);
        Map<CardColor, Integer> requirementsLeaderCardGian1 = new HashMap<>();
        Map<CardColor, Integer> requirementsLeaderCardGian2 = new HashMap<>();
        Map<CardColor, Integer> requirementsLeaderCardFlavio1 = new HashMap<>();
        Map<CardColor, Integer> requirementsLeaderCardFlavio2 = new HashMap<>();
        Map<ResourceType, Integer> requirementsLeaderCardLeo1 = new HashMap<>();
        Map<ResourceType, Integer> requirementsLeaderCardLeo2 = new HashMap<>();
        requirementsLeaderCardGian1.put(CardColor.Gold, 1);
        requirementsLeaderCardGian1.put(CardColor.Emerald, 1);
        requirementsLeaderCardGian2.put(CardColor.Sapphire, 1);
        requirementsLeaderCardGian2.put(CardColor.Amethyst, 1);
        requirementsLeaderCardFlavio1.put(CardColor.Emerald, 1);
        requirementsLeaderCardFlavio1.put(CardColor.Sapphire, 1);
        requirementsLeaderCardFlavio2.put(CardColor.Gold, 1);
        requirementsLeaderCardFlavio2.put(CardColor.Amethyst, 1);
        requirementsLeaderCardLeo1.put(ResourceType.Coin, 5);
        requirementsLeaderCardLeo2.put(ResourceType.Stone, 5);
        LeaderCard leaderCardGian1 = new LeaderCard("id1", perkLeaderCardGian1, 2, null, requirementsLeaderCardGian1,
                null);
        LeaderCard leaderCardGian2 = new LeaderCard("id2", perkLeaderCardGian2, 2, null, requirementsLeaderCardGian2,
                null);
        LeaderCard leaderCardFlavio1 = new LeaderCard("id3", perkLeaderCardFlavio1, 2, null,
                requirementsLeaderCardFlavio1, null);
        LeaderCard leaderCardFlavio2 = new LeaderCard("id4", perkLeaderCardFlavio2, 2, null,
                requirementsLeaderCardFlavio2, null);
        LeaderCard leaderCardLeo1 = new LeaderCard("id5", perkLeaderCardLeo1, 3, requirementsLeaderCardLeo1, null,
                null);
        LeaderCard leaderCardLeo2 = new LeaderCard("id6", perkLeaderCardLeo2, 3, requirementsLeaderCardLeo2, null,
                null);

        gianluca.getBoard().getLeaderCards().add(leaderCardGian1);
        gianluca.getBoard().getLeaderCards().add(leaderCardGian2);
        flavio.getBoard().getLeaderCards().add(leaderCardFlavio1);
        flavio.getBoard().getLeaderCards().add(leaderCardFlavio2);
        leo.getBoard().getLeaderCards().add(leaderCardLeo1);
        leo.getBoard().getLeaderCards().add(leaderCardLeo2);

        // removing the random beginning of the match
        Marble[][] tray = new Marble[4][3];
        List<Marble> trayList = new ArrayList<Marble>();
        trayList.add(new Marble(MarbleType.White));
        trayList.add(new Marble(MarbleType.White));
        trayList.add(new Marble(MarbleType.White));
        trayList.add(new Marble(MarbleType.White));
        trayList.add(new Marble(MarbleType.Blue));
        trayList.add(new Marble(MarbleType.Blue));
        trayList.add(new Marble(MarbleType.Grey));
        trayList.add(new Marble(MarbleType.Grey));
        trayList.add(new Marble(MarbleType.Yellow));
        trayList.add(new Marble(MarbleType.Yellow));
        trayList.add(new Marble(MarbleType.Purple));
        trayList.add(new Marble(MarbleType.Purple));
        trayList.add(new Marble(MarbleType.Red));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                tray[i][j] = trayList.remove(0);
            }
        }
        Marble outsideMarble = trayList.remove(0);
        model.getMatch().getMarket().setOutsideMarble(outsideMarble);
        assertEquals(outsideMarble.getType(), model.getMatch().getMarket().getOutSideMarble().getType());
        model.getMatch().getMarket().setTray(tray);

        // beginning of the match

        // turn 1 gianluca
        Market localMarket = new Market();
        localMarket.setTray(tray);
        localMarket.setOutsideMarble(outsideMarble);
        List<Resource> resourceList1Gian = localMarket.insertMarble(0, 1, Optional.empty());
        Warehouse warehouse1Gian = new Warehouse(model.getMatch());
        warehouse1Gian.getShelves().get(ShelfFloor.First).add(new Resource(ResourceType.Coin));
        warehouse1Gian.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Servant));
        warehouse1Gian.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Servant));
        Move move1Gian = new TakeMarketResourcesMove(warehouse1Gian, new ArrayList<Resource>(), localMarket, false);
        MoveList moveList1Gian = new MoveList();
        moveList1Gian.add(move1Gian);
        PlayerMove playerMove1Gian = new PlayerMove(gianluca, null, moveList1Gian);
        controller.update(playerMove1Gian);
        assertEquals(0, gianluca.getVictoryPoints());
        assertEquals(0, gianluca.getBoard().getFaithMap().getMarkerPosition());
        assertEquals(1, gianluca.getBoard().getWarehouse().getShelves().get(ShelfFloor.First).size());
        assertEquals(2, gianluca.getBoard().getWarehouse().getShelves().get(ShelfFloor.Second).size());
        assertEquals(ResourceType.Coin, gianluca.getBoard().getWarehouse().getShelves().get(ShelfFloor.First).get(0).getType());
        assertEquals(ResourceType.Servant, gianluca.getBoard().getWarehouse().getShelves().get(ShelfFloor.Second).get(0).getType());
        assertEquals(ResourceType.Servant, gianluca.getBoard().getWarehouse().getShelves().get(ShelfFloor.Second).get(1).getType());


        // turn 1 flavio
        Warehouse warehouse1Flavio = new Warehouse(model.getMatch());
        localMarket.insertMarble(0, 2, Optional.empty());
        warehouse1Flavio.getShelves().get(ShelfFloor.Third).add(new Resource(ResourceType.Servant));
        warehouse1Flavio.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Stone));
        warehouse1Flavio.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Stone));
        Move move1Flavio = new TakeMarketResourcesMove(warehouse1Flavio, new ArrayList<Resource>(), localMarket, false);
        MoveList moveList1Flavio = new MoveList();
        moveList1Flavio.add(move1Flavio);
        PlayerMove playerMove1Flavio = new PlayerMove(flavio, null, moveList1Flavio);
        controller.update(playerMove1Flavio);
        assertEquals(0, flavio.getVictoryPoints());
        assertEquals(0, flavio.getBoard().getFaithMap().getMarkerPosition());
        assertEquals(ResourceType.Servant, flavio.getBoard().getWarehouse().getShelves().get(ShelfFloor.Third).get(0).getType());
        assertEquals(ResourceType.Stone, flavio.getBoard().getWarehouse().getShelves().get(ShelfFloor.Second).get(0).getType());
        assertEquals(ResourceType.Stone, flavio.getBoard().getWarehouse().getShelves().get(ShelfFloor.Second).get(1).getType());


        // turn 1
        Warehouse warehouse1Leo = new Warehouse(model.getMatch());
        localMarket.insertMarble(1, 2, Optional.empty());
        warehouse1Leo.getShelves().get(ShelfFloor.Third).add(new Resource(ResourceType.Shield));
        warehouse1Leo.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Coin));
        warehouse1Leo.getShelves().get(ShelfFloor.First).add(new Resource(ResourceType.Stone));
        Boolean hasRedMarble = true;
        Move move1Leo = new TakeMarketResourcesMove(warehouse1Leo, new ArrayList<Resource>(), localMarket, hasRedMarble);
        MoveList moveList1Leo = new MoveList();
        moveList1Leo.add(move1Leo);
        PlayerMove playerMove1Leo = new PlayerMove(leo, null, moveList1Leo);
        controller.update(playerMove1Leo);
        assertEquals(0, leo.getVictoryPoints());
        assertEquals(1, leo.getBoard().getFaithMap().getMarkerPosition());
        assertEquals(ResourceType.Shield, leo.getBoard().getWarehouse().getShelves().get(ShelfFloor.Third).get(0).getType());
        assertEquals(ResourceType.Coin, leo.getBoard().getWarehouse().getShelves().get(ShelfFloor.Second).get(0).getType());
        assertEquals(ResourceType.Stone, leo.getBoard().getWarehouse().getShelves().get(ShelfFloor.First).get(0).getType());

        // turn 2 gianluca
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        resourcesToEliminateWarehouse.put(ResourceType.Servant, 2);
        Move move2Gian = new BuyDevCardMove("id2", DevCardPosition.Right, resourcesToEliminateWarehouse, new HashMap<>());
        MoveList moveList2Gian = new MoveList();
        moveList2Gian.add(move2Gian);
        PlayerMove playerMove2Gian = new PlayerMove(gianluca, null, moveList2Gian);
        controller.update(playerMove2Gian);
        assertEquals(0, gianluca.getVictoryPoints());
        assertEquals(0, gianluca.getBoard().getFaithMap().getMarkerPosition());
        assertEquals(1, gianluca.getBoard().getWarehouse().getShelves().get(ShelfFloor.First).size());
        assertEquals(0, gianluca.getBoard().getWarehouse().getShelves().get(ShelfFloor.Second).size());
        assertEquals(ResourceType.Coin, gianluca.getBoard().getWarehouse().getShelves().get(ShelfFloor.First).get(0).getType());
        assertEquals(2, gianluca.getBoard().getLeaderCards().size());
        assertTrue(gianluca.getBoard().getMapTray().get(DevCardPosition.Right).get(0).getId().equals("id2"));


        // turn 2 flavio
        resourcesToEliminateWarehouse.clear();
        Move move2Flavio = new DiscardLeaderCardMove("id3");
        MoveList moveList2Flavio = new MoveList();
        moveList2Flavio.add(move2Flavio);
        PlayerMove playerMove2Flavio = new PlayerMove(flavio, null, moveList2Flavio);
        controller.update(playerMove2Flavio);
        assertEquals(1, flavio.getBoard().getLeaderCards().size());
        assertEquals("id4", flavio.getBoard().getLeaderCards().get(0).getId());
        assertEquals(1, flavio.getBoard().getFaithMap().getMarkerPosition());
        assertEquals(0, flavio.getVictoryPoints());


        // turn 2 Leo
        resourcesToEliminateWarehouse.clear();
        Move move2Leo = new DiscardLeaderCardMove("id3");
        Warehouse warehouse2Leo = new Warehouse(model.getMatch());
        localMarket.insertMarble(1, 2, Optional.empty());
        warehouse2Leo.getShelves().get(ShelfFloor.Third).add(new Resource(ResourceType.Shield));
        warehouse2Leo.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Coin));
        warehouse2Leo.getShelves().get(ShelfFloor.First).add(new Resource(ResourceType.Stone));
        List<Resource> discardedResources = new ArrayList<Resource>();
        discardedResources.add(new Resource(ResourceType.Shield));
        discardedResources.add(new Resource(ResourceType.Servant));
        discardedResources.add(new Resource(ResourceType.Servant));
        Move move3Leo = new TakeMarketResourcesMove(warehouse2Leo, discardedResources, localMarket, false);
        MoveList moveList2Leo = new MoveList();
        moveList2Leo.add(move2Leo);
        moveList2Leo.add(move3Leo);
        PlayerMove playerMove2Leo = new PlayerMove(leo, null, moveList2Leo);
        controller.update(playerMove2Leo);
        assertEquals(2, leo.getBoard().getLeaderCards().size());
        assertEquals(1, leo.getBoard().getFaithMap().getMarkerPosition());
        MoveList moveList3Leo = new MoveList();
        moveList3Leo.add(move3Leo);
        PlayerMove playerMove3Leo = new PlayerMove(leo, null, moveList3Leo);
        controller.update(playerMove3Leo);
        assertEquals(0, leo.getVictoryPoints());
        assertEquals(4, flavio.getBoard().getFaithMap().getMarkerPosition());
        assertEquals(3, gianluca.getBoard().getFaithMap().getMarkerPosition());
        assertEquals(1, flavio.getVictoryPoints());
        assertEquals(1, gianluca.getVictoryPoints());


        // turn 3 gianluca
        resourcesToEliminateWarehouse.clear();
        resourcesToEliminateWarehouse = gianluca.getBoard().getMapTray().get(DevCardPosition.Right).get(0).getProduction().getRequiredResources();
        ProductionMove move3Gian = new ProductionMove("id2", null, resourcesToEliminateWarehouse,
                null, ProductionType.Board, null);
        MoveList moveList3Gian = new MoveList();
        moveList3Gian.add(move3Gian);
        PlayerMove playerMove3Gian = new PlayerMove(gianluca, null, moveList3Gian);
        controller.update(playerMove3Gian);

        // end game
        flavio.getBoard().moveForward();//1
        flavio.getBoard().moveForward();//2
        flavio.getBoard().moveForward();
        flavio.getBoard().moveForward();//4
        flavio.getBoard().moveForward();
        flavio.getBoard().moveForward();//6
        flavio.getBoard().moveForward();
        flavio.getBoard().moveForward();//8
        flavio.getBoard().moveForward();
        flavio.getBoard().moveForward();//10
        flavio.getBoard().moveForward();
        flavio.getBoard().moveForward();//12
        flavio.getBoard().moveForward();
        flavio.getBoard().moveForward();//14
        flavio.getBoard().moveForward();
        flavio.getBoard().moveForward();//16
        flavio.getBoard().moveForward();
        flavio.getBoard().moveForward();//18
        flavio.getBoard().moveForward();//4+19=23 partita non finita
        assertFalse(model.getMatch().getIsLastTurn());
        assertEquals(PapalSlotStatus.Taken,flavio.getBoard().getFaithMap().getPapalFavourSlots().get(0).getStatus());
        assertEquals(PapalSlotStatus.Taken,flavio.getBoard().getFaithMap().getPapalFavourSlots().get(1).getStatus());
        assertEquals(PapalSlotStatus.Available,flavio.getBoard().getFaithMap().getPapalFavourSlots().get(2).getStatus());
        flavio.getBoard().moveForward();
        assertTrue(model.getMatch().getIsLastTurn());
        assertEquals(PapalSlotStatus.Taken,flavio.getBoard().getFaithMap().getPapalFavourSlots().get(0).getStatus());
        assertEquals(PapalSlotStatus.Taken,flavio.getBoard().getFaithMap().getPapalFavourSlots().get(1).getStatus());
        assertEquals(PapalSlotStatus.Taken,flavio.getBoard().getFaithMap().getPapalFavourSlots().get(2).getStatus());
    }

}