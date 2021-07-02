package it.polimi.ingsw.project.model.board.faithMap;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.faithMap.tile.ActivableTile;
import it.polimi.ingsw.project.model.board.faithMap.tile.NormalTile;
import it.polimi.ingsw.project.model.board.faithMap.tile.PapalCouncilTile;
import it.polimi.ingsw.project.model.board.faithMap.tile.VictoryPointsTile;
import it.polimi.ingsw.project.observer.custom.PapalCouncilObserver;
import it.polimi.ingsw.project.observer.custom.VictoryPointsObserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FaithMap implements Serializable {
  private static final long serialVersionUID = 3840280592475091294L;
  private int markerPosition;
  private int blackMarkerPosition;
  private List<ActivableTile> faithTiles;
  private final List<PapalFavourSlot> papalFavourSlots;
  // definire un costruttore (bisogna specificare ogni casella della mappa)

  /**
   * it construct the FaithMap Object
   * @param match added by the model to connect the match to the observers
   * @param player added by the model to generate the map on each player and add the observers
   */
  public FaithMap(Match match, Player player) {
    this.markerPosition = 0;
    this.blackMarkerPosition = 0;
    this.generateMap(match, player);
    this.papalFavourSlots = new ArrayList<>();
    this.papalFavourSlots.add(new PapalFavourSlot(2));
    this.papalFavourSlots.add(new PapalFavourSlot(3));
    this.papalFavourSlots.add(new PapalFavourSlot(4));
  }

  /**
   * it generates the map of the game
   * @param match added by the faithMap constructor to attach the observers
   * @param player added by the faithMap constructor to attach the observers
   */
  private void generateMap(Match match, Player player) { // la faithmap è fissa

    this.faithTiles = new ArrayList<>();
    this.faithTiles.add(new NormalTile());// 1
    this.faithTiles.add(new NormalTile());
    VictoryPointsTile victoryPointsTile = new VictoryPointsTile(1);
    victoryPointsTile.addObserver(new VictoryPointsObserver(player));
    this.faithTiles.add(victoryPointsTile);// 3
    this.faithTiles.add(new NormalTile());
    this.faithTiles.add(new NormalTile());
    victoryPointsTile = new VictoryPointsTile(2);
    victoryPointsTile.addObserver(new VictoryPointsObserver(player));
    this.faithTiles.add(victoryPointsTile);// 6
    this.faithTiles.add(new NormalTile());
    PapalCouncilTile papalCouncilTile = new PapalCouncilTile(1);
    papalCouncilTile.addObserver(new PapalCouncilObserver(match));
    this.faithTiles.add(papalCouncilTile);
    victoryPointsTile = new VictoryPointsTile(4);
    victoryPointsTile.addObserver(new VictoryPointsObserver(player));
    this.faithTiles.add(victoryPointsTile);// 9
    this.faithTiles.add(new NormalTile());
    this.faithTiles.add(new NormalTile());
    victoryPointsTile = new VictoryPointsTile(6);
    victoryPointsTile.addObserver(new VictoryPointsObserver(player));
    this.faithTiles.add(victoryPointsTile);// 12
    this.faithTiles.add(new NormalTile());
    this.faithTiles.add(new NormalTile());
    victoryPointsTile = new VictoryPointsTile(9);
    victoryPointsTile.addObserver(new VictoryPointsObserver(player));
    this.faithTiles.add(victoryPointsTile);// 15
    papalCouncilTile = new PapalCouncilTile(2);
    papalCouncilTile.addObserver(new PapalCouncilObserver(match));
    this.faithTiles.add(papalCouncilTile);
    this.faithTiles.add(new NormalTile());
    victoryPointsTile = new VictoryPointsTile(12);
    victoryPointsTile.addObserver(new VictoryPointsObserver(player));
    this.faithTiles.add(victoryPointsTile);// 18
    this.faithTiles.add(new NormalTile());
    this.faithTiles.add(new NormalTile());
    victoryPointsTile = new VictoryPointsTile(16);
    victoryPointsTile.addObserver(new VictoryPointsObserver(player));
    this.faithTiles.add(victoryPointsTile);// 21
    this.faithTiles.add(new NormalTile());
    this.faithTiles.add(new NormalTile());
    papalCouncilTile = new PapalCouncilTile(3);
    papalCouncilTile.addObserver(new PapalCouncilObserver(match));
    this.faithTiles.add(papalCouncilTile);// è anche una
                                          // it.polimi.ingsw.project.model.board.faithMap.tile.VictoryPointsTile
  }

  /**
   * function called for the persistence when a game is reloaded from the disk on the server
   * @param match passed by the model to re-add the specific observer we need
   * @param player passed by the model to re-add the specific observer we need
   */
  public void readdObservers(Match match, Player player) {
    for (ActivableTile activableTile : faithTiles) {
      activableTile.addObserverBasedOnType(match, player);
    }
  }

  public int getMarkerPosition() {
    return markerPosition;
  }

  public int getBlackMarkerPosition() {
    return blackMarkerPosition;
  }

  public List<ActivableTile> getFaithTiles() {
    return faithTiles;
  }

  public List<PapalFavourSlot> getPapalFavourSlots() {
    return papalFavourSlots;
  }

  /**
   * it moves forward the position of the player. It does nothing on the last position
   */
  public void moveForward() {
    if(markerPosition < 24) {
      faithTiles.get(markerPosition).activate();
      markerPosition++;
    }
  }

  /**
   * it moves forward Lorenzo for single player game
   * @return the new position of the black Marker for Lorenzo
   */
  public int moveForwardBlack() {
    if(blackMarkerPosition < 24) {
      faithTiles.get(blackMarkerPosition).activate();
      blackMarkerPosition++;
    }
    return blackMarkerPosition;
  }

  /**
   * it is called when someone steps on a PapalFavourSlot
   * @param numTile it is the tile of the position of the player
   * @return it returns the points gained by stepping over this papalFavourSlot
   */
  public int papalCouncil(int numTile) {
    if (this.papalFavourSlots.get(numTile - 1).getStatus() != PapalSlotStatus.Available) {
      // cioè se l'ho già presa o persa
      return 0;
    } else {
      switch (numTile) {
        case 1:
          if (this.markerPosition > 4) {
            this.papalFavourSlots.get(0).updateStatus(PapalSlotStatus.Taken);
            return this.papalFavourSlots.get(0).getVictoryPoints();
          } else {
            this.papalFavourSlots.get(0).updateStatus(PapalSlotStatus.Lost);
            return 0;
          }
        case 2:
          if (this.markerPosition > 11) {
            this.papalFavourSlots.get(numTile - 1).updateStatus(PapalSlotStatus.Taken);
            return this.papalFavourSlots.get(numTile - 1).getVictoryPoints();
          } else {
            this.papalFavourSlots.get(numTile - 1).updateStatus(PapalSlotStatus.Lost);
            return 0;
          }
        case 3:
          if (this.markerPosition == 23) {
            this.papalFavourSlots.get(numTile - 1).updateStatus(PapalSlotStatus.Taken);
            // ossia se sono il giocatore che arriva alla fine
            return 24;
          }
          if (this.markerPosition > 18) {
            this.papalFavourSlots.get(numTile - 1).updateStatus(PapalSlotStatus.Taken);
            return this.papalFavourSlots.get(numTile - 1).getVictoryPoints();
          } else {
            this.papalFavourSlots.get(numTile - 1).updateStatus(PapalSlotStatus.Lost);
            return 0;
          }
        default:
          break;
      }
    }
    return 0;
  }
}
