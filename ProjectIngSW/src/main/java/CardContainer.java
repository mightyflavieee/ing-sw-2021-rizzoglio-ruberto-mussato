import java.util.*;

public class CardContainer {
  private Map<CardLevel,Map<CardColor,List<DevelopmentCard>>> cardContainer;

  public Map<CardLevel,Map<CardColor,List<DevelopmentCard>>> getCardContainer(){
    return cardContainer;
  }

  public String showAndSelectFromAvailableCards(){
    // select one card and return the id
    return "id da mettere";
  }
  
  public boolean buy(String idCardSelected, Board board){
    Map<ResourceType, Integer> currentResources = board.mapAllResources();
    return false;
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
            return true; //carte finite quindi hai perso
          }
    return false; //hai scartato 2 carte e va bene così
  }

}