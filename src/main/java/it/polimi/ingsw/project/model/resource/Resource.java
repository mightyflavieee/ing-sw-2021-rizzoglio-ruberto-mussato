package it.polimi.ingsw.project.model.resource;

import java.io.Serializable;

public class Resource implements Serializable { //TODO serve davvero o basta resourceType?
    private ResourceType type;

    public Resource(ResourceType type) {
        this.type = type;
    }

    public ResourceType getType() {
        return type;
    }
}
