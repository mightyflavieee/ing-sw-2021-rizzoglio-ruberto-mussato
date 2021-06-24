package it.polimi.ingsw.project.model.resource;

import java.io.Serializable;

public class Resource implements Serializable { //TODO serve davvero o basta resourceType?
    private static final long serialVersionUID = 38402805923333334L;
    private final ResourceType type;

    public Resource(ResourceType type) {
        this.type = type;
    }

    public ResourceType getType() {
        return type;
    }
    public String toString(){
        return this.type.toString();
    }
}
