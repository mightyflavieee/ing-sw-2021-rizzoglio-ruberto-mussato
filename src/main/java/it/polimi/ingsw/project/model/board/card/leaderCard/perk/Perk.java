package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.resource.Resource;

public abstract class Perk {
  Resource resource;
  Board board;

  public Perk(Resource resource, Board board) {
    this.resource = resource;
    this.board = board;
  }

  public Resource getResource() {
    return resource;
  }

  public void usePerk(Resource resource){}
}
