package it.polimi.ingsw.project.model.resource;

public class Resource {
    private ResourceType type;

    public Resource(ResourceType type) {
        this.type = type;
    }

    public ResourceType getType() {
        return type;
    }
}
