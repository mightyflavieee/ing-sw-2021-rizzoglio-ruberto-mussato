package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import java.io.Serializable;
import java.util.*;

public class CardContainer implements Cloneable, Serializable {
  private static final long serialVersionUID = 3440280592475092704L;
  private final LinkedHashMap<CardLevel, LinkedHashMap<CardColor, List<DevelopmentCard>>> cardContainer;

  public LinkedHashMap<CardLevel, LinkedHashMap<CardColor, List<DevelopmentCard>>> getCardContainer() {
    return cardContainer;
  }

  /**
   * it construct the cardContainer from the JSON file
   */
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

  /**
   * @return it removes the entire tray for the development Cards
   */
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

  /**
   * it adds a card to the card container
   * @param developmentCard card to add to the container (it is used in the tests)
   */
  public void addCardToContainer(DevelopmentCard developmentCard) {
    this.cardContainer.get(developmentCard.getLevel()).get(developmentCard.getColor()).add(developmentCard);
  }

  /**
   * it tries to discard a card from the cardContainer
   * @param cardColor color of the card you are trying to discard
   * @param cardLevel level of the card you are trying to discard
   * @return it returns true if the card and level you are trying to discard is empty, false it discard and then check the same thing
   */
  private boolean tryToDiscard(CardColor cardColor, CardLevel cardLevel) {
    if (cardContainer.get(cardLevel).get(cardColor).isEmpty()) {
      return true;
    } else {
      cardContainer.get(cardLevel).get(cardColor).remove(0);
    }
    return cardContainer.get(CardLevel.Three).get(cardColor).isEmpty();
  }

  /**
   * it discard the cards for the single player game
   * @param cardColor color selected to be discarded
   * @return it returns true if you have finished the card of a specific color, false if not
   */
  public boolean discard(CardColor cardColor) {
    for (int i = 0; i < 2; i++)
      if (tryToDiscard(cardColor, CardLevel.One))
        if (tryToDiscard(cardColor, CardLevel.Two))
          if (tryToDiscard(cardColor, CardLevel.Three)) {
            return true; // if you finish the cards you loose
          }
    return false;
  }

  /**
   * checks if the card of that specific id is present
   * @param devCardID id of the selected card to check
   * @return true if the card is present, false if not
   */
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

  /**
   * it returns the card from the cardContainer
   * @param devCardID id of the selected Card
   * @return it returns the selected card, null if it is not present
   */
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

  /**
   * it removes the wanted card after it has been selected
   * @param devCardID selected card id
   * @return it removes the remove developmentCard
   */
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

  /**
   * used when we want to visualize the status of the card container
   * @return it returns all available cards from the cardContainer
   */
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