package it.polimi.ingsw.project.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void moveForward() {Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        player.createFaithMap(new Match(playerList));
        player.moveForward();
        assertEquals(1, player.getBoard().getFaithMap().getMarkerPosition());
        assertTrue(player.getStatus());
    }

    @Test
    void moveForwardBlack() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        player.createFaithMap(new Match(playerList));
        player.moveForwardBlack();
        assertEquals(1, player.getBoard().getFaithMap().getBlackMarkerPosition());
    }

}