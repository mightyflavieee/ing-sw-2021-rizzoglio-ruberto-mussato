package it.polimi.ingsw.project.model.resource;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    @Test
    void getType() {
        ResourceType resourceType = ResourceType.Coin;
        Resource resource = new Resource(ResourceType.Coin);
        assertEquals(resourceType, resource.getType());
    }
    @Test
    void testToString(){
        Resource resource = new Resource(ResourceType.Shield);
        assertEquals(ResourceType.Shield.toString(),resource.toString());
    }
}