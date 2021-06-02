package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;

public class ResourceInHandlerGUI extends JInternalFrame {
    private ResourceType resourceType;
    private JButton firstShelfButton, secondShelfButton, thirdShelfButton, discardButton;
    private int resourceNum;
    private JLabel imageLabel, numLabel;
    public ResourceInHandlerGUI() {
        this.setTitle("Resource Selected:");
        this.setVisible(false);
        this.setLayout(new GridLayout(5,1));
        this.imageLabel = new JLabel();
        this.numLabel = new JLabel();
        this.firstShelfButton = new JButton("Insert in the First Shelf");
        this.secondShelfButton = new JButton("Insert in the Second Shelf");
        this.thirdShelfButton = new JButton("Insert in the Third Shelf");
        this.discardButton = new JButton("Discard");
        JPanel resourcePanel = new JPanel();
        resourcePanel.setLayout(new GridLayout(1,2));
        resourcePanel.add(imageLabel);
        resourcePanel.add(numLabel);
        this.add(resourcePanel);
        this.add(firstShelfButton);
        this.add(secondShelfButton);
        this.add(thirdShelfButton);
        this.add(discardButton);
    }
    public void refresh(){
//        if(this.resourceNum == 0){
//            this.setVisible(false);
//        }else{
//            this.setVisible(true);
//        }
        this.imageLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/" + this.resourceType.toString() + ".png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.numLabel.setText(String.valueOf(this.resourceNum));
    }
    public void addCoin(){
        this.resourceType = ResourceType.Coin;
        this.resourceNum = this.resourceNum + 1;
        this.refresh();
    }

    public void addStone() {
        this.resourceType = ResourceType.Stone;
        this.resourceNum = this.resourceNum + 1;
        this.refresh();
    }
    public void addShield() {
        this.resourceType = ResourceType.Shield;
        this.resourceNum = this.resourceNum + 1;
        this.refresh();
    }
    public void addServant() {
        this.resourceType = ResourceType.Servant;
        this.resourceNum = this.resourceNum + 1;
        this.refresh();
    }
}
