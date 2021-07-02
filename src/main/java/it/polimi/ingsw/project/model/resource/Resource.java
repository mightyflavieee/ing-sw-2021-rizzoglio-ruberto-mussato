package it.polimi.ingsw.project.model.resource;

import java.io.Serializable;

public class Resource implements Serializable {
    private static final long serialVersionUID = 38402805923333334L;
    private final ResourceType type;

    /**
     * it construct the resource
     * @param type it is the type of the resource
     */
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
