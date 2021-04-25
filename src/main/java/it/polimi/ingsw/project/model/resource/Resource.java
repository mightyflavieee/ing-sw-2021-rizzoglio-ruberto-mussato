package it.polimi.ingsw.project.model.resource;

import java.io.Serializable;

public class Resource implements Serializable {
    private ResourceType type;

    public Resource(ResourceType type) {
        this.type = type;
    }

    public ResourceType getType() {
        return type;
    }
}
