package it.polimi.ingsw.project.model.market;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.observer.Observable;

import java.util.*;

public class Market extends Observable<Market> implements Cloneable {
    private static final long serialVersionUID = 3840280593885092704L;
    // sarebbe da modificare inserendo la posizione delle biglie
    private Marble[][] tray = new Marble[4][3];
    // (0,2),(1,2),(2,2),(3,2)
    // (0,1),(1,1),(2,1),(3,1)
    // (0,0),(1,0),(2,0),(3,0)
    private Marble outsideMarble;

    /**
     * it constructs and init the market with a random tray
     */
    public Market() {
        List<Marble> trayList = new ArrayList<>();
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

    /**
     * @param marble input marble that needs to be converted
     * @param transmutationPerk it can be present if the leaderCard has one
     * @return it returns the ResourceType of the marble in input
     */
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
                return transmutationPerk;
        }
    }

    /**
     * extracts the resources in the specific column
     * @param columnIndex index of the column
     * @param transmutationPerk if it has the transmutationPerk it is different from null
     * @return it returns all the resources extracted by this column
     */
    private List<Resource> getResourceInColumn(Integer columnIndex, ResourceType transmutationPerk) {
        List<Resource> listOfObteinedResources = new ArrayList<>();
        Marble[] marbleColumn = tray[columnIndex];
        for (Marble marble : marbleColumn) {
            ResourceType type = convertMarbleToResourceType(marble, transmutationPerk);
            if (type != null) {
                listOfObteinedResources.add(new Resource(type));
            }
        }
        return listOfObteinedResources;
    }

    /**
     * extracts the resources in the specific row
     * @param rowIndex index of the row
     * @param transmutationPerk if it has the transmutationPerk it is different from null
     * @return it returns all the resources extracted by this column
     */
    private List<Resource> getResourceInRow(Integer rowIndex, ResourceType transmutationPerk) {
        List<Resource> listOfObteinedResources = new ArrayList<>();
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

    /**
     * it shifts the market in that chosen direction
     * @param index index of the column chosen for the shift
     */
    private void shiftMarblesInColumn(Integer index) {
        Marble[] oldArrayOfMarbles = tray[index].clone();
        tray[index][0] = outsideMarble;
        if (tray[index].length - 1 >= 0)
            System.arraycopy(oldArrayOfMarbles, 0, tray[index], 1, tray[index].length - 1);
        outsideMarble = oldArrayOfMarbles[oldArrayOfMarbles.length - 1];
    }

    /**
     * it shifts the market in that chosen direction
     * @param index index of the row chosen for the shift
     */
    private void shiftMarblesInRow(Integer index) {
        Marble[] oldArrayOfMarbles = new Marble[4];
        for (int i = tray.length - 1; i >= 0; i--) {
            oldArrayOfMarbles[i] = tray[i][index];
        }
        for (int j = 1; j < tray.length; j++) {
            tray[j - 1][index] = oldArrayOfMarbles[j];
        }
        tray[3][index] = outsideMarble;
        outsideMarble = oldArrayOfMarbles[0];
    }

    /**
     * it inserts the marble in the market, updates the market and returns the list of obtained Resources
     * @param axis chosen axis for the shift
     * @param position chosen index for the shift
     * @param transmutationPerk perk selected for the transmutation
     * @return it returns the list of the obtained resources
     */
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

    /**
     * @return it returns the market to string for the cli
     */
    public String toString(){
        StringBuilder string = new StringBuilder();
        for(int j = 2; j > -1; j--) {
            for (int i = 0; i < 4; i++) {
                string.append(" ").append(this.tray[i][j]);
            }
            string.append("\n");
        }
    return string.toString();
    }
}
