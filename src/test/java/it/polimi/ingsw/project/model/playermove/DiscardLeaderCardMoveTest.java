package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DiscardLeaderCardMoveTest {

    @Test
    void isFeasibleMove() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        Map<ResourceType, Integer> resoucesRequired = new HashMap<>();
        resoucesRequired.put(ResourceType.Servant, 1);
        resoucesRequired.put(ResourceType.Shield, 1);
        LeaderCard leaderCard = new LeaderCard("prova", null, 1, resoucesRequired,
                null, null);
        player.getBoard().getLeaderCards().add(leaderCard);
        Move discardLeaderCardMove = new DiscardLeaderCardMove("prova");
        assertTrue(discardLeaderCardMove.isFeasibleMove(match));
    }

    @Test
    void performMove() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        Map<ResourceType, Integer> resoucesRequired = new HashMap<>();
        resoucesRequired.put(ResourceType.Servant, 1);
        resoucesRequired.put(ResourceType.Shield, 1);
        LeaderCard leaderCard = new LeaderCard("prova", null, 1, resoucesRequired,
                null, null);
        player.getBoard().getLeaderCards().add(leaderCard);
        Move discardLeaderCardMove = new DiscardLeaderCardMove("prova");
        assertTrue(discardLeaderCardMove.isFeasibleMove(match));
        discardLeaderCardMove.performMove(match);
        assertEquals(1, player.getBoard().getFaithMap().getMarkerPosition());
        assertFalse(discardLeaderCardMove.isFeasibleMove(match));
    }
    @Test
    void discardToWin(){
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        Map<ResourceType, Integer> resoucesRequired = new HashMap<>();
        resoucesRequired.put(ResourceType.Servant, 1);
        resoucesRequired.put(ResourceType.Shield, 1);
        LeaderCard leaderCard = new LeaderCard("prova", null, 1, resoucesRequired,
                null, null);
        Move discardLeaderCardMove = new DiscardLeaderCardMove("prova");
        for(int i = 1; i < 25; i++) {
            player.getBoard().getLeaderCards().add(leaderCard);
            discardLeaderCardMove.performMove(match);
            assertEquals(i, player.getBoard().getFaithMap().getMarkerPosition());
        }
        assertEquals(24, player.getBoard().getFaithMap().getMarkerPosition());
        assertTrue(match.getIsLastTurn());
    }

    @Test
    void testToString() {
    }
}