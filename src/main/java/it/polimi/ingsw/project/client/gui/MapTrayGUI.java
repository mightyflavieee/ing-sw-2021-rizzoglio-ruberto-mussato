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
        JButton leftButton = new JButton();
        JButton centerButton = new JButton();
        JButton rightButton = new JButton();
        leftButton.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/retro_devcard.png")
                .getImage().getScaledInstance(100, 160, Image.SCALE_SMOOTH)));
        centerButton.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/retro_devcard.png")
                .getImage().getScaledInstance(100, 160, Image.SCALE_SMOOTH)));
        rightButton.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/retro_devcard.png")
                .getImage().getScaledInstance(100, 160, Image.SCALE_SMOOTH)));
        this.mapTrayButtons.put(DevCardPosition.Left, leftButton);
        this.mapTrayButtons.put(DevCardPosition.Center, centerButton);
        this.mapTrayButtons.put(DevCardPosition.Right, rightButton);
    }

    public void setMapTrayImage(String id, DevCardPosition position) {
        this.mapTrayButtons.get(position).setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/developmentcards/"+ id + ".png")
                .getImage().getScaledInstance(100, 160, Image.SCALE_SMOOTH)));
    }
}
