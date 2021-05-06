package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.card.Card;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import java.io.Serializable;
import java.util.*;

public class CardContainer implements Cloneable, Serializable {
  private Map<CardLevel,Map<CardColor,List<DevelopmentCard>>> cardContainer;

  public Map<CardLevel,Map<CardColor,List<DevelopmentCard>>> getCardContainer(){
    return cardContainer;
  }

  // DA MIGLIORARE CON JSON
  public CardContainer() {
    List<DevelopmentCard> developmentCardList = new ArrayList<>();
    Map<CardColor,List<DevelopmentCard>> firstLevelMap = new HashMap<>();
    Map<CardColor,List<DevelopmentCard>> secondLevelMap = new HashMap<>();
    Map<CardColor,List<DevelopmentCard>> thirdLevelMap = new HashMap<>();
    firstLevelMap.put(CardColor.Gold, developmentCardList);
    firstLevelMap.put(CardColor.Sapphire, developmentCardList);
    firstLevelMap.put(CardColor.Emerald, developmentCardList);
    firstLevelMap.put(CardColor.Amethyst, developmentCardList);
    secondLevelMap.put(CardColor.Gold, developmentCardList);
    secondLevelMap.put(CardColor.Sapphire, developmentCardList);
    secondLevelMap.put(CardColor.Emerald, developmentCardList);
    secondLevelMap.put(CardColor.Amethyst, developmentCardList);
    thirdLevelMap.put(CardColor.Gold, developmentCardList);
    thirdLevelMap.put(CardColor.Sapphire, developmentCardList);
    thirdLevelMap.put(CardColor.Emerald, developmentCardList);
    thirdLevelMap.put(CardColor.Amethyst, developmentCardList);
    Map<CardLevel,Map<CardColor,List<DevelopmentCard>>> container = new HashMap<>();
    container.put(CardLevel.One, firstLevelMap);
    container.put(CardLevel.Two, secondLevelMap);
    container.put(CardLevel.Three, thirdLevelMap);
    this.cardContainer = container;
  }

  public void addCardToContainer(CardLevel cardLevel, CardColor cardColor, DevelopmentCard developmentCard) {
    this.cardContainer.get(cardLevel).get(cardColor).add(developmentCard);
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