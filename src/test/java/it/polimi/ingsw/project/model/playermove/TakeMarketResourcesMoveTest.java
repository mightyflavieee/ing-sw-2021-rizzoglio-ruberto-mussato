package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.market.Marble;
import it.polimi.ingsw.project.model.market.MarbleType;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TakeMarketResourcesMoveTest {

    @Test
    void isFeasibleMove() {
        Player gianluca = new Player("Gianluca");
        Player flavio = new Player("Flavio");
        Player leo = new Player("Leo");
        List<Player> playerList = new ArrayList<>();
        playerList.add(gianluca);
        playerList.add(flavio);
        playerList.add(leo);
        Match match = new Match(playerList);

        // removing the random beginning of the match
        Marble[][] tray = new Marble[4][3];
        List<Marble> trayList = new ArrayList<>();
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
        match.getMarket().setOutsideMarble(outsideMarble);
        assertEquals(outsideMarble.getType(), match.getMarket().getOutSideMarble().getType());
        match.getMarket().setTray(tray);

        // beginning of the match

        // turn 1 gianluca
        Market localMarket = new Market();
        localMarket.setTray(tray);
        localMarket.setOutsideMarble(outsideMarble);
        //List<Resource> resourceList1Gian = new ArrayList<>();
        // inserisco in basso a destra una biglia rossa
      //  resourceList1Gian = localMarket.insertMarble(0, 1, null);
        Warehouse warehouse1Gian = new Warehouse(null);
        warehouse1Gian.getShelves().get(ShelfFloor.First).add(new Resource(ResourceType.Coin));
        warehouse1Gian.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Servant));
        warehouse1Gian.getShelves().get(ShelfFloor.Second).add(new Resource(ResourceType.Servant));
        Move move1Gian = new TakeMarketResourcesMove(warehouse1Gian, new ArrayList<>(), localMarket, false);
        assertTrue(move1Gian.isFeasibleMove(match));

    }

}