package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.DevCardPosition;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MapTrayGUI extends JInternalFrame {
    private Map<DevCardPosition, JButton> mapTrayButtons;

    public MapTrayGUI() {
        this.setTitle("Map Tray");
        this.setVisible(true);
        this.setLayout(new GridLayout(1, 3));
        createButtons();
        for (DevCardPosition position : this.mapTrayButtons.keySet()) {
            this.add(this.mapTrayButtons.get(position));
        }
        this.pack();
    }

    private void createButtons() {
        this.mapTrayButtons = new HashMap<>();
        this.mapTrayButtons.put(DevCardPosition.Left, new JButton());
        this.mapTrayButtons.put(DevCardPosition.Center, new JButton());
        this.mapTrayButtons.put(DevCardPosition.Right, new JButton());
    }
}
