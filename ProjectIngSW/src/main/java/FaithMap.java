import java.util.List;

public class FaithMap {
  private int markerPosition;
  private int blackMarkerPosition;
  private List<ActivableTile> faithTiles;
  private List<PapalFavourSlot> papalFavourSlots;

  public int getMarkerPosition(){
    return markerPosition;
  }

  public int getBlackMarkerPosition(){
    return blackMarkerPosition;
  }

  public List<ActivableTile> getFaithTiles(){
    return faithTiles;
  }

  public List<PapalFavourSlot> getPapalFavourSlots(){
    return papalFavourSlots;
  }

  public void moveForward(){
    markerPosition++;
  }
  public void moveForwardBlack(){
    blackMarkerPosition++;
  }
}
