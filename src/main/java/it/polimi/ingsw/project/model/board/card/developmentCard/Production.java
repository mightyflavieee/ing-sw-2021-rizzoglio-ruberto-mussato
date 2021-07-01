package it.polimi.ingsw.project.model.board.card.developmentCard;

import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.Serializable;
import java.util.*;

public class Production implements Serializable{
    private static final long serialVersionUID = 3843338592475092704L;
    private final Map<ResourceType, Integer> requiredResources;
    private final Map<ResourceType, Integer> manufacturedResources;

    /**
     * @param requiredResources it is the map of the resources needed for the production
     * @param manufacturedResources it is the map of the resources gained after this production
     */
    public Production(Map<ResourceType, Integer> requiredResources, Map<ResourceType, Integer> manufacturedResources) {
        this.requiredResources = requiredResources;
        this.manufacturedResources = manufacturedResources;
    }

    public Map<ResourceType, Integer> getManufacturedResources() {
        return new HashMap<>(this.manufacturedResources);
    }

    public Map<ResourceType, Integer> getRequiredResources() {
        return new HashMap<>(this.requiredResources);
    }

    /**
     * @return returns the formatted string for the cli
     */
    public String toString() {
        StringBuilder converted;
        converted = new StringBuilder("Required resources:\n");
        for (ResourceType type : this.requiredResources.keySet()) {
            converted.append("\t").append(type).append(" = ").append(this.requiredResources.get(type)).append("\n");
        }
        converted.append("Manufactured resources:\n");
        for (ResourceType type : this.manufacturedResources.keySet()) {
            converted.append("\t").append(type).append(" = ").append(this.manufacturedResources.get(type)).append("\n");
        }
        return converted.toString();
    }
}
