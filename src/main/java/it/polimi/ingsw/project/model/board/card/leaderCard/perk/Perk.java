package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.resource.Resource;

import java.io.Serializable;

public abstract class Perk implements Serializable {
  Resource resource;
  Board board;

  public Perk(Resource resource, Board board) {
    this.resource = resource;
    this.board = board;
  }

  public Resource getResource() {
    return new Resource(this.resource.getType());
  }

  public void usePerk(Resource resource){
    //TODO da implementare
  }
}
