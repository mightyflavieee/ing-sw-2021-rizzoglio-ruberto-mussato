package it.polimi.ingsw.project.model.market;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    @Test
    void getTray() {
        int grey = 0, blue = 0, purple = 0, red = 0, white = 0, yellow = 0;
        Market market = new Market();
        Marble[][] tray = market.getTray();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                MarbleType type = tray[i][j].getType();
                switch (type) {
                    case Blue:
                        blue++;
                        break;
                    case Purple:
                        purple++;
                        break;
                    case Yellow:
                        yellow++;
                        break;
                    case Grey:
                        grey++;
                        break;
                    case Red:
                        red++;
                        break;
                    case White:
                        white++;
                        break;
                }
            }
        }
        switch (market.getOutSideMarble().getType()) {
            case Blue:
                blue++;
                break;
            case Purple:
                purple++;
                break;
            case Yellow:
                yellow++;
                break;
            case Grey:
                grey++;
                break;
            case Red:
                red++;
                break;
            case White:
                white++;
                break;
        }
        assertEquals(4, white);
        assertEquals(2, blue);
        assertEquals(2, grey);
        assertEquals(2, yellow);
        assertEquals(2, purple);
        assertEquals(1, red);
    }

    @Test
    void getOutSideMarble() {
        Market market = new Market();
        assertNotNull(market.getOutSideMarble());
        assertTrue(MarbleType.Grey == market.getOutSideMarble().getType()
                || MarbleType.Blue == market.getOutSideMarble().getType()
                || MarbleType.Purple == market.getOutSideMarble().getType()
                || MarbleType.Red == market.getOutSideMarble().getType()
                || MarbleType.White == market.getOutSideMarble().getType()
                || MarbleType.Yellow == market.getOutSideMarble().getType());
    }

    @Test
    void insertMarble() {
        List<Resource>  returnedResourceList;
        Market market = new Market();
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
        market.setOutsideMarble(outsideMarble);
        market.setTray(tray);
        returnedResourceList = market.insertMarble(1, 2, null);
        int grey = 0, blue = 0, purple = 0, red = 0,  yellow = 0;

        for (Resource resource : returnedResourceList) {
            ResourceType type = resource.getType();
            switch (type) {
                case Shield:
                    blue++;
                    break;
                case Servant:
                    purple++;
                    break;
                case Coin:
                    yellow++;
                    break;
                case Stone:
                    grey++;
                    break;
                case Faith:
                    red++;
                    break;
                default:
                    break;
            }
        }
        assertEquals(purple, 1);
        assertEquals(yellow, 1);
        assertEquals(blue, 1);
        Marble[][] newtray = new Marble[4][3];
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
                newtray[i][j] = trayList.remove(0);
            }
        }
        Marble newoutsideMarble = trayList.remove(0);
        for (int i = 0; i < 3; i++) {
            newtray[i][2] = newtray[i + 1][2];
        }
        newtray[3][2] = newoutsideMarble;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(newtray[i][j].getType(), market.getTray()[i][j].getType());
            }
        }
    }
    @Test
    void testToString(){
        Market market = new Market();
        String string = market.toString();
        assertFalse(string.isEmpty());
      //  assertFalse(string.isBlank());
    }
}
