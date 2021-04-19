package it.polimi.ingsw.project.model.board.card;

import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.observer.Observable;

import java.util.*;

public abstract class Card extends Observable<Perk> {
  private int victoryPoints;
  private List<Object> resourcesRequired;
  
  public int getPoints(){
    return victoryPoints;
  }

  public List<Object> getResourcesRequired(){
    return resourcesRequired;
  }
  public void notify(Perk perk){
    //è necessiario?
    super.notify(perk);
  }
}
