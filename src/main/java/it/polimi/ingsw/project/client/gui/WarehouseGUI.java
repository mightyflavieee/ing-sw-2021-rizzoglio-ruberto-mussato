package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.warehouse.ResourceButtonListener;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseGUI extends JInternalFrame {
    private Map<ShelfFloor, List<JButton>> shelvesButtons;
    private Map<ShelfFloor, Integer> numberOfResoucesPerShelf;
    private Map<ShelfFloor, ResourceType> resourceTypePerShelf;
    private boolean canChangeShelves;
    private ShelfFloor floorToChange;
    private InformationsGUI informationsGUI;
    private Warehouse warehouseModel;

    public WarehouseGUI(InformationsGUI informationsGUI, Warehouse warehouse) {
        this.setTitle("Warehouse");
        this.setVisible(true);
        this.setLayout(new GridLayout(1, 2));
        JPanel resourcesColumns = new JPanel();
        JPanel arrowColumns = new JPanel();
        resourcesColumns.setLayout(new GridLayout(3, 1));
        arrowColumns.setLayout(new GridLayout(2, 1));
        createResourceButtons();
        List<JButton> arrowButtons = createArrowButtons();
        JPanel firstFloor = new JPanel();
        JPanel secondFloor = new JPanel();
        JPanel thirdFloor = new JPanel();
        firstFloor.setLayout(new GridLayout(1, 1));
        secondFloor.setLayout(new GridLayout(1, 2));
        thirdFloor.setLayout(new GridLayout(1, 3));
        for (ShelfFloor floor : this.shelvesButtons.keySet()) {
            for (JButton button : this.shelvesButtons.get(floor)) {
                if (floor == ShelfFloor.First) {
                    firstFloor.add(button);
                } else {
                    if (floor == ShelfFloor.Second) {
                        secondFloor.add(button);
                    } else {
                        thirdFloor.add(button);
                    }
                }
            }
        }
        resourcesColumns.add(firstFloor);
        resourcesColumns.add(secondFloor);
        resourcesColumns.add(thirdFloor);
        for (JButton button: arrowButtons) {
            arrowColumns.add(button);
        }
        this.add(resourcesColumns);
        this.add(arrowColumns);
        this.pack();
        this.numberOfResoucesPerShelf = new HashMap<>();
        this.resourceTypePerShelf = new HashMap<>();
        this.numberOfResoucesPerShelf.put(ShelfFloor.First, 0);
        this.numberOfResoucesPerShelf.put(ShelfFloor.Second, 0);
        this.numberOfResoucesPerShelf.put(ShelfFloor.Third, 0);
        this.informationsGUI = informationsGUI;
        this.warehouseModel = warehouse;
        refresh();
    }

    public InformationsGUI getInformationsGUI() { return this.informationsGUI; }

    // returns this.canChangeShelves
    public boolean getCanChangeShelves() { return this.canChangeShelves; }

    // returns this.floorToChange
    public ShelfFloor getFloorToChange() { return this.floorToChange; }

    // sets this.canChangeShelves to true or false
    public void setCanChangeShelves(boolean value) { this.canChangeShelves = value; }

    // sets floor to change for change shelf move
    public void setFloorToChange(ShelfFloor floor) { this.floorToChange = floor; }

    // creates the arrow buttons on the left of the warehouse
    private List<JButton> createArrowButtons() {
        List<JButton> arrowButtons = new ArrayList<>();
        JButton arrowUp = new JButton();
        JButton arrowDown = new JButton();
        arrowUp.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse/arrow_up.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        arrowDown.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse/arrow_down.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        arrowButtons.add(arrowUp);
        arrowButtons.add(arrowDown);
        return arrowButtons;
    }

    // creates the resources buttons that are part of the shelves
    private void createResourceButtons() {
        this.shelvesButtons = new HashMap<>();
        List<JButton> firstFloor = new ArrayList<>();
        List<JButton> secondFloor = new ArrayList<>();
        List<JButton> thirdFloor = new ArrayList<>();
        JButton button1FirstFloor = new JButton();
        JButton button1SecondFloor = new JButton();
        JButton button2SecondFloor = new JButton();
        JButton button1ThirdFloor = new JButton();
        JButton button2ThirdFloor = new JButton();
        JButton button3ThirdFloor = new JButton();
        button1FirstFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button1SecondFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button2SecondFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button1ThirdFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button2ThirdFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button3ThirdFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button1FirstFloor.addActionListener(new ResourceButtonListener(this, ShelfFloor.First));
        button1SecondFloor.addActionListener(new ResourceButtonListener(this, ShelfFloor.Second));
        button2SecondFloor.addActionListener(new ResourceButtonListener(this, ShelfFloor.Second));
        button1ThirdFloor.addActionListener(new ResourceButtonListener(this, ShelfFloor.Third));
        button2ThirdFloor.addActionListener(new ResourceButtonListener(this, ShelfFloor.Third));
        button3ThirdFloor.addActionListener(new ResourceButtonListener(this, ShelfFloor.Third));
        firstFloor.add(button1FirstFloor);
        secondFloor.add(button1SecondFloor);
        secondFloor.add(button2SecondFloor);
        thirdFloor.add(button1ThirdFloor);
        thirdFloor.add(button2ThirdFloor);
        thirdFloor.add(button3ThirdFloor);
        this.shelvesButtons.put(ShelfFloor.First, firstFloor);
        this.shelvesButtons.put(ShelfFloor.Second, secondFloor);
        this.shelvesButtons.put(ShelfFloor.Third, thirdFloor);
    }

    private void refresh() {
        for (ShelfFloor floor : this.warehouseModel.getShelves().keySet()) {
            int count = 0;
            ResourceType resourceTypeInShelf = null;
            for (Resource resource : this.warehouseModel.getShelves().get(floor)) {
                this.shelvesButtons.get(floor).get(count).setIcon(new ImageIcon(
                        new ImageIcon("src/main/resources/resourcetype/" + resource.getType() + ".png")
                        .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                count++;
                resourceTypeInShelf = resource.getType();
            }
            this.numberOfResoucesPerShelf.put(floor, count);
            this.resourceTypePerShelf.put(floor, resourceTypeInShelf);
            if (this.shelvesButtons.get(floor).size() > this.warehouseModel.getShelves().get(floor).size()) {
                for (int i = this.shelvesButtons.get(floor).size(); i > this.warehouseModel.getShelves().get(floor).size(); i--) {
                    this.shelvesButtons.get(floor).get(i-1).setIcon(new ImageIcon(
                            new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                            .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                }
            }
        }
    }

    // inserts the resources indicated in the chosen shelf
    public void insertInShelf(ShelfFloor floor, List<Resource> resourcesToInsert) {
        this.warehouseModel.insertInShelves(floor, resourcesToInsert);
        refresh();
    }

    public void changeShelf(ShelfFloor floorA, ShelfFloor floorB) {
        this.warehouseModel.swapShelves(floorA, floorB);
        refresh();
    }

        /*
    // updates the ShelfFloor
    public void updateShelfFloor(ShelfFloor floor, ResourceType newResourceType, int numOfResourcesToChange) {
        for (ShelfFloor shelfFloor : this.shelvesButtons.keySet()) {
            if (shelfFloor == floor) {
                for (int i = 0; i < numOfResourcesToChange; i++) {
                    this.shelvesButtons.get(shelfFloor).get(i).setIcon(new ImageIcon(
                            new ImageIcon("src/main/resources/resourcetype/" + newResourceType + ".png")
                            .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                }
                this.numberOfResoucesPerShelf.put(shelfFloor, numOfResourcesToChange);
                this.resourceTypePerShelf.put(shelfFloor, newResourceType);
                // if the numOfResourcesToChange is less than the total number of resources present in that shelf,
                // puts in the other buttons the void image
                if (numOfResourcesToChange < this.shelvesButtons.get(shelfFloor).size()) {
                    for (int i = this.shelvesButtons.get(shelfFloor).size(); i > numOfResourcesToChange; i--) {
                        this.shelvesButtons.get(shelfFloor).get(i).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                    }
                }
            }
        }
    }

    // swaps resources from floorA to floorB. Outputs an error message on the information section (in GUI)
    // if this operation cannot be done
    public void changeShelf(ShelfFloor floorA, ShelfFloor floorB) {
        if ((floorA == ShelfFloor.First && floorB == ShelfFloor.Second) ||
                (floorB == ShelfFloor.First && floorA == ShelfFloor.Second)) {
            if (this.numberOfResoucesPerShelf.get(ShelfFloor.Second) < 2) {
                ResourceType resourceTypeFloorA = this.resourceTypePerShelf.get(floorA);
                updateShelfFloor(floorA, this.resourceTypePerShelf.get(floorB), 1);
                updateShelfFloor(floorB, resourceTypeFloorA, 1);
            } else {
                //TODO ALERT, CANNOT DO THIS CHANGE
            }
        }
        if ((floorA == ShelfFloor.First && floorB == ShelfFloor.Third) ||
                (floorB == ShelfFloor.First && floorA == ShelfFloor.Third)) {
            if (this.numberOfResoucesPerShelf.get(ShelfFloor.Third) < 2) {
                ResourceType resourceTypeFloorA = this.resourceTypePerShelf.get(floorA);
                updateShelfFloor(floorA, this.resourceTypePerShelf.get(floorB), 1);
                updateShelfFloor(floorB, resourceTypeFloorA, 1);
            } else {
                //TODO ALERT, CANNOT DO THIS CHANGE
            }
        }
        if ((floorA == ShelfFloor.Second && floorB == ShelfFloor.Third) ||
                (floorB == ShelfFloor.Second && floorA == ShelfFloor.Third)) {
            if (this.numberOfResoucesPerShelf.get(ShelfFloor.Third) != 3) {
                ResourceType resourceTypeFloorA = this.resourceTypePerShelf.get(floorA);
                int numberOfResourcesFloorA = this.numberOfResoucesPerShelf.get(floorA);
                updateShelfFloor(floorA, this.resourceTypePerShelf.get(floorB), this.numberOfResoucesPerShelf.get(floorB));
                updateShelfFloor(floorB, resourceTypeFloorA, numberOfResourcesFloorA);
            } else {
                //TODO ALERT, CANNOT DO THIS CHANGE
            }
        }
    }*/


    public Map<ShelfFloor, Integer> getNumberOfResoucesPerShelf() {
        return numberOfResoucesPerShelf;
    }

    public Map<ShelfFloor, ResourceType> getResourceTypePerShelf() {
        return resourceTypePerShelf;
    }
}
