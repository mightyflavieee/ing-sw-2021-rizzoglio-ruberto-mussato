package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.CardContainer;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentCardTest {

    @Test
    void getRequiredResources() {
        Map<ResourceType, Integer> map, returnedMap;
        map = new HashMap<>();
        map.put(ResourceType.Coin, 2);
        DevelopmentCard developmentCard = new DevelopmentCard(CardColor.Gold, CardLevel.One, new Production(map, map),
                "prova", 3, map);
        returnedMap = developmentCard.getRequiredResources();
        assertNotNull(returnedMap);
        assertEquals(returnedMap.size(), map.size());
        assertEquals(returnedMap, map);
        assertEquals(3,developmentCard.getPoints());
        assertNotSame(map, returnedMap);

    }
    @Test
    void toStringTest(){
        CardContainer cardContainer = new CardContainer();
        assertFalse(cardContainer.getCardContainer().get(CardLevel.One).get(CardColor.Gold).get(0).toString().isEmpty());
        assertFalse(cardContainer.getCardContainer().get(CardLevel.One).get(CardColor.Gold).get(0).toString().isBlank());
    }

}