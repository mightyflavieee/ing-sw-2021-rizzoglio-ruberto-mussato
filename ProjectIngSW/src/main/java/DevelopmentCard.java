import java.util.*;

public class DevelopmentCard extends Card{
  private CardColor color;
  private CardLevel level;
  private Production production;
  private String id;

  public DevelopmentCard() {

  }

  public CardColor getColor() {
    return color;
  }

  public CardLevel getLevel() {
    return level;
  }

  public Production getProduction(){
    return production;
  }

  public String getId() {
    return id;
  }

  public List<Resource> useProduction(Warehouse warehouse) {
    Map<ResourceType, List<Resource>> requiredResources = this.production.getRequiredResources();
    List<ResourceType> resourceTypesRequired = new ArrayList<ResourceType>();
    Map<ShelfFloor, List<Resource>> shelfs = warehouse.getShelfs();
    List<Resource> firstFloor = shelfs.get(ShelfFloor.First);
    List<Resource> secondFloor = shelfs.get(ShelfFloor.Second);
    List<Resource> thirdFloor = shelfs.get(ShelfFloor.Third);
    List<Resource> manufacturedResources = new ArrayList<Resource>();

    // constructs a map of the resource types currently present in the warehouse and how many there are
    // QUA SOPRA NON VENGONO GESTITI GLI SLOT EXTRA DEL MAGAZZINO (acquisiti tramite LeaderCard)
    Map<ResourceType, Integer> currentResources = new HashMap<>();
    if (!firstFloor.isEmpty()) {
      currentResources.put(firstFloor.get(0).getType(), 1);
    }
    if (!secondFloor.isEmpty()) {
      currentResources.put(secondFloor.get(0).getType(), secondFloor.size());
    }
    if (!thirdFloor.isEmpty()) {
      currentResources.put(thirdFloor.get(0).getType(), thirdFloor.size());
    }

    // adds to a temporary list 'resourceTypesRequired' the resource types needed for the production
    if (requiredResources.get(ResourceType.Coin).size() > 0) {
      resourceTypesRequired.add(ResourceType.Coin);
    }
    if (requiredResources.get(ResourceType.Stone).size() > 0) {
      resourceTypesRequired.add(ResourceType.Stone);
    }
    if (requiredResources.get(ResourceType.Servant).size() > 0) {
      resourceTypesRequired.add(ResourceType.Servant);
    }
    if (requiredResources.get(ResourceType.Shield).size() > 0) {
      resourceTypesRequired.add(ResourceType.Shield);
    }

    // for each resource type needed for the production, verify if there is enough (or any) in the warehouse
    for (ResourceType type: resourceTypesRequired) {
      if (currentResources.get(type) == null || requiredResources.get(type).size() > currentResources.get(type) ) {
        System.out.println("Not enough resources");
        return manufacturedResources;
      }
    }

    manufacturedResources = this.production.getManufacturedResources();
    return manufacturedResources;
  }

}
