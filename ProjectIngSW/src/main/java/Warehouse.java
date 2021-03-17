import java.util.*;

public class Warehouse {
  private Map<ShelfFloor, List<Resource>> shelfs;
  private Optional<Map<ExtraResourceType, List<Resource>>> extraDeposit;
  private List<Observer> observers;

  public Map<ShelfFloor, List<Resource>> getShelfs() {
    return null;
  }
  public void insertResources(ShelfFloor shelf, List<Resource> resourcesToBeInserted){}

  public void swapShelf(ShelfFloor swapper, ShelfFloor swappee){}

  public void clearShelf(ShelfFloor floor){
    
  }
  public void attach(Observer observer){}
  public void detach(Observer observer){}
}
