package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.Serializable;
import java.util.*;

public class CardContainer implements Cloneable, Serializable {
  private Map<CardLevel,Map<CardColor,List<DevelopmentCard>>> cardContainer;
  //TODO costruttore
  public Map<CardLevel,Map<CardColor,List<DevelopmentCard>>> getCardContainer(){
    return cardContainer;
  }


  private boolean tryToDiscard(CardColor cardColor, CardLevel cardLevel){
    if(cardContainer.get(cardLevel).get(cardColor).isEmpty()){
      return false;
    }
    else{
      cardContainer.get(cardLevel).get(cardColor).remove(0);
    }
  return true;
  }

  public boolean discard(CardColor cardColor){
    for(int i = 0; i < 2; i++)
      if (!tryToDiscard(cardColor, CardLevel.One))
        if (!tryToDiscard(cardColor, CardLevel.Two))
          if (!tryToDiscard(cardColor, CardLevel.Three)){
            return true; // if you finish the cards you loose
          }
    return false; 
  }

  public boolean isCardPresent(String devCardID) {
    for (CardLevel level : this.cardContainer.keySet()) {
      for (CardColor color : this.cardContainer.get(level).keySet()) {
        for (DevelopmentCard card : this.cardContainer.get(level).get(color)) {
          if (card.getId().equals(devCardID)) { return true; }
        }
      }
    }
    return false;
  }

  public DevelopmentCard fetchCard(String devCardID) {
    for (CardLevel level : this.cardContainer.keySet()) {
      for (CardColor color : this.cardContainer.get(level).keySet()) {
        for (DevelopmentCard card : this.cardContainer.get(level).get(color)) {
          if (card.getId().equals(devCardID)) {
            return card;
          }
        }
      }
    }
    return null;
  }

  public DevelopmentCard removeBoughtCard(String devCardID) {
    DevelopmentCard boughtCard = fetchCard(devCardID);
    for (CardLevel level : this.cardContainer.keySet()) {
      for (CardColor color : this.cardContainer.get(level).keySet()) {
        for (DevelopmentCard card : this.cardContainer.get(level).get(color)) {
          if (card.getId().equals(devCardID)) {
            this.cardContainer.get(level).get(color).remove(boughtCard);
          }
        }
      }
    }
    return boughtCard;
  }

}