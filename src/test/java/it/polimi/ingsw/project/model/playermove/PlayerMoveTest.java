package it.polimi.ingsw.project.model.playermove;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMoveTest {

    @Test
    void getView() {
        PlayerMove playerMove = new PlayerMove(null,null,null);
        assertNull(playerMove.getView());
    }
}