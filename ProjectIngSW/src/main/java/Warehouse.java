import java.util.*;

public class Warehouse {
  private Map<ShelfFloor, List<Resource>> shelfs;
  private List<Resource> extraDeposit; // ATTENZIONE: per ora tolto l'Optional finché non capiamo come fare
  private List<Observer> observers;

  public Warehouse() {
  }

  public Map<ResourceType, Integer> mapAllResources(){
    Map<ResourceType, Integer> currentResourcesMap = new HashMap<>();
 
    return null;
  }

  public Map<ShelfFloor, List<Resource>> getShelfs() {
    return shelfs;
  }

  public void insertResources(List<Resource> resourcesToBeInserted){}

  public void swapShelf(ShelfFloor swapper, ShelfFloor swappee){}

  public void clearShelf(ShelfFloor floor){
    
  }

  public List<Resource> getExtraDeposit(){
    return extraDeposit;
  }

  // adds resources to the extra deposit
  public void addExtraDeposit(Resource resource) {
    if (this.extraDeposit.size() < 2) {
      this.extraDeposit.add(resource);
    } else {
      System.out.println("Extra Deposit is already full!");
    }
  }

  public List<Observer> getObservers(){
    return observers;
  }

  public void attach(Observer observer){}

  public void detach(Observer observer){}
}
