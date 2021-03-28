import java.util.*;

public class Warehouse {
  private Map<ShelfFloor, List<Resource>> shelfs;
  private Optional<Map<ExtraResourceType, List<Resource>>> extraDeposit;
  private List<Observer> observers;
  
  public Map<ResourceType, Integer> mapAllResources(){
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<ResourceType,Integer>();
 
    return null;
  }

  public Map<ShelfFloor, List<Resource>> getShelfs() {
    return shelfs;
  }
  public void insertResources(List<Resource> resourcesToBeInserted){}

  public void swapShelf(ShelfFloor swapper, ShelfFloor swappee){}

  public void clearShelf(ShelfFloor floor){
    
  }

  public Optional<Map<ExtraResourceType, List<Resource>>> getExtraDeposit(){
    return extraDeposit;
  }

  public List<Observer> getObservers(){
    return observers;
  }
  public void attach(Observer observer){}
  public void detach(Observer observer){}
}
