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
        Warehouse warehouse = new Warehouse(null);
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        List<Resource> resourcesListSecondFloor = new ArrayList<>();
        List<Resource> resourcesListThirdFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        Resource resource1 = new Resource(ResourceType.Stone);
        Resource resource2 = new Resource(ResourceType.Stone);
        Resource resource3 = new Resource(ResourceType.Servant);
        Resource resource4 = new Resource(ResourceType.Servant);
        Resource resource5 = new Resource(ResourceType.Servant);
        resourcesListFirstFloor.add(resource0);
        resourcesListSecondFloor.add(resource1);
        resourcesListSecondFloor.add(resource2);
        resourcesListThirdFloor.add(resource3);
        resourcesListThirdFloor.add(resource4);
        resourcesListThirdFloor.add(resource5);
        warehouse.getShelves().put(ShelfFloor.First, resourcesListFirstFloor);
        warehouse.getShelves().put(ShelfFloor.Second, resourcesListSecondFloor);
        warehouse.getShelves().put(ShelfFloor.Third, resourcesListThirdFloor);
        Map<ResourceType, Integer> allResources = warehouse.mapAllContainedResources();
        for (ResourceType resourceType : allResources.keySet()) {
            Integer num = allResources.get(resourceType);
            assertNotNull(num);
        }
    }

    @Test
    void listToMapResources() {
        Warehouse warehouse = new Warehouse(null);
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
    }
    @Test
    void swapShelves(){
        Warehouse warehouse = new Warehouse(null);
        List<Resource> resourcesListFirstFloor = new ArrayList<>();
        List<Resource> resourcesListSecondFloor = new ArrayList<>();
        List<Resource> resourcesListThirdFloor = new ArrayList<>();
        Resource resource0 = new Resource(ResourceType.Coin);
        Resource resource1 = new Resource(ResourceType.Stone);
        Resource resource3 = new Resource(ResourceType.Servant);
        Resource resource4 = new Resource(ResourceType.Servant);
        Resource resource5 = new Resource(ResourceType.Servant);
        resourcesListFirstFloor.add(resource0);
        resourcesListSecondFloor.add(resource1);
        resourcesListThirdFloor.add(resource3);
        resourcesListThirdFloor.add(resource4);
        resourcesListThirdFloor.add(resource5);
        warehouse.getShelves().put(ShelfFloor.First, resourcesListFirstFloor);
        warehouse.getShelves().put(ShelfFloor.Second, resourcesListSecondFloor);
        warehouse.getShelves().put(ShelfFloor.Third, resourcesListThirdFloor);
        warehouse.swapShelves(ShelfFloor.First,ShelfFloor.Second);
        assertEquals(ResourceType.Coin,warehouse.getShelves().get(ShelfFloor.Second).get(0).getType());
        assertEquals(ResourceType.Stone,warehouse.getShelves().get(ShelfFloor.First).get(0).getType());
        assertEquals(ResourceType.Servant,warehouse.getShelves().get(ShelfFloor.Third).get(0).getType());

    }
    @Test
    void testtoString(){
        Warehouse warehouse = new Warehouse(null);
        String string = warehouse.toString();
       // assertFalse(string.isBlank());
        assertFalse(string.isEmpty());

    }
    @Test
    void insertFalse() {
        Warehouse warehouse = new Warehouse(null);
        List<Resource> resourceList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            resourceList.add(new Resource(ResourceType.Servant));
        }
        assertFalse(warehouse.insertInExtraDeposit(resourceList));
        assertFalse(warehouse.insertInShelves(ShelfFloor.Second,resourceList));
    }
}