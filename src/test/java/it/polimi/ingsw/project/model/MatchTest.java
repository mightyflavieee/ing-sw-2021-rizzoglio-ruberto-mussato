package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.resource.ResourceType;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    @Test
    void performExtractActionTokenMove() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        while (!(match.getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)) {
            match.getActionTokenContainer().shuffle();
        }
        match.performExtractActionTokenMove();
        assertEquals(2, player.getBoard().getFaithMap().getBlackMarkerPosition());
    }

    @Test
    void moveForwardBlack() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        match.moveForwardBlack();
        assertEquals(1, player.getBoard().getFaithMap().getBlackMarkerPosition());
    }

    @Test
    void endGameforBlack() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        for (int i = 1; i < 13; i++) {
            while (!(match.getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)) {
                match.getActionTokenContainer().shuffle();
            }
            match.performExtractActionTokenMove();
            assertEquals(2 * i, player.getBoard().getFaithMap().getBlackMarkerPosition());
        }
        assertEquals(24, player.getBoard().getFaithMap().getBlackMarkerPosition());
        assertTrue(match.getisOver());

    }

    @Test
    void getter() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        assertNotNull(match.getLeaderCardsToString(player.getNickname()));
        assertNotNull(match.getWarehouse(player.getNickname()));
        assertNotNull(match.getBoardByPlayerNickname(player.getNickname()));
        assertNotNull(match.getOpponentsToString(player.getNickname()));
        assertEquals(new ArrayList<ResourceType>(), match.getTransmutationPerk(player.getNickname()));
        assertNotNull(match.getWarehouseToString(player.getNickname()));
        assertNotNull(match.getHistoryToString(player.getNickname()));
    }

}