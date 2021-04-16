package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.*;

public class Production {
    private Map<ResourceType, Integer> requiredResources;
    private Map<ResourceType, Integer> manufacturedResources;

    public Map<ResourceType, Integer> getManufacturedResources() {
        return manufacturedResources;
    }

    public Map<ResourceType, Integer> getRequiredResources() {
        return requiredResources;
    }
}
