import java.util.List;

public class Production {
    private List<Resource> requiredResources;
    private List<Resource> manufacturedResources;

    public List<Resource> getManufacturedResources() {
        return manufacturedResources;
    }

    public List<Resource> getRequiredResources() {
        return requiredResources;
    }
}
