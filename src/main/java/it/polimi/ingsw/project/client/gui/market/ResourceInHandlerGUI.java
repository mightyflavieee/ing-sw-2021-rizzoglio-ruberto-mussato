package it.polimi.ingsw.project.client.gui.market;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.board.WarehouseGUI;
import it.polimi.ingsw.project.client.gui.listeners.market.*;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * it is used to select the resources that you collected from the market in order to put them in the warehouse
 */
public class ResourceInHandlerGUI extends JInternalFrame {
    private ResourceType resourceType;
    private final JButton firstShelfButton;
    private final JButton secondShelfButton;
    private final JButton thirdShelfButton;
    private final JButton extraDepositButton;
    private final JButton discardButton;
    private final JButton deselectButton;
    private int resourceNum;
    private final JLabel imageLabel;
    private final JLabel numLabel;
    private final WarehouseGUI warehouseGUI;
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


    /**
     * enables or disables the insert buttons according to the rules
     */
    public void refresh(){
        Map<ShelfFloor,Integer> numOfResourcesPerShelves = this.warehouseGUI.getNumberOfResoucesPerShelf();
        Map<ShelfFloor,ResourceType> resourceTypePerShelf = this.warehouseGUI.getResourceTypePerShelf();
        Map<ResourceType, Integer> extraDeposit = this.warehouseGUI.getWarehouseModel().getExtraDeposit();

        this.imageLabel.setIcon(Utils.readIcon("resourcetype/" + this.resourceType.toString() + ".png",30,30));
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
                this.firstShelfButton.setEnabled(numOfResourcesPerShelves.get(ShelfFloor.First) == 0
                        && resourceTypePerShelf.get(ShelfFloor.Second) != this.resourceType
                        && resourceTypePerShelf.get(ShelfFloor.Third) != this.resourceType);
                this.secondShelfButton.setEnabled(((resourceTypePerShelf.get(ShelfFloor.Second) == this.resourceType
                        && numOfResourcesPerShelves.get(ShelfFloor.Second) < 2)
                        || numOfResourcesPerShelves.get(ShelfFloor.Second) == 0)
                        && resourceTypePerShelf.get(ShelfFloor.First) != this.resourceType
                        && resourceTypePerShelf.get(ShelfFloor.Third) != this.resourceType);
                this.thirdShelfButton.setEnabled(((resourceTypePerShelf.get(ShelfFloor.Third) == this.resourceType
                        && numOfResourcesPerShelves.get(ShelfFloor.Third) < 3)
                        || numOfResourcesPerShelves.get(ShelfFloor.Third) == 0)
                        && resourceTypePerShelf.get(ShelfFloor.Second) != this.resourceType
                        && resourceTypePerShelf.get(ShelfFloor.First) != this.resourceType);
                if(extraDeposit == null){
                    this.extraDepositButton.setEnabled(false);
                }else{
                    if(!extraDeposit.containsKey(this.resourceType)){
                        this.extraDepositButton.setEnabled(false);
                    }else{
                        this.extraDepositButton.setEnabled(extraDeposit.get(this.resourceType) < 2);
                    }
                }
                this.discardButton.setEnabled(true);
                this.deselectButton.setEnabled(true);
                break;
            case 2:
                this.firstShelfButton.setEnabled(false);
                this.secondShelfButton.setEnabled(numOfResourcesPerShelves.get(ShelfFloor.Second) == 0
                        && resourceTypePerShelf.get(ShelfFloor.First) != this.resourceType
                        && resourceTypePerShelf.get(ShelfFloor.Third) != this.resourceType);
                this.thirdShelfButton.setEnabled(((resourceTypePerShelf.get(ShelfFloor.Third) == this.resourceType
                        && numOfResourcesPerShelves.get(ShelfFloor.Third) < 2)
                        || numOfResourcesPerShelves.get(ShelfFloor.Third) == 0)
                        && resourceTypePerShelf.get(ShelfFloor.Second) != this.resourceType
                        && resourceTypePerShelf.get(ShelfFloor.First) != this.resourceType);
                if(extraDeposit == null){
                    this.extraDepositButton.setEnabled(false);
                }else{
                    if(!extraDeposit.containsKey(this.resourceType)){
                        this.extraDepositButton.setEnabled(false);
                    }else{
                        this.extraDepositButton.setEnabled(extraDeposit.get(this.resourceType) == 0);
                    }
                }
                this.discardButton.setEnabled(true);
                this.deselectButton.setEnabled(true);
                break;
            case 3:
                this.firstShelfButton.setEnabled(false);
                this.secondShelfButton.setEnabled(false);
                this.thirdShelfButton.setEnabled(numOfResourcesPerShelves.get(ShelfFloor.Third) == 0
                        && resourceTypePerShelf.get(ShelfFloor.Second) != this.resourceType
                        && resourceTypePerShelf.get(ShelfFloor.First) != this.resourceType);
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

    /**
     * adds a coin to the resource selected
     */
    public void addCoin(){
        this.resourceType = ResourceType.Coin;
        this.resourceNum ++;
        this.refresh();
    }
    /**
     * adds a stone to the resource selected
     */
    public void addStone() {
        this.resourceType = ResourceType.Stone;
        this.resourceNum ++;
        this.refresh();
    }
    /**
     * adds a shield to the resource selected
     */
    public void addShield() {
        this.resourceType = ResourceType.Shield;
        this.resourceNum ++;
        this.refresh();
    }
    /**
     * adds a servant to the resource selected
     */
    public void addServant() {
        this.resourceType = ResourceType.Servant;
        this.resourceNum ++;
        this.refresh();
    }

    /**
     * set to 0 the number of selected resources, it is used when the resources are moved somewhere else
     */
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
