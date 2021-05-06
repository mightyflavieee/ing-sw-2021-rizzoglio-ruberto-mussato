package it.polimi.ingsw.project.model.market;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarbleTest {

    @Test
    void getType() {
        MarbleType marbleType = MarbleType.Grey;
        Marble marble = new Marble(marbleType);
        assertEquals(marbleType,marble.getType());
    }
}