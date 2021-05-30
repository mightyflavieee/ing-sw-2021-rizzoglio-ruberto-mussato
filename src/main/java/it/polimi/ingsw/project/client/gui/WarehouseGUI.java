package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.ShelfFloor;
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

    public WarehouseGUI() {
        this.setTitle("Warehouse");
        this.setVisible(true);
        this.setLayout(new GridLayout(3, 1));
        createButtons();
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
        this.add(firstFloor);
        this.add(secondFloor);
        this.add(thirdFloor);
        this.pack();
        this.numberOfResoucesPerShelf.put(ShelfFloor.First, 0);
        this.numberOfResoucesPerShelf.put(ShelfFloor.Second, 0);
        this.numberOfResoucesPerShelf.put(ShelfFloor.Third, 0);
    }

    private void createButtons() {
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
                .ImageIcon("src/main/resources/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button1SecondFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button2SecondFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button1ThirdFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button2ThirdFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        button3ThirdFloor.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/warehouse_no_resource.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
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

    // updates the ShelfFloor. If newResourceType is null, then it eliminates numOfResourcesToChange
    // from that ShelfFloor.
    public void updateShelfFloor(ShelfFloor floor, ResourceType newResourceType, int numOfResourcesToChange) {
        for (ShelfFloor shelfFloor : this.shelvesButtons.keySet()) {
            if (shelfFloor == floor) {
                for (int i = 0; i < numOfResourcesToChange; i++) {
                    if (newResourceType == null) {
                        this.shelvesButtons.get(shelfFloor).get(numOfResourcesToChange-i-1).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/warehouse_no_resource.png")
                                        .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                    } else {
                        this.shelvesButtons.get(shelfFloor).get(i).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/resourcetype/" + newResourceType + ".png")
                                        .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));

                    }
                }
                this.numberOfResoucesPerShelf.put(shelfFloor, numOfResourcesToChange);
                this.resourceTypePerShelf.put(shelfFloor, newResourceType);
                // if the numOfResourcesToChange is less than the total number of resources present in that shelf,
                // puts them to void image
                if (numOfResourcesToChange < this.shelvesButtons.get(shelfFloor).size()) {
                    for (int i = 0; i < (this.shelvesButtons.get(shelfFloor).size()-numOfResourcesToChange); i++) {
                        this.shelvesButtons.get(shelfFloor).get(i).setIcon(new ImageIcon(
                                new ImageIcon("src/main/resources/warehouse_no_resource.png")
                                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
                    }
                }
            }
        }
    }

    public void changeShelf(ShelfFloor floorA, ShelfFloor floorB) {
        if ((floorA == ShelfFloor.First && floorB == ShelfFloor.Second) ||
                (floorB == ShelfFloor.First && floorA == ShelfFloor.Second)) {
            if (this.numberOfResoucesPerShelf.get(ShelfFloor.Second) != 2) {
                updateShelfFloor(floorA, this.resourceTypePerShelf.get(floorB), 1);
            } else {
                //TODO ALERT, CANNOT DO THIS CHANGE
            }
        }
        if ((floorA == ShelfFloor.First && floorB == ShelfFloor.Third) ||
                (floorB == ShelfFloor.First && floorA == ShelfFloor.Third)) {
            if (this.numberOfResoucesPerShelf.get(ShelfFloor.Third) < 2) {
                updateShelfFloor(floorA, this.resourceTypePerShelf.get(floorB), 1);
            } else {
                //TODO ALERT, CANNOT DO THIS CHANGE
            }
        }
        if ((floorA == ShelfFloor.Second && floorB == ShelfFloor.Third) ||
                (floorB == ShelfFloor.Second && floorA == ShelfFloor.Third)) {
            if (this.numberOfResoucesPerShelf.get(ShelfFloor.Third) != 3) {
                updateShelfFloor(floorA, this.resourceTypePerShelf.get(floorB),
                        Math.max(this.numberOfResoucesPerShelf.get(floorA), this.numberOfResoucesPerShelf.get(floorB)));
            } else {
                //TODO ALERT, CANNOT DO THIS CHANGE
            }
        }
    }
}
