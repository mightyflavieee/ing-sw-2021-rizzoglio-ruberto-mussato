import org.junit.jupiter.api.Test;

import it.polimi.ingsw.project.model.board.faithMap.FaithMap;

import static org.junit.jupiter.api.Assertions.*;

class FaithMapTest {

    @Test
    void checkMarkerPositionAfterMove() {
       final FaithMap faithMap = new FaithMap();
       faithMap.moveForward();
       assertTrue(faithMap.getMarkerPosition()==1);
    }

    @Test
    void getBlackMarkerPosition() {

    }

    @Test
    void getFaithTiles() {
    }

    @Test
    void getPapalFavourSlots() {
    }

    @Test
    void moveForward() {
    }

    @Test
    void moveForwardBlack() {
    }

    @Test
    void papalCouncil() {
    }
}