package it.polimi.ingsw.project.model.board.card.leaderCard.perk;

import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProductionPerk extends Perk{

  public ProductionPerk(Resource resource, Board board) {
    super(resource, board);
  }

  private ResourceType chooseWhichResourceToEliminate() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenResourceTypeToEliminate;
    ResourceType type = null;
    System.out.println("Which it.polimi.ingsw.project.model.resource." +
            "Resource would you want to use for the production?\n1. Coin;\n2. Shield;\n3. Servant;\n4. Stone.");
    try {
      chosenResourceTypeToEliminate = reader.readLine();
      switch (Integer.parseInt(chosenResourceTypeToEliminate)) {
        case 1:
          type = ResourceType.Coin;
          break;
        case 2:
          type = ResourceType.Shield;
          break;
        case 3:
          type = ResourceType.Servant;
          break;
        case 4:
          type = ResourceType.Stone;
          break;
        default:
          throw new Exception();
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Cannot read commad!");
    }
    return type;
  }

  private Resource chooseResourceToProduce() {
    System.out.println("Which it.polimi.ingsw.project.model.resource." +
            "Resource do you want?\n1. Coin;\n2. Shield;\n3. Servant;\n4. Stone." +
            "\n(You will also advance 1 position in the Faith Map!)");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenResource;
    Resource resourceToAdd = null;
    try {
      chosenResource = reader.readLine();
      switch (Integer.parseInt(chosenResource)) {
        case 1:
          resourceToAdd = new Resource(ResourceType.Coin);
          break;
        case 2:
          resourceToAdd = new Resource(ResourceType.Shield);
          break;
        case 3:
          resourceToAdd = new Resource(ResourceType.Servant);
          break;
        case 4:
          resourceToAdd = new Resource(ResourceType.Stone);
          break;
        default:
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Cannot read commad!");
    }
    return resourceToAdd;
  }

  @Override
  public void usePerk(Resource resource) {
    Resource resourceToAdd = chooseResourceToProduce();
    List<Resource> resourceToBeInserted = new ArrayList<>();
    resourceToBeInserted.add(resourceToAdd);
    this.board.getWarehouse().eliminateResourceForProductionPerk(chooseWhichResourceToEliminate());
    this.board.getWarehouse().insertResources(resourceToBeInserted);
    this.board.moveForward();
  }

  public Resource getResource() {
    return super.getResource();
  }

}
