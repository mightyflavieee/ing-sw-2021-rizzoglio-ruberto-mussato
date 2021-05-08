package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.actionTokens.DiscardActionToken;
import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
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
        assertEquals(0,player.getVictoryPoints());
        assertEquals(0,player.getBoard().getFaithMap().getMarkerPosition());
    }

    @Test
    void zeroLevel1(){
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
        DiscardActionToken discardActionToken;
        for(int i = 1; i < 3; i++) {
            do {
                match.getActionTokenContainer().shuffle();
                while (!(match.getActionTokenContainer().getActionTokens().get(0) instanceof DiscardActionToken)) {
                    match.getActionTokenContainer().shuffle();
                }
                discardActionToken = (DiscardActionToken) match.getActionTokenContainer().getActionTokens().get(0);
            } while (discardActionToken.getCardColor() != CardColor.Amethyst);
            extractActionTokenMove.performMove(match);
            assertEquals(4 - 2*i,match.getCardContainer().getCardContainer().get(CardLevel.One).get(CardColor.Amethyst).size());
        }
        assertEquals(0,player.getVictoryPoints());
        assertEquals(0,player.getBoard().getFaithMap().getMarkerPosition());
    }

    @Test
    void lostByDiscard(){
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
        DiscardActionToken discardActionToken;
        for(int i = 0; i < 6; i++) {
            do {
                match.getActionTokenContainer().shuffle();
                while (!(match.getActionTokenContainer().getActionTokens().get(0) instanceof DiscardActionToken)) {
                    match.getActionTokenContainer().shuffle();
                }
                discardActionToken = (DiscardActionToken) match.getActionTokenContainer().getActionTokens().get(0);
            } while (discardActionToken.getCardColor() != CardColor.Emerald);
            extractActionTokenMove.performMove(match);
        }
        assertEquals(0,player.getVictoryPoints());
        assertEquals(0,player.getBoard().getFaithMap().getMarkerPosition());
        assertTrue(match.getisOver());
    }



    @Test
    void testToString() {
    }
}