import java.util.*;

public class CardContainer {
  private Map<CardLevel,Map<CardColor,List<DevelopmentCard>>> cardContainer;
  public Map<CardLevel,Map<CardColor,List<DevelopmentCard>>> getCardContainer(){
    return cardContainer;
  }
}
