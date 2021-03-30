import java.util.List;

public class LeaderCard extends Card{
  final private Perk perk;
  private Status status;
  private List<Observer> observers = null;

  public LeaderCard(Perk assignedPerk) {
    this.perk = assignedPerk;
    this.status = Status.Inactive;
  }

  public Perk getPerk(){
    return perk;
  }

  public Status getStatus(){
    return status;
  }

  // changes the status of the LeaderCard to Active
  public void activateCard(){
    if (this.status == Status.Active) {
      // if the perk is a WarehousePerk and it's active, it is NOT reusable
      if (this.perk instanceof WarehousePerk) {
        System.out.println("Leader Card is already active!");
      } else {
        // if the perk is reusable, utilize the perk
        this.perk.usePerk();
      }
    } else {
      this.status = Status.Active;
      this.perk.usePerk();
    }
  }

  /*public void discard() {

  }*/

  public void attach(Observer observer) {
    this.observers.add(observer);
  }

  public void detach(Observer observer){
    this.observers.remove(observer);
  }
}

