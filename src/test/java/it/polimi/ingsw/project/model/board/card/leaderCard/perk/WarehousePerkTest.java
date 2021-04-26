package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarehousePerkTest {

    @Test
    void usePerk() {
    }

    @Test
    void getResource() {
        Perk warehousePerk;
        Resource resource, returnedResource;
        resource = new Resource(ResourceType.Coin);
        warehousePerk = new WarehousePerk(resource, new Board());
        returnedResource = warehousePerk.getResource();
        assertTrue(returnedResource != resource);
        assertTrue(returnedResource.getType() == resource.getType());
    }
}