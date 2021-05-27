package it.polimi.ingsw.project.model.playermove;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void testToString() {
        assertEquals("Generic Move", new Move().toString());
    }
}