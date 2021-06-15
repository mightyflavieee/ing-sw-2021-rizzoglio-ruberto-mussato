package it.polimi.ingsw.project.utils.TypeAdapters.Deserializers;

import it.polimi.ingsw.project.model.board.card.CardColor;

public class ActionTokenDeserializer {
  private CardColor cardColor;
  private String type;

  public String getType() {
    return type;
  }

  public CardColor getCardColor() {
    return cardColor;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setCardColor(CardColor cardColor) {
    this.cardColor = cardColor;
  }

}
