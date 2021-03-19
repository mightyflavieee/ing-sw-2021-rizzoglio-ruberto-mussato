import java.util.*;
public class DevelopmentCard extends Card{
  private CardColor color;
  private CardLevel level;
  private Production production;

  public CardColor getColor() {
    return color;
  }

  public CardLevel getLevel() {
    return level;
  }

  public Production getProduction(){
    return production;
  }

  public List<Resource> useProduction(List<Resource> inputResources){
    return null;
  }

}
