package it.polimi.ingsw.project.utils.TypeAdapters.Deserializers;


/**
 * it is used to deserialize from json the ActivableTile
 */
public class ActivableTileDeserializer {
  private int numTile;
  private int victoryPoints;
  private String type = "normalTile";

  public String getType() {
    return type;
  }

  public int getNumTile() {
    return numTile;
  }

  public int getVictoryPoints() {
    return victoryPoints;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setNumTile(int numTile) {
    this.numTile = numTile;
  }

  public void setVictoryPoints(int victoryPoints) {
    this.victoryPoints = victoryPoints;
  }
}
