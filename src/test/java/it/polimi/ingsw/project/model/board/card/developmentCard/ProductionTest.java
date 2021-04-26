package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductionTest {

    @Test
    void getManufacturedResources() {
        Map<ResourceType, Integer> manufacturedResources, requiredResource,  returnedManufacturedResources;
        manufacturedResources = new HashMap<ResourceType, Integer>();
        manufacturedResources.put(ResourceType.Coin, 2);
        requiredResource = new HashMap<ResourceType, Integer>();
        requiredResource.put(ResourceType.Servant, 3);
        Production production = new Production(requiredResource, manufacturedResources);
        returnedManufacturedResources = production.getManufacturedResources();
        assertTrue(returnedManufacturedResources != null);
        assertTrue(returnedManufacturedResources.size() == manufacturedResources.size());
        assertTrue(returnedManufacturedResources.equals(manufacturedResources));
        assertFalse(manufacturedResources == returnedManufacturedResources);

    }

    @Test
    void getRequiredResources() {
        Map<ResourceType, Integer> manufacturedResources, requiredResource,  returnedRequiredResources;
        manufacturedResources = new HashMap<ResourceType, Integer>();
        manufacturedResources.put(ResourceType.Coin, 2);
        requiredResource = new HashMap<ResourceType, Integer>();
        requiredResource.put(ResourceType.Servant, 3);
        Production production = new Production(requiredResource, manufacturedResources);
        returnedRequiredResources = production.getRequiredResources();
        assertTrue(returnedRequiredResources != null);
        assertTrue(returnedRequiredResources.size() == requiredResource.size());
        assertTrue(returnedRequiredResources.equals(requiredResource));
        assertFalse(requiredResource == returnedRequiredResources);
    }
}