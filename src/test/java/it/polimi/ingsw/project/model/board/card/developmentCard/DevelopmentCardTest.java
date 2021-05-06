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
        map = new HashMap<>();
        map.put(ResourceType.Coin, 2);
        DevelopmentCard developmentCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, new Production(map,map),
                "prova", 3);
        returnedMap = developmentCard.getRequiredResources();
        assertNotNull(returnedMap);
        assertEquals(returnedMap.size(), map.size());
        assertEquals(returnedMap, map);
        assertNotSame(map, returnedMap);

    }


}