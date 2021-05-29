package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.ShelfFloor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseGUI extends JInternalFrame {
    private Map<ShelfFloor, List<JButton>> shelvesButtons;

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
    }

    private void createButtons() {
        this.shelvesButtons = new HashMap<>();
        List<JButton> firstFloor = new ArrayList<>();
        List<JButton> secondFloor = new ArrayList<>();
        List<JButton> thirdFloor = new ArrayList<>();
        firstFloor.add(new JButton());
        secondFloor.add(new JButton());
        secondFloor.add(new JButton());
        thirdFloor.add(new JButton());
        thirdFloor.add(new JButton());
        thirdFloor.add(new JButton());
        this.shelvesButtons.put(ShelfFloor.First, firstFloor);
        this.shelvesButtons.put(ShelfFloor.Second, secondFloor);
        this.shelvesButtons.put(ShelfFloor.Third, thirdFloor);
    }
}
