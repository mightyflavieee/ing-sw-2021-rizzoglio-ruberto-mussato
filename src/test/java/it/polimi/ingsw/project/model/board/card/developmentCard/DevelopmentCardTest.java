package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardTest {

    @Test
    void getColor() {
    }

    @Test
    void getLevel() {
    }

    @Test
    void getProduction() {
    }

    @Test
    void getId() {
    }

    @Test
    void getRequiredResources() {
        Map<ResourceType, Integer> map, returnedMap;
        map = new HashMap<ResourceType, Integer>();
        map.put(ResourceType.Coin, 2);
        DevelopmentCard developmentCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, new Production(),
                "prova", map);
        returnedMap = developmentCard.getRequiredResources();
        assertTrue(returnedMap != null);
        assertTrue(returnedMap.size() == map.size());
        assertTrue(returnedMap.equals(map));
        assertFalse(map == returnedMap);

    }

}