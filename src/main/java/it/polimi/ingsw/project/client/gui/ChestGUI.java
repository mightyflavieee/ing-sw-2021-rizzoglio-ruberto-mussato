package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChestGUI extends JInternalFrame {
    private Map<ResourceType, JButton> resourcesButtons;
    private Map<ResourceType, JLabel> numberOfResouces;

    public ChestGUI() {
        this.setTitle("Chest");
        this.setVisible(true);
        this.setLayout(new GridLayout(4, 1));
        createButtons();
        for (ResourceType type : this.resourcesButtons.keySet()) {
            this.add(this.resourcesButtons.get(type));
        }
        this.pack();
    }

    private void createButtons() {
        this.resourcesButtons = new HashMap<>();
        this.resourcesButtons.put(ResourceType.Coin, new JButton());
        this.resourcesButtons.put(ResourceType.Servant, new JButton());
        this.resourcesButtons.put(ResourceType.Shield, new JButton());
        this.resourcesButtons.put(ResourceType.Stone, new JButton());
    }
}
