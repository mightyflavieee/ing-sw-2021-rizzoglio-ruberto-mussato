package it.polimi.ingsw.project.model.market;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    @Test
    void getTray() {
        int grey = 0, blue = 0, purple = 0, red = 0, white = 0, yellow = 0;
        Market market = new Market();
        Marble [][] tray = market.getTray();
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 3; j++){
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
        assertTrue(MarbleType.Grey == market.getOutSideMarble().getType() ||
                    MarbleType.Blue == market.getOutSideMarble().getType() ||
                    MarbleType.Purple == market.getOutSideMarble().getType() ||
                    MarbleType.Red == market.getOutSideMarble().getType() ||
                    MarbleType.White == market.getOutSideMarble().getType() ||
                    MarbleType.Yellow == market.getOutSideMarble().getType());
    }

    @Test
    void insertMarble() {
    }
}