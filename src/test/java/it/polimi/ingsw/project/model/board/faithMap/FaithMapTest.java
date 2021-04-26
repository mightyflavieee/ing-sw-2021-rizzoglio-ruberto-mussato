package it.polimi.ingsw.project.model.board.faithMap;

import it.polimi.ingsw.project.model.board.faithMap.tile.ActivableTile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FaithMapTest {

    @Test
    void getMarkerPosition() {
    }

    @Test
    void getBlackMarkerPosition() {
    }

    @Test
    void getFaithTiles() {
        FaithMap faithMap = new FaithMap();
        List<ActivableTile> returnedList;
        returnedList = faithMap.getFaithTiles();
        assertNotNull(returnedList);
        assertEquals(24, returnedList.size());
    }

    @Test
    void getPapalFavourSlots() {
        FaithMap faithMap = new FaithMap();
        List<PapalFavourSlot> returnedList;
        returnedList = faithMap.getPapalFavourSlots();
        assertNotNull(returnedList);
        assertEquals(3, returnedList.size());
    }

    @Test
    void moveForward() {
        FaithMap faithMap = new FaithMap();
        int oldPosition, newPosition;
        oldPosition = faithMap.getMarkerPosition();
        assertEquals(0, oldPosition);
        faithMap.moveForward();
        newPosition = faithMap.getMarkerPosition();
        assertEquals(newPosition, oldPosition + 1);
        assertTrue(newPosition <= 24);
    }

    @Test
    void moveForwardBlack() {
        FaithMap faithMap = new FaithMap();
        int oldPosition, newPosition;
        oldPosition = faithMap.getBlackMarkerPosition();
        assertEquals(0, oldPosition);
        faithMap.moveForwardBlack();
        newPosition = faithMap.getBlackMarkerPosition();
        assertEquals(newPosition, oldPosition + 1);
        assertTrue(newPosition <= 24);
    }

    @Test
    void papalCouncil() {
        FaithMap faithMap = new FaithMap();
        for(int i = 1; i < 4; i++){
            {
                int result = faithMap.papalCouncil(i);
                assertTrue(result >= 0 && result <= 24);
            }
        }
    }
}