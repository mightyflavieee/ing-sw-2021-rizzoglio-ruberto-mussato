public class LeaderCard {
  private Perk perk;
  private Status status;

  public Perk getPerk(){
    return perk;
  }

  public Status getStatus(){
    return status;
  }

  public void activateCard(){
    status = Status.Active;
  }

  public void discard(){}

  public void attach(Observer observer){}

  public void detach(Observer observer){}
}

