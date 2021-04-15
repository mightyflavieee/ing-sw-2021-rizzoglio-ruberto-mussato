package it.polimi.ingsw.project.model;

import java.util.List;

import it.polimi.ingsw.project.observer.custom.LeaderCardObserver;

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

  // changes the status of the it.polimi.ingsw.project.model.LeaderCard to Active
  public void activateCard() {
    if (this.status == Status.Active) {
      // if the perk is reusable (only it.polimi.ingsw.project.model.ProductionPerk), utilize the perk again
      if (this.perk instanceof ProductionPerk) {
        this.perk.usePerk(this.perk.resource);
      } else {
        // if the perk is not a it.polimi.ingsw.project.model.ProductionPerk and it's active, it is NOT reusable
        System.out.println("Leader it.polimi.ingsw.project.model.Card is already active!");
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
    converted = "it.polimi.ingsw.project.model.Perk: " + this.perk.toString() + "\n" + "it.polimi.ingsw.project.model.Status: " + this.status.toString() + "\n" + "Id: " + this.id + "\n";
    return converted;
  }

  public void attach(LeaderCardObserver observer) {
    this.observers.add(observer);
  }

  public void detach(LeaderCardObserver observer) {
    this.observers.remove(observer);
  }
}
