package it.polimi.ingsw.project.model.board.card;

import java.io.Serializable;

/**
 * Abstract class for the cards
 */
public abstract class Card implements Serializable {

  private static final long serialVersionUID = 3840281192471192711L;
  private final int victoryPoints;

  
  public int getPoints(){
    return victoryPoints;
  }

  /**
   * @param victoryPoints are the points for the card when you buy it or activate it
   */
  public Card(int victoryPoints) {
    this.victoryPoints = victoryPoints;
  }

}
