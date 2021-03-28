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

  public void activateCard(){
    if (this.status == Status.Active) {
      System.out.println("Leader Card is already active!");
    } else {
      this.status = Status.Active;
      this.perk.usePerk();
    }
  }

  public void discard(){}

  public void attach(Observer observer) {
    this.observers.add(observer);
  }

  public void detach(Observer observer){
    this.observers.remove(observer);
  }
}

