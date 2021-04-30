package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.resource.Resource;

import java.util.Collections;
import java.util.List;

public class TakeMarketResourcesMove extends Move {

  private final Warehouse warehouse;
  private final List<Resource> discardedResources;

  public TakeMarketResourcesMove(
    Warehouse warehouse,
    List<Resource> discardedResources
  ) {
    this.warehouse = warehouse;
    this.discardedResources = discardedResources;
  }

  @Override
  public boolean isFeasibleMove(Match match) {
    return match.isFeasibleTakeMarketResourcesMove(
      warehouse,
      discardedResources
    );
  }

  @Override
  public void performMove(Match match) {
    match.performTakeMarketResourceMove(warehouse, discardedResources);
  }

  @Override
  public String toString() {
    //TODO
    return new String("Generic Move");
  }
}
