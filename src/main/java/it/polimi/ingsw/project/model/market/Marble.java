package it.polimi.ingsw.project.model.market;

import java.io.Serializable;

public class Marble implements Serializable {
  private MarbleType marble;

  public MarbleType getType() {
    return marble;
  }
}
