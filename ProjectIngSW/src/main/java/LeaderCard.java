import java.util.List;

public class LeaderCard extends Card {
  final private Perk perk;
  private Status status;
  private List<LeaderCardObserver> observers = null;
  private String id;

  public LeaderCard(String id, Perk assignedPerk) {
    this.id = id;
    this.perk = assignedPerk;
    this.status = Status.Inactive;
  }

  public Perk getPerk() {
    return perk;
  }

  public Status getStatus() {
    return status;
  }

  public String getId() {
    return id;
  }

  // changes the status of the LeaderCard to Active
  public void activateCard() {
    if (this.status == Status.Active) {
      // if the perk is reusable (only ProductionPerk), utilize the perk again
      if (this.perk instanceof ProductionPerk) {
        this.perk.usePerk(this.perk.resource);
      } else {
        // if the perk is not a ProductionPerk and it's active, it is NOT reusable
        System.out.println("Leader Card is already active!");
      }
    } else {
      this.status = Status.Active;
      this.perk.usePerk(this.perk.resource);
      for (LeaderCardObserver observer: observers) {
        observer.update(this.perk);
      }
    }
  }

  public String toString() {
    String converted;
    converted = "Perk: " + this.perk.toString() + "\n" + "Status: " + this.status.toString() + "\n" + "Id: " + this.id + "\n";
    return converted;
  }

  public void attach(LeaderCardObserver observer) {
    this.observers.add(observer);
  }

  public void detach(LeaderCardObserver observer) {
    this.observers.remove(observer);
  }
}
