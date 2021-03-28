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
}
