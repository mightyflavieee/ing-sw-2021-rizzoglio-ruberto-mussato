import java.util.List;

public class FaithMap {
  private int markerPosition;
  private int blackMarkerPosition;
  private List<ActivableTile> faithTiles;
  private List<PapalFavourSlot> papalFavourSlots;
  //definire un costruttore (bisogna specificare ogni casella della mappa)
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

  public void moveForward(){ //il player che chiama questa funziona passa se stesso
    markerPosition++;
    faithTiles.get(markerPosition).activate();
  }
  public void moveForwardBlack(){
    blackMarkerPosition++;
    faithTiles.get(markerPosition).activate();
  }
  public void papalCouncil(int numTile){
    //i consigli sono ogni 8, implementare di conseguenza; il numero di caselle prima cambia
  }
}
