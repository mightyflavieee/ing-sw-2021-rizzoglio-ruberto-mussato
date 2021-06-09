package it.polimi.ingsw.project.client.gui.market;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.client.gui.listeners.*;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ResourceInHandlerGUI extends JInternalFrame {
    private ResourceType resourceType;
    private JButton firstShelfButton, secondShelfButton, thirdShelfButton, extraDepositButton, discardButton,deselectButton;
    private int resourceNum;
    private JLabel imageLabel, numLabel;
    private WarehouseGUI warehouseGUI;
    public ResourceInHandlerGUI(WarehouseGUI warehouseGUI, ResourceInHandGUI resourceInHandGUI, GUI gui) {
        this.warehouseGUI = warehouseGUI;
        this.setTitle("Resource Selected:");
        this.setVisible(false);
        this.setLayout(new GridLayout(7,1));
        this.imageLabel = new JLabel();
        this.numLabel = new JLabel();
        this.firstShelfButton = new JButton("Insert in the First Shelf");
        this.secondShelfButton = new JButton("Insert in the Second Shelf");
        this.thirdShelfButton = new JButton("Insert in the Third Shelf");
        this.extraDepositButton = new JButton("Insert in the Extra Deposit");
        this.discardButton = new JButton("Discard");
        this.deselectButton = new JButton("Deselect");
        JPanel resourcePanel = new JPanel();
        resourcePanel.setLayout(new GridLayout(1,2));
        resourcePanel.add(imageLabel);
        resourcePanel.add(numLabel);
        this.add(resourcePanel);
        this.add(firstShelfButton);
        this.add(secondShelfButton);
        this.add(thirdShelfButton);
        this.add(extraDepositButton);
        this.add(discardButton);
        this.add(deselectButton);
        this.firstShelfButton.setEnabled(false);
        this.secondShelfButton.setEnabled(false);
        this.thirdShelfButton.setEnabled(false);
        this.extraDepositButton.setEnabled(false);
        this.discardButton.setEnabled(false);
        this.deselectButton.setEnabled(false);
        this.firstShelfButton.addActionListener(new InsertFirstShelfButtonListener(this,warehouseGUI,resourceInHandGUI,gui));
        this.secondShelfButton.addActionListener(new InsertSecondShelfButtonListener(this,warehouseGUI,resourceInHandGUI,gui));
        this.thirdShelfButton.addActionListener(new InsertThirdShelfButtonListener(this,warehouseGUI,resourceInHandGUI,gui));
        this.extraDepositButton.addActionListener(new InsertExtraDepositButtonListener(this,warehouseGUI,resourceInHandGUI,gui));
        this.discardButton.addActionListener(new DiscardButtonListener(this,resourceInHandGUI,gui));
        this.deselectButton.addActionListener(new DeselectButtonListener(this,resourceInHandGUI));
    }
    public void refresh(){
        Map<ShelfFloor,Integer> numOfResourcesPerShelves = this.warehouseGUI.getNumberOfResoucesPerShelf();
        Map<ShelfFloor,ResourceType> resourceTypePerShelf = this.warehouseGUI.getResourceTypePerShelf();
        Map<ResourceType, Integer> extraDeposit = this.warehouseGUI.getWarehouseModel().getExtraDeposit();

        this.imageLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/resourcetype/" + this.resourceType.toString() + ".png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        this.numLabel.setText(String.valueOf(this.resourceNum));
        switch (this.resourceNum){
            case 0:
                this.firstShelfButton.setEnabled(false);
                this.secondShelfButton.setEnabled(false);
                this.thirdShelfButton.setEnabled(false);
                this.extraDepositButton.setEnabled(false);
                this.discardButton.setEnabled(false);
                this.deselectButton.setEnabled(false);
                break;
            case 1:
                if(numOfResourcesPerShelves.get(ShelfFloor.First)==0) {
                    this.firstShelfButton.setEnabled(true);
                }else{
                    this.firstShelfButton.setEnabled(false);
                }
                if((resourceTypePerShelf.get(ShelfFloor.Second)==this.resourceType
                && numOfResourcesPerShelves.get(ShelfFloor.Second) < 2)
                        || numOfResourcesPerShelves.get(ShelfFloor.Second)==0) {
                    this.secondShelfButton.setEnabled(true);
                }else{
                    this.secondShelfButton.setEnabled(false);
                }
                if((resourceTypePerShelf.get(ShelfFloor.Third)==this.resourceType
                        && numOfResourcesPerShelves.get(ShelfFloor.Third) < 3)
                        || numOfResourcesPerShelves.get(ShelfFloor.Third)==0) {
                    this.thirdShelfButton.setEnabled(true);
                }else{
                    this.thirdShelfButton.setEnabled(false);
                }
                if(extraDeposit == null){
                    this.extraDepositButton.setEnabled(false);
                }else{
                    if(!extraDeposit.containsKey(this.resourceType)){
                        this.extraDepositButton.setEnabled(false);
                    }else{
                        if(extraDeposit.get(this.resourceType) < 2){
                            this.extraDepositButton.setEnabled(true);
                        }else{
                            this.extraDepositButton.setEnabled(false);
                        }
                    }
                }
                this.discardButton.setEnabled(true);
                this.deselectButton.setEnabled(true);
                break;
            case 2:
                this.firstShelfButton.setEnabled(false);
                if(numOfResourcesPerShelves.get(ShelfFloor.Second)==0) {
                    this.secondShelfButton.setEnabled(true);
                }else{
                    this.secondShelfButton.setEnabled(false);
                }
                if((resourceTypePerShelf.get(ShelfFloor.Third)==this.resourceType
                        && numOfResourcesPerShelves.get(ShelfFloor.Third) < 2)
                        || numOfResourcesPerShelves.get(ShelfFloor.Third)==0) {
                    this.thirdShelfButton.setEnabled(true);
                }else{
                    this.thirdShelfButton.setEnabled(false);
                }
                if(extraDeposit == null){
                    this.extraDepositButton.setEnabled(false);
                }else{
                    if(!extraDeposit.containsKey(this.resourceType)){
                        this.extraDepositButton.setEnabled(false);
                    }else{
                        if(extraDeposit.get(this.resourceType) ==0){
                            this.extraDepositButton.setEnabled(true);
                        }else{
                            this.extraDepositButton.setEnabled(false);
                        }
                    }
                }
                this.discardButton.setEnabled(true);
                this.deselectButton.setEnabled(true);
                break;
            case 3:
                this.firstShelfButton.setEnabled(false);
                this.secondShelfButton.setEnabled(false);
                if(numOfResourcesPerShelves.get(ShelfFloor.Third) == 0) {
                    this.thirdShelfButton.setEnabled(true);
                }else{
                    this.thirdShelfButton.setEnabled(false);
                }
                this.extraDepositButton.setEnabled(false);
                this.discardButton.setEnabled(true);
                this.deselectButton.setEnabled(true);
                break;
            default:
                this.firstShelfButton.setEnabled(false);
                this.secondShelfButton.setEnabled(false);
                this.thirdShelfButton.setEnabled(false);
                this.extraDepositButton.setEnabled(false);
                this.discardButton.setEnabled(true);
                this.deselectButton.setEnabled(true);
                break;
        }

    }
    public void addCoin(){
        this.resourceType = ResourceType.Coin;
        this.resourceNum ++;
        this.refresh();
    }
    public void addStone() {
        this.resourceType = ResourceType.Stone;
        this.resourceNum ++;
        this.refresh();
    }
    public void addShield() {
        this.resourceType = ResourceType.Shield;
        this.resourceNum ++;
        this.refresh();
    }
    public void addServant() {
        this.resourceType = ResourceType.Servant;
        this.resourceNum ++;
        this.refresh();
    }
    public void removeResource(){
        this.resourceNum = 0;
        this.refresh();
    }

    public ResourceType getResourceType() {
        return this.resourceType;
    }

    public int getResourceNum() {
        return this.resourceNum;
    }
}
