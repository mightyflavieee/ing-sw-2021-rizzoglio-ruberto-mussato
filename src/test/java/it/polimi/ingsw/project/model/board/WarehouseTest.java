package it.polimi.ingsw.project.model.board;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    @Test
    void mapAllContainedResources() {
    }

    @Test
    void listToMapResources() {
        Warehouse warehouse = new Warehouse();
        List<Resource> resources = new ArrayList<>();
        Map<ResourceType, Integer> returnedResources;
        resources.add(new Resource(ResourceType.Coin));
        resources.add(new Resource(ResourceType.Servant));
        resources.add(new Resource(ResourceType.Shield));
        resources.add(new Resource(ResourceType.Coin));
        returnedResources = warehouse.listToMapResources(resources);
        assertNotNull(returnedResources);
        assertEquals(2, (int) returnedResources.get(ResourceType.Coin));
        assertEquals(1, (int) returnedResources.get(ResourceType.Servant));
        assertEquals(1, (int) returnedResources.get(ResourceType.Shield));
      //  assertTrue(returnedResources.get(ResourceType.Faith)==0);
      //  assertTrue(returnedResources.get(ResourceType.Stone)==0);
    }

    @Test
    void getShelfs() {
    }

    @Test
    void getNumResourcesToDiscard() {
    }


    @Test
    void swapShelves() {
    }

    @Test
    void getExtraDeposit() {
    }

    @Test
    void createExtraDeposit() {
    }

    @Test
    void eliminateResourceForProductionPerk() {
    }

    @Test
    void discardResourcesInHand() {
    }

    @Test
    void eliminateResources() {
    }
}