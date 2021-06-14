package it.polimi.ingsw.project.model.actionTokens;

import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionTokenContainerTest {

    @Test
    void getActionTokens() {
        ActionTokenContainer actionTokenContainer;
        actionTokenContainer = new ActionTokenContainer(null);
        assertNotNull(actionTokenContainer.getActionTokens());
        assertEquals(6, actionTokenContainer.getActionTokens().size());
    }

    @Test
    void shuffle() {
        ActionTokenContainer actionTokenContainer;
        List<ActionToken> oldList;
        List<ActionToken> newList;
        actionTokenContainer = new ActionTokenContainer(null);
        oldList = actionTokenContainer.getActionTokens();
        actionTokenContainer.shuffle();
        newList = actionTokenContainer.getActionTokens();
        assertEquals(oldList.size(), newList.size());
        assertTrue(newList.containsAll(oldList));
        assertTrue(oldList.containsAll(newList));
        assertFalse(newList.get(0) == oldList.get(0) && newList.get(1) == oldList.get(1)
                && newList.get(2) == oldList.get(2) && newList.get(3) == oldList.get(3)
                && newList.get(4) == oldList.get(4) && newList.get(5) == oldList.get(5));

    }

    @Test
    void lorenzoTest() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList, null);
        while (!(model.getMatch().getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)) {
            model.getMatch().getActionTokenContainer().shuffle();
        }
        model.getMatch().performExtractActionTokenMove();
        assertEquals(2, player.getBoard().getFaithMap().getBlackMarkerPosition());

    }

    @Test
    void lostToBlackMarker() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList, null);
        for (int i = 1; i < 13; i++) {
            while (!(model.getMatch().getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)) {
                model.getMatch().getActionTokenContainer().shuffle();
            }
            model.getMatch().performExtractActionTokenMove();
            assertEquals(i * 2, player.getBoard().getFaithMap().getBlackMarkerPosition());
        }
        assertTrue(model.getMatch().getisOver());
    }

    @Test
    void discardActionToken() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList, null);
        assertEquals(4,
                model.getMatch().getCardContainer().getCardContainer().get(CardLevel.One).get(CardColor.Gold).size());
        do {
            model.getMatch().getActionTokenContainer().shuffle();
            while (!(model.getMatch().getActionTokenContainer().getActionTokens()
                    .get(0) instanceof DiscardActionToken)) {
                model.getMatch().getActionTokenContainer().shuffle();
            }
        } while (!(((DiscardActionToken) model.getMatch().getActionTokenContainer().getActionTokens().get(0))
                .getCardColor() == CardColor.Gold));
        model.getMatch().performExtractActionTokenMove();
        assertEquals(2,
                model.getMatch().getCardContainer().getCardContainer().get(CardLevel.One).get(CardColor.Gold).size());
    }

    @Test
    void lostToDiscard() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList, null);
        assertEquals(4, model.getMatch().getCardContainer().getCardContainer().get(CardLevel.One).get(CardColor.Emerald)
                .size());
        do {
            model.getMatch().getActionTokenContainer().shuffle();
            while (!(model.getMatch().getActionTokenContainer().getActionTokens()
                    .get(0) instanceof DiscardActionToken)) {
                model.getMatch().getActionTokenContainer().shuffle();
            }
        } while (!(((DiscardActionToken) model.getMatch().getActionTokenContainer().getActionTokens().get(0))
                .getCardColor() == CardColor.Emerald));
        model.getMatch().performExtractActionTokenMove();
        assertEquals(2, model.getMatch().getCardContainer().getCardContainer().get(CardLevel.One).get(CardColor.Emerald)
                .size());

        do {
            model.getMatch().getActionTokenContainer().shuffle();
            while (!(model.getMatch().getActionTokenContainer().getActionTokens()
                    .get(0) instanceof DiscardActionToken)) {
                model.getMatch().getActionTokenContainer().shuffle();
            }
        } while (!(((DiscardActionToken) model.getMatch().getActionTokenContainer().getActionTokens().get(0))
                .getCardColor() == CardColor.Emerald));
        model.getMatch().performExtractActionTokenMove();
        assertEquals(0, model.getMatch().getCardContainer().getCardContainer().get(CardLevel.One).get(CardColor.Emerald)
                .size());

        do {
            model.getMatch().getActionTokenContainer().shuffle();
            while (!(model.getMatch().getActionTokenContainer().getActionTokens()
                    .get(0) instanceof DiscardActionToken)) {
                model.getMatch().getActionTokenContainer().shuffle();
            }
        } while (!(((DiscardActionToken) model.getMatch().getActionTokenContainer().getActionTokens().get(0))
                .getCardColor() == CardColor.Emerald));
        model.getMatch().performExtractActionTokenMove();
        assertEquals(2, model.getMatch().getCardContainer().getCardContainer().get(CardLevel.Two).get(CardColor.Emerald)
                .size());

        do {
            model.getMatch().getActionTokenContainer().shuffle();
            while (!(model.getMatch().getActionTokenContainer().getActionTokens()
                    .get(0) instanceof DiscardActionToken)) {
                model.getMatch().getActionTokenContainer().shuffle();
            }
        } while (!(((DiscardActionToken) model.getMatch().getActionTokenContainer().getActionTokens().get(0))
                .getCardColor() == CardColor.Emerald));
        model.getMatch().performExtractActionTokenMove();
        assertEquals(0, model.getMatch().getCardContainer().getCardContainer().get(CardLevel.Two).get(CardColor.Emerald)
                .size());

        do {
            model.getMatch().getActionTokenContainer().shuffle();
            while (!(model.getMatch().getActionTokenContainer().getActionTokens()
                    .get(0) instanceof DiscardActionToken)) {
                model.getMatch().getActionTokenContainer().shuffle();
            }
        } while (!(((DiscardActionToken) model.getMatch().getActionTokenContainer().getActionTokens().get(0))
                .getCardColor() == CardColor.Emerald));
        model.getMatch().performExtractActionTokenMove();
        assertEquals(2, model.getMatch().getCardContainer().getCardContainer().get(CardLevel.Three)
                .get(CardColor.Emerald).size());

        do {
            model.getMatch().getActionTokenContainer().shuffle();
            while (!(model.getMatch().getActionTokenContainer().getActionTokens()
                    .get(0) instanceof DiscardActionToken)) {
                model.getMatch().getActionTokenContainer().shuffle();
            }
        } while (!(((DiscardActionToken) model.getMatch().getActionTokenContainer().getActionTokens().get(0))
                .getCardColor() == CardColor.Emerald));
        model.getMatch().performExtractActionTokenMove();
        assertEquals(0, model.getMatch().getCardContainer().getCardContainer().get(CardLevel.Three)
                .get(CardColor.Emerald).size());

        assertTrue(model.getMatch().getisOver());

    }

    @Test
    void moveAndShuffle() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList, null);
        while (!(model.getMatch().getActionTokenContainer().getActionTokens()
                .get(0) instanceof MoveAndShuffleActionToken)) {
            model.getMatch().getActionTokenContainer().shuffle();
        }
        model.getMatch().performExtractActionTokenMove();
        assertEquals(1, player.getBoard().getFaithMap().getBlackMarkerPosition());

    }
}