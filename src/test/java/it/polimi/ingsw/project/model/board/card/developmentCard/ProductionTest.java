package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProductionTest {

    @Test
    void getManufacturedResources() {
        Map<ResourceType, Integer> manufacturedResources, requiredResource,  returnedManufacturedResources;
        manufacturedResources = new HashMap<>();
        manufacturedResources.put(ResourceType.Coin, 2);
        requiredResource = new HashMap<>();
        requiredResource.put(ResourceType.Servant, 3);
        Production production = new Production(requiredResource, manufacturedResources);
        returnedManufacturedResources = production.getManufacturedResources();
        assertNotNull(returnedManufacturedResources);
        assertEquals(returnedManufacturedResources.size(), manufacturedResources.size());
        assertEquals(returnedManufacturedResources, manufacturedResources);
        assertNotSame(manufacturedResources, returnedManufacturedResources);

    }

    @Test
    void getRequiredResources() {
        Map<ResourceType, Integer> manufacturedResources, requiredResource,  returnedRequiredResources;
        manufacturedResources = new HashMap<>();
        manufacturedResources.put(ResourceType.Coin, 2);
        requiredResource = new HashMap<>();
        requiredResource.put(ResourceType.Servant, 3);
        Production production = new Production(requiredResource, manufacturedResources);
        returnedRequiredResources = production.getRequiredResources();
        assertNotNull(returnedRequiredResources);
        assertEquals(returnedRequiredResources.size(), requiredResource.size());
        assertEquals(returnedRequiredResources, requiredResource);
        assertNotSame(requiredResource, returnedRequiredResources);
    }
}