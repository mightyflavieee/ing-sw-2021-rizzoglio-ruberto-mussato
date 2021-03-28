import java.util.*;

public class Production {
    private Map<ResourceType, List<Resource>> requiredResources;
    private List<Resource> manufacturedResources;

    public List<Resource> getManufacturedResources() {
        return manufacturedResources;
    }

    public Map<ResourceType, List<Resource>> getRequiredResources() {
        return requiredResources;
    }
}
