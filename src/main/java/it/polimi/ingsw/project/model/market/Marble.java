package it.polimi.ingsw.project.model.market;

import java.io.Serializable;

public class Marble implements Serializable {
  private final MarbleType marbleType;

  public Marble(MarbleType marbleType) {
    this.marbleType = marbleType;
  }

  public MarbleType getType() {
    return this.marbleType;
  }

  public String toString(){
    return this.marbleType.toString();
  }
}
