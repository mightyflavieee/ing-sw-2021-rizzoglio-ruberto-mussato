package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.card.developmentCard.Production;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionPerkTest {



    @Test
    void getResource() {
        Perk productionPerk;
        Resource resource, returnedResource;
        resource = new Resource(ResourceType.Coin);
        productionPerk = new ProductionPerk(resource, new Board());
        returnedResource = productionPerk.getResource();
        assertTrue(returnedResource != resource);
        assertTrue(returnedResource.getType() == resource.getType());
    }
}