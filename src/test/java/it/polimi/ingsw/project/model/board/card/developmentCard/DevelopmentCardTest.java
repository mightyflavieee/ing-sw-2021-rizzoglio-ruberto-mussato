package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.CardContainer;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

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
    void extractJson() {
        Gson gson = new Gson();

        try (Reader reader = new FileReader("src/main/resources/developmentCards.json")) {

            // Convert JSON File to Java Object
            CardContainer cardContainer = gson.fromJson(reader, CardContainer.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void buildJson() {
        Map<ResourceType, Integer> requiredResources = new HashMap<>();
        requiredResources.put(ResourceType.Stone, 1);
        Map<ResourceType, Integer> manufacturedResources = new HashMap<>();
        manufacturedResources.put(ResourceType.Faith, 1);
        Map<ResourceType, Integer> costResources = new HashMap<>();
        costResources.put(ResourceType.Coin, 2);
        Production demoProduction = new Production(requiredResources, manufacturedResources);
        DevelopmentCard devCard = new DevelopmentCard(CardColor.Amethyst, CardLevel.One, demoProduction, "id", 1,
                costResources);
        devCard.toJson();
    }

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
        assertNotSame(map, returnedMap);

    }

}