package it.polimi.ingsw.project.model.market;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.observer.Observable;

import java.io.Serializable;
import java.util.*;

public class Market extends Observable<Market> implements Cloneable, Serializable {
    // sarebbe da modificare inserendo la posizione delle biglie
    private Marble[][] tray = new Marble[4][3];
    // (0,2),(1,2),(2,2),(3,2)
    // (0,1),(1,1),(2,1),(3,1)
    // (0,0),(1,0),(2,0),(3,0)
    private Marble outsideMarble;
    // TODO costruttore

    public Market() {
        List<Marble> trayList = new ArrayList<Marble>();
        trayList.add(new Marble(MarbleType.White));
        trayList.add(new Marble(MarbleType.White));
        trayList.add(new Marble(MarbleType.White));
        trayList.add(new Marble(MarbleType.White));
        trayList.add(new Marble(MarbleType.Blue));
        trayList.add(new Marble(MarbleType.Blue));
        trayList.add(new Marble(MarbleType.Grey));
        trayList.add(new Marble(MarbleType.Grey));
        trayList.add(new Marble(MarbleType.Yellow));
        trayList.add(new Marble(MarbleType.Yellow));
        trayList.add(new Marble(MarbleType.Purple));
        trayList.add(new Marble(MarbleType.Purple));
        trayList.add(new Marble(MarbleType.Red));
        Collections.shuffle(trayList);
        this.outsideMarble = trayList.remove(0);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                tray[i][j] = trayList.remove(0);
            }
        }

    }

    public Marble[][] getTray() {
        return tray.clone();
    }

    public void setTray(Marble[][] tray) {
        this.tray = tray;
    }

    public void setOutsideMarble(Marble outsideMarble) {
        this.outsideMarble = outsideMarble;
    }

    public Marble getOutSideMarble() {
        return outsideMarble;
    }

    private ResourceType convertMarbleToResourceType(Marble marble, ResourceType transmutationPerk) {
        switch (marble.getType()) {
            case Grey:
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
                if (transmutationPerk != null) {
                    return transmutationPerk;
                } else {
                    return null;
                }
        }
    }

    private List<Resource> getResourceInColumn(Integer columnIndex, ResourceType transmutationPerk) {
        List<Resource> listOfObteinedResources = new ArrayList<Resource>();
        Marble[] marbleColumn = tray[columnIndex];
        for (Marble marble : marbleColumn) {
            ResourceType type = convertMarbleToResourceType(marble, transmutationPerk);
            if (type != null) {
                listOfObteinedResources.add(new Resource(type));
            }
        }
        return listOfObteinedResources;
    }

    private List<Resource> getResourceInRow(Integer rowIndex, ResourceType transmutationPerk) {
        List<Resource> listOfObteinedResources = new ArrayList<Resource>();
        Marble[] oldArrayOfMarbles = new Marble[4];
        for (int i = 0; i < tray.length; i++) {
            oldArrayOfMarbles[i] = tray[i][rowIndex];
        }
        for (Marble marble : oldArrayOfMarbles) {
            ResourceType type = convertMarbleToResourceType(marble, transmutationPerk);
            if (type != null) {
                listOfObteinedResources.add(new Resource(type));
            }
        }
        return listOfObteinedResources;
    }

    private void shiftMarblesInColumn(Integer position) {
        Marble[] oldArrayOfMarbles = tray[position].clone();
        tray[position][0] = outsideMarble;
        for (int i = 1; i < tray[position].length; i++) {
            tray[position][i] = oldArrayOfMarbles[i - 1];
        }
        outsideMarble = oldArrayOfMarbles[oldArrayOfMarbles.length - 1];
    }

    private void shiftMarblesInRow(Integer position) {
        Marble[] oldArrayOfMarbles = new Marble[4];
        for (int i = tray.length - 1; i >= 0; i--) {
            oldArrayOfMarbles[i] = tray[i][position];
        }
        for (int j = 1; j < tray.length; j++) {
            tray[j - 1][position] = oldArrayOfMarbles[j];
        }
        tray[3][position] = outsideMarble;
        outsideMarble = oldArrayOfMarbles[0];
    }

    public List<Resource> insertMarble(Integer axis, Integer position, ResourceType transmutationPerk) {
        // axis = 1 = horizontal, position can be 0 1 2
        // axis = 0 = vertical, position can be 0 1 2 3
        // from right to left, from bottom to up
        // (0,2),(1,2),(2,2),(3,2)<--1
        // (0,1),(1,1),(2,1),(3,1)<--1
        // (0,0),(1,0),(2,0),(3,0)<--1
        // 0 0 0 0
        List<Resource> listOfObteinResources;
        if (axis == 0) {
            listOfObteinResources = getResourceInColumn(position, transmutationPerk);
            shiftMarblesInColumn(position);
        } else {
            listOfObteinResources = getResourceInRow(position, transmutationPerk);
            shiftMarblesInRow(position);
        }
        return listOfObteinResources;
    }
    public String toString(){
        String string = "";
        for(int j = 2; j > -1; j--) {
            for (int i = 0; i < 4; i++) {
                string = string + " " +this.tray[i][j].getType();
            }
            string = string + "\n";
        }
    return string;
    }

}
