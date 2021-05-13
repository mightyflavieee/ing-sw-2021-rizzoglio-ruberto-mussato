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
        List<Resource> resourceList1Gian = new ArrayList<>();
        // inserisco in basso a destra una biglia rossa
        resourceList1Gian = localMarket.insertMarble(1, 0, false, ResourceType.Faith);
        Warehouse warehouse1Gian = new Warehouse();
        warehouse1Gian.getShelves().get(ShelfFloor.First).add(new Resource(ResourceType.Coin));
        warehouse1Gian.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Servant));
        warehouse1Gian.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Servant));
        Move move1Gian = new TakeMarketResourcesMove(warehouse1Gian, null, localMarket);
        MoveList moveList1Gian = new MoveList();
        moveList1Gian.add(move1Gian);
        PlayerMove playerMove1Gian = new PlayerMove(gianluca, null, moveList1Gian);
        // controller.update(playerMove1Gian);

        // move flavio

    }

}