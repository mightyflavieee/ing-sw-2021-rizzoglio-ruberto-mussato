package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import java.io.Serializable;
import java.util.*;

public class CardContainer implements Cloneable, Serializable {
  private final LinkedHashMap<CardLevel, LinkedHashMap<CardColor, List<DevelopmentCard>>> cardContainer;

  public LinkedHashMap<CardLevel, LinkedHashMap<CardColor, List<DevelopmentCard>>> getCardContainer() {
    return cardContainer;
  }

  // DA MIGLIORARE CON JSON
  public CardContainer() {

    // Convert JSON File to Java Object
    CardContainerBuilder cardContainerBuilder = new CardContainerBuilder("");
    List<DevelopmentCard> allCards = cardContainerBuilder.allCards;
    Collections.shuffle(allCards);
    LinkedHashMap<CardLevel, LinkedHashMap<CardColor, List<DevelopmentCard>>> tempCardContainer = initLinkedHashMapCards();
    for (DevelopmentCard developmentCard : allCards) {
      tempCardContainer.get(developmentCard.getLevel()).get(developmentCard.getColor()).add(developmentCard);
    }
    this.cardContainer = tempCardContainer;

  }

  private LinkedHashMap<CardLevel, LinkedHashMap<CardColor, List<DevelopmentCard>>> initLinkedHashMapCards() {
    LinkedHashMap<CardLevel, LinkedHashMap<CardColor, List<DevelopmentCard>>> tempCardContainer = new LinkedHashMap<>();
    for (CardLevel cardLevel : CardLevel.values()) {
      final LinkedHashMap<CardColor, List<DevelopmentCard>> LinkedHashMapColorCard = new LinkedHashMap<>();
      for (CardColor cardColor : CardColor.values()) {
        LinkedHashMapColorCard.put(cardColor, new ArrayList<>());
      }
      tempCardContainer.put(cardLevel, LinkedHashMapColorCard);
    }
    return tempCardContainer;
  }

  public void addCardToContainer(DevelopmentCard developmentCard) {
    this.cardContainer.get(developmentCard.getLevel()).get(developmentCard.getColor()).add(developmentCard);
  }

  private boolean tryToDiscard(CardColor cardColor, CardLevel cardLevel) {
    if (cardContainer.get(cardLevel).get(cardColor).isEmpty()) {
      return true;
    } else {
      cardContainer.get(cardLevel).get(cardColor).remove(0);
    }
    return cardContainer.get(CardLevel.Three).get(cardColor).isEmpty();
  }

  public boolean discard(CardColor cardColor) {
    for (int i = 0; i < 2; i++)
      if (tryToDiscard(cardColor, CardLevel.One))
        if (tryToDiscard(cardColor, CardLevel.Two))
          if (tryToDiscard(cardColor, CardLevel.Three)) {
            return true; // if you finish the cards you loose
          }
    return false;
  }

  public boolean isCardPresent(String devCardID) {
    for (CardLevel level : this.cardContainer.keySet()) {
      for (CardColor color : this.cardContainer.get(level).keySet()) {
        for (DevelopmentCard card : this.cardContainer.get(level).get(color)) {
          if (card.getId().equals(devCardID)) {
            return true;
          }
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
            break;
          }
        }
      }
    }
    return boughtCard;
  }

  public List<DevelopmentCard> getAvailableDevCards() {
    List<DevelopmentCard> availableDevCards = new ArrayList<>();
    for (CardLevel level : this.cardContainer.keySet()) {
      for (CardColor color : this.cardContainer.get(level).keySet()) {
        if (!this.cardContainer.get(level).get(color).isEmpty()) {
          availableDevCards.add(this.cardContainer.get(level).get(color).get(this.cardContainer.get(level).get(color).size()-1));
        }
      }
    }
    return availableDevCards;
  }
}