package it.polimi.ingsw.project.model.market;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.*;

public class Market {
  // sarebbe da modificare inserendo la posizione delle biglie
  private Marble[][] tray;
  private Marble outsideMarble;

  public Marble[][] getTray() {
    return tray;
  }

  public Marble getOutSideMarble() {
    return outsideMarble;
  }

  private ResourceType convertMarbleToResourceType(Marble marble, boolean isTransmutationPresent,
                                                   ResourceType resourceType) {
    switch (marble.getType()) {
    case Black:
      return ResourceType.Stone;
    case Blue:
      return ResourceType.Shield;
    case Purple:
      return ResourceType.Servant;
    case Red:
      return ResourceType.Faith;
    case Yellow:
      return ResourceType.Coin;
    default:
      if (isTransmutationPresent) {
        return resourceType;
      } else {
        return null;
      }
    }
  }


  private List<Resource> getResourceInColumn(Integer columnIndex, boolean isTransmutationPresent,
                                             ResourceType resourceType) {
    List<Resource> listOfObteinedResources = new ArrayList<Resource>();
    Marble[] marbleColumn = tray[columnIndex];
    for (Marble marble : marbleColumn) {
      ResourceType type = convertMarbleToResourceType(marble, isTransmutationPresent, resourceType);
      if (type != null) {
        listOfObteinedResources.add(new Resource(type));
      }
    }
    return listOfObteinedResources;
  }


  private List<Resource> getResourceInRow(Integer rowIndex, boolean isTransmutationPresent, ResourceType resourceType) {
    List<Resource> listOfObteinedResources = new ArrayList<Resource>();
    Marble[] oldArrayOfMarbles = new Marble[4];
    for (int i = 0; i < tray.length; i++) {
      oldArrayOfMarbles[i] = tray[i][rowIndex];
    }
    for (Marble marble : oldArrayOfMarbles) {
      ResourceType type = convertMarbleToResourceType(marble, isTransmutationPresent, resourceType);
      if (type != null) {
        listOfObteinedResources.add(new Resource(type));
      }
    }
    return listOfObteinedResources;
  }


  private void shiftMarblesInColumn(Integer position) {
    Marble[] oldArrayOfMarbles = tray[position];
    tray[position][0] = outsideMarble;
    for (int i = 1; i < tray[position].length; i++) {
      tray[position][i] = oldArrayOfMarbles[i - 1];
    }
    outsideMarble = oldArrayOfMarbles[oldArrayOfMarbles.length];
  }


  private void shiftMarblesInRow(Integer position) {
    Marble[] oldArrayOfMarbles = new Marble[4];
    for (int i = 0; i < tray.length; i++) {
      oldArrayOfMarbles[i] = tray[i][position];
    }
    tray[0][position] = outsideMarble;
    tray[position] = oldArrayOfMarbles;
    outsideMarble = oldArrayOfMarbles[oldArrayOfMarbles.length];
  }


  public List<Resource> insertMarble(Integer axis, Integer position, boolean isTransmutationPresent,
      ResourceType resourceType) {
    // if axis chosen was x else y
    List<Resource> listOfObteinResources;
    if (axis == 0) {
      listOfObteinResources = getResourceInColumn(position, isTransmutationPresent, resourceType);
      shiftMarblesInColumn(position);
    } else {
      listOfObteinResources = getResourceInRow(position, isTransmutationPresent, resourceType);
      shiftMarblesInRow(position);
    }
    return listOfObteinResources;
  }
}
