import java.io.*;
import java.util.*;

public class Player {
  private Board board;
  private boolean isConnected;
  private String nickname;
  private int victoryPoints;

  public Player(String nickname) {
    this.board = new Board();
    this.isConnected = true; // Da gestire stato della connessione del player (con funzione apposita)
    this.nickname = nickname;
    this.victoryPoints = 0;
  }

  private int chooseAction() {
    System.out.println(
        "Choose which action you want to perform:\n1. Take Resources from the Market;\n2. Buy one Development Card;\n3. Activate the Production.");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenActionString = "";
    try {
      chosenActionString = reader.readLine();
    } catch (Exception e) {
      e.printStackTrace();
    }
    int chosenAction = Integer.parseInt(chosenActionString);
    return chosenAction;
  }

  private void showPossiblePositions(Integer axis) {
    Integer maximumPosition;
    if (axis == 0) {
      maximumPosition = 4;
    } else {
      maximumPosition = 3;
    }
    for (int i = 0; i < maximumPosition; i++) {
      System.out.println(String.format("\"%d\" cell"));
    }
  }

  private Map<String, Integer> choosePosition() {
    Map<String, Integer> mapOfPositions = new HashMap<String, Integer>();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenActionString = "";
    System.out.println("In which axis you want to insert the marble?\n\"0\" for X axis. \n\"1\" for Y axis.");
    while (true) {
      try {
        chosenActionString = reader.readLine();
        mapOfPositions.put("axis", Integer.parseInt(chosenActionString));
        break;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Unable to parse integer, insert a Integer!");
      }
    }
    System.out.println("In which position you want to insert the marble?");
    showPossiblePositions(mapOfPositions.get("axis"));
    while (true) {
      try {
        chosenActionString = reader.readLine();
        mapOfPositions.put("position", Integer.parseInt(chosenActionString));
        break;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Unable to parse integer, insert a Integer!");
      }
    }
    return mapOfPositions;
  }

  private boolean takeResourcesFromMarket(Market market) {
    Map<String, Integer> chosenPosition = choosePosition();
    List<Resource> acquiredResources = market.insertMarble(chosenPosition.get("axis"), chosenPosition.get("position"));
    for (Resource resource : acquiredResources) {
      if (resource.getType() == ResourceType.Faith) {
        
      }
    }
    Warehouse warehouse = this.board.getWarehouse();
    warehouse.insertResources(acquiredResources);
    return true;
  }

  private void showCurrentResources() {
    Map<ResourceType, Integer> currentResources = this.board.mapAllResources();
    System.out.println("Your total resources are:");
    currentResources.entrySet().stream().forEach(singleMapObject -> {
      System.out.println(singleMapObject.getKey() + ": " + singleMapObject.getValue());
    });
  }

  private boolean buyDevelopmentCard(CardContainer cardContainer) {
    while (true) {
      showCurrentResources();
      String idCardSelected = cardContainer.showAndSelectFromAvailableCards();
      boolean hasBought = cardContainer.buy(idCardSelected, this.board);
      if (!hasBought) {
        System.out.println("Would you like to try to buy a different card?");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
          String chosenAction = reader.readLine();
          if (chosenAction == "NO") {
            return false;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        return true;
      }
    }
  }

  private boolean activateProduction() {
    boolean hasActivated = this.board.activateProductionOnCard();

    return hasActivated;
  }

  public Board getBoard() {
    return board;
  }

  public boolean getStatus() {
    return isConnected;
  }

  public String getNickname() {
    return nickname;
  }

  public int getVictoryPoints() {
    return victoryPoints;
  }

  public boolean playTurn(Market market, CardContainer cardContainer, ActionTokenContainer actionTokenContainer) {
    boolean retry = false;

    while (!retry) {
      int chosenAction = chooseAction();
      switch (chosenAction) {
      case 1:
        retry = takeResourcesFromMarket(market);
        break;
      case 2:
        retry = buyDevelopmentCard(cardContainer);
        break;
      case 3:
        retry = activateProduction();
        break;
      default:
        break;
      }
    }
    return false;
  }
}
