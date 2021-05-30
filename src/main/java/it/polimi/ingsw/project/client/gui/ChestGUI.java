package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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

        JButton coinButton = new JButton();
        JButton servantButton = new JButton();
        JButton shieldButton = new JButton();
        JButton stoneButton = new JButton();
        coinButton.setLayout(new GridLayout(1, 2));
        servantButton.setLayout(new GridLayout(1, 2));
        shieldButton.setLayout(new GridLayout(1, 2));
        stoneButton.setLayout(new GridLayout(1, 2));
        List<JLabel> resourcesIcons = createResourcesIcons();
        createLabels();
        coinButton.add(resourcesIcons.get(0));
        servantButton.add(resourcesIcons.get(1));
        shieldButton.add(resourcesIcons.get(2));
        stoneButton.add(resourcesIcons.get(3));
        coinButton.add(this.numberOfResouces.get(ResourceType.Coin));
        servantButton.add(this.numberOfResouces.get(ResourceType.Servant));
        shieldButton.add(this.numberOfResouces.get(ResourceType.Shield));
        stoneButton.add(this.numberOfResouces.get(ResourceType.Stone));
        this.resourcesButtons.put(ResourceType.Coin, coinButton);
        this.resourcesButtons.put(ResourceType.Servant, servantButton);
        this.resourcesButtons.put(ResourceType.Shield, shieldButton);
        this.resourcesButtons.put(ResourceType.Stone, stoneButton);
    }

    private List<JLabel> createResourcesIcons() {
        List<JLabel> list = new ArrayList<>();
        JLabel coinLabel = new JLabel();
        JLabel servantLabel = new JLabel();
        JLabel shieldLabel = new JLabel();
        JLabel stoneLabel = new JLabel();
        coinLabel.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/resourcetype/Coin.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        servantLabel.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/resourcetype/Servant.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        shieldLabel.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/resourcetype/Shield.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        stoneLabel.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/resourcetype/Stone.png")
                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
        list.add(coinLabel);
        list.add(servantLabel);
        list.add(shieldLabel);
        list.add(stoneLabel);
        return list;
    }

    private void createLabels() {
        this.numberOfResouces = new HashMap<>();
        JLabel coinLabel = new JLabel();
        JLabel servantLabel = new JLabel();
        JLabel shieldLabel = new JLabel();
        JLabel stoneLabel = new JLabel();
        coinLabel.setText("= 0");
        servantLabel.setText("= 0");
        shieldLabel.setText("= 0");
        stoneLabel.setText("= 0");
        this.numberOfResouces.put(ResourceType.Coin, coinLabel);
        this.numberOfResouces.put(ResourceType.Servant, servantLabel);
        this.numberOfResouces.put(ResourceType.Shield, shieldLabel);
        this.numberOfResouces.put(ResourceType.Stone, stoneLabel);
    }

    public void updateLabel(ResourceType type, int value) {
        for (ResourceType resourceType : this.numberOfResouces.keySet()) {
            if (resourceType == type) {
                this.numberOfResouces.get(type).setText(String.valueOf(value));
            }
        }
    }
}