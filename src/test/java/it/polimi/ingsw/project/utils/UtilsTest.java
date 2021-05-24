package it.polimi.ingsw.project.utils;

import it.polimi.ingsw.project.model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void extractOpponentsName() {
        Player player1 = new Player("aa");
        Player player2 = new Player("bb");
        Player player3 = new Player("cc");
        Player player4 = new Player("dd");
        List<Player> playerList = new ArrayList<>();
        List<String> opponentsListNames;
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        List<Player> playerListCopy = new ArrayList<>(playerList);
        opponentsListNames = Utils.extractOpponentsName(player4,playerList);
        assertEquals(3,opponentsListNames.size());
        assertTrue(opponentsListNames.contains("aa"));
        assertTrue(opponentsListNames.contains("bb"));
        assertTrue(opponentsListNames.contains("cc"));
        assertTrue(playerList.containsAll(playerListCopy));
        assertTrue(playerListCopy.containsAll(playerList));

    }
}