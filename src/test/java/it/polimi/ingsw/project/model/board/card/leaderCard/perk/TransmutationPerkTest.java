package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransmutationPerkTest {

    @Test
    void usePerk() {
    }

    @Test
    void getResource() {
        Perk transmutationPerk;
        Resource resource, returnedResource;
        resource = new Resource(ResourceType.Coin);
        transmutationPerk = new TransmutationPerk(resource, new Board());
        returnedResource = transmutationPerk.getResource();
        assertTrue(returnedResource != resource);
        assertTrue(returnedResource.getType() == resource.getType());
    }
}