package it.polimi.ingsw.project.model.board.card;

import java.io.Serializable;

public abstract class Card implements Serializable {
  private final int victoryPoints;

  
  public int getPoints(){
    return victoryPoints;
  }

  public Card(int victoryPoints) {
    this.victoryPoints = victoryPoints;
  }

}
