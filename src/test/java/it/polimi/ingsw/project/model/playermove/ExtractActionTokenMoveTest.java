package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExtractActionTokenMoveTest {

    @Test
    void isFeasibleMove() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        MoveList moveList = new MoveList();
        Move extractActionTokenMove = new ExtractActionTokenMove();
        moveList.add(extractActionTokenMove);
        PlayerMove playerMove = new PlayerMove(player,
                null,moveList);
        assertTrue(playerMove.isFeasibleMove(match,0));

    }

    @Test
    void performMove() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Match match = new Match(playerList);
        MoveList moveList = new MoveList();
        Move extractActionTokenMove = new ExtractActionTokenMove();
        moveList.add(extractActionTokenMove);
        PlayerMove playerMove = new PlayerMove(player,
                null,moveList);
        assertTrue(playerMove.isFeasibleMove(match,0));

        while(!(match.getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)){
            match.getActionTokenContainer().shuffle();
        }
        extractActionTokenMove.performMove(match);
        assertEquals(2, player.getBoard().getFaithMap().getBlackMarkerPosition());
    }

    @Test
    void testToString() {
    }
}