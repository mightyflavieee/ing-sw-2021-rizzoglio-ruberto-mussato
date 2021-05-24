package it.polimi.ingsw.project.model.actionTokens;

import org.junit.jupiter.api.Test;

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
        assertFalse(newList.get(0) == oldList.get(0)
                    && newList.get(1) == oldList.get(1)
                    && newList.get(2) == oldList.get(2)
                    && newList.get(3) == oldList.get(3)
                    && newList.get(4) == oldList.get(4)
                    && newList.get(5) == oldList.get(5));

    }

}