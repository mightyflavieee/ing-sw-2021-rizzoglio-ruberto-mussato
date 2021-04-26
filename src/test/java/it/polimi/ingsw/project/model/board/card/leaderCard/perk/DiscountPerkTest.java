package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountPerkTest {

    @Test
    void usePerk() {
    }

    @Test
    void getResource() {
        Perk discountPerk;
        Resource resource, returnedResource;
        resource = new Resource(ResourceType.Coin);
        discountPerk = new DiscountPerk(resource, new Board());
        returnedResource = discountPerk.getResource();
        assertNotSame(returnedResource, resource);
        assertSame(returnedResource.getType(), resource.getType());

    }
}