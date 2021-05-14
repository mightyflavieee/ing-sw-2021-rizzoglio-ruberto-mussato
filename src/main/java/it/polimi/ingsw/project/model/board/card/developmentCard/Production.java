package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.Serializable;
import java.util.*;

public class Production implements Serializable{
    private Map<ResourceType, Integer> requiredResources;
    private Map<ResourceType, Integer> manufacturedResources;

    public Production(Map<ResourceType, Integer> requiredResources, Map<ResourceType, Integer> manufacturedResources) {
        this.requiredResources = requiredResources;
        this.manufacturedResources = manufacturedResources;
    }

    public Map<ResourceType, Integer> getManufacturedResources() {
        Map<ResourceType, Integer> mapToReturn = new HashMap<>();
        mapToReturn.putAll(this.manufacturedResources);
        return mapToReturn;
    }

    public Map<ResourceType, Integer> getRequiredResources() {
        Map<ResourceType, Integer> mapToReturn = new HashMap<>();
        mapToReturn.putAll(this.requiredResources);
        return mapToReturn;
    }
}
