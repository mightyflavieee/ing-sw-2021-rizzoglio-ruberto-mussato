package it.polimi.ingsw.project.model.board.card;

import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.observer.Observable;

import java.io.Serializable;
import java.util.*;

public abstract class Card implements Serializable {
  private int victoryPoints;

  
  public int getPoints(){
    return victoryPoints;
  }

  public Card(int victoryPoints) {
    this.victoryPoints = victoryPoints;
  }

}