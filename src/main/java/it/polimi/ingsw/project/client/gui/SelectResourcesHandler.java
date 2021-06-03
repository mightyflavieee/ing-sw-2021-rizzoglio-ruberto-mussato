package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.HashMap;
import java.util.Map;

public class SelectResourcesHandler {
    private Map<ResourceType, Integer> resourcesFromWarehouse;
    private Map<ResourceType, Integer> resourcesFromChest;

    public SelectResourcesHandler() {
        this.resourcesFromWarehouse = new HashMap<>();
        this.resourcesFromChest = new HashMap<>();
    }

    public Map<ResourceType, Integer> getResourcesFromWarehouse() {
        return resourcesFromWarehouse;
    }

    public Map<ResourceType, Integer> getResourcesFromChest() {
        return resourcesFromChest;
    }

    public void incrementResourcesFromWarehouse(ResourceType resourceType) {
        if (this.resourcesFromWarehouse.containsKey(resourceType)) {
            this.resourcesFromWarehouse.put(resourceType, this.resourcesFromWarehouse.get(resourceType) + 1);
        } else {
            this.resourcesFromWarehouse.put(resourceType, 1);
        }
    }

    public void incrementResourcesFromChest(ResourceType resourceType) {
        if (this.resourcesFromChest.containsKey(resourceType)) {
            this.resourcesFromChest.put(resourceType, this.resourcesFromChest.get(resourceType) + 1);
        } else {
            this.resourcesFromChest.put(resourceType, 1);
        }
    }
}
