package it.polimi.ingsw.project.client.gui.board;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.listeners.selectresources.WarehouseGUISelectResourceListener;
import it.polimi.ingsw.project.client.gui.listeners.warehouse.ArrowDownButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.warehouse.ArrowUpButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.warehouse.ResourceButtonListener;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class WarehouseGUI extends JInternalFrame {
    private LinkedHashMap<ShelfFloor, List<JButton>> shelvesButtons;
    private final Map<ShelfFloor, Integer> numberOfResoucesPerShelf;
    private final Map<ShelfFloor, ResourceType> resourceTypePerShelf;
    private boolean canChangeShelves;
    private ShelfFloor floorToChange;
    private final InformationsGUI informationsGUI;
    private Warehouse warehouseModel;
    private final ExtraDepositsGUI extraDepositsGUI;
    private boolean clickable;

    public WarehouseGUI(InformationsGUI informationsGUI, Warehouse warehouse) {
        this.setTitle("Warehouse");
        this.setVisible(true);
        //this.setLayout(new GridLayout(1, 2));
        this.setLayout(new GridLayout(2, 1));

        JPanel mainWarehouse = new JPanel();
        mainWarehouse.setLayout(new GridLayout(1, 2));

        this.informationsGUI = informationsGUI;

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
        mainWarehouse.add(resourcesColumns);
        mainWarehouse.add(arrowColumns);

        this.extraDepositsGUI = new ExtraDepositsGUI(this.informationsGUI, warehouse);
        this.add(mainWarehouse);
        this.add(this.extraDepositsGUI);

        this.pack();

        this.numberOfResoucesPerShelf = new HashMap<>();
        this.resourceTypePerShelf = new HashMap<>();
        this.numberOfResoucesPerShelf.put(ShelfFloor.First, 0);
        this.numberOfResoucesPerShelf.put(ShelfFloor.Second, 0);
        this.numberOfResoucesPerShelf.put(ShelfFloor.Third, 0);
        this.warehouseModel = warehouse;
        this.clickable = false;
        this.setCanChangeShelves(false);
        refresh();
    }

    public Map<ShelfFloor, List<JButton>> getShelvesButtons() { return shelvesButtons; }

    public InformationsGUI getInformationsGUI() { return this.informationsGUI; }

    /**
     * returns this.canChangeShelves
     */
    public boolean getCanChangeShelves() { return this.canChangeShelves; }

    /**
     * returns this.floorToChange
     */
    public ShelfFloor getFloorToChange() { return this.floorToChange; }

    /**
     * updates the local model and its visual representation
     */
    public void setWarehouseModel(Warehouse warehouseModel) {
        this.warehouseModel = warehouseModel;
        this.extraDepositsGUI.setWarehouseModel(warehouseModel);
        this.canChangeShelves = false;
        this.refresh();

    }

    /**
     * @param value sets this.canChangeShelves to true or false
     */
    public void setCanChangeShelves(boolean value) {
        this.canChangeShelves = value;
        this.refresh();
    }

    /**
     * @param floor sets floor to change for change shelf move
     */
    public void setFloorToChange(ShelfFloor floor) { this.floorToChange = floor; }

    // creates the arrow buttons on the left of the warehouse
    private List<JButton> createArrowButtons() {
        List<JButton> arrowButtons = new ArrayList<>();
        JButton arrowUp = new JButton();
        JButton arrowDown = new JButton();
        arrowUp.setIcon(Utils.readIcon("warehouse/arrow_up.png",10,10));
        arrowDown.setIcon(Utils.readIcon("warehouse/arrow_down.png",10,10));
        arrowUp.addActionListener(new ArrowUpButtonListener(this, this.informationsGUI));
        arrowDown.addActionListener(new ArrowDownButtonListener(this, this.informationsGUI));
        arrowButtons.add(arrowUp);
        arrowButtons.add(arrowDown);
        return arrowButtons;
    }

    // creates the resources buttons that are part of the shelves
    private void createResourceButtons() {
        this.shelvesButtons = new LinkedHashMap<>();
        List<JButton> firstFloor = new ArrayList<>();
        List<JButton> secondFloor = new ArrayList<>();
        List<JButton> thirdFloor = new ArrayList<>();
        JButton button1FirstFloor = new JButton();
        JButton button1SecondFloor = new JButton();
        JButton button2SecondFloor = new JButton();
        JButton button1ThirdFloor = new JButton();
        JButton button2ThirdFloor = new JButton();
        JButton button3ThirdFloor = new JButton();
        button1FirstFloor.setIcon(Utils.readIcon("warehouse/warehouse_no_resource.png",10,10));
        button1SecondFloor.setIcon(Utils.readIcon("warehouse/warehouse_no_resource.png",10,10));
        button2SecondFloor.setIcon(Utils.readIcon("warehouse/warehouse_no_resource.png",10,10));
        button1ThirdFloor.setIcon(Utils.readIcon("warehouse/warehouse_no_resource.png",10,10));
        button2ThirdFloor.setIcon(Utils.readIcon("warehouse/warehouse_no_resource.png",10,10));
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

    /**
     * updates the visual representation based on the local model
     */
    public void refresh() {
        this.extraDepositsGUI.refresh();
        for (ShelfFloor floor : this.warehouseModel.getShelves().keySet()) {
            int count = 0;
            ResourceType resourceTypeInShelf = null;
            for (Resource resource : this.warehouseModel.getShelves().get(floor)) {
                this.shelvesButtons.get(floor).get(count).setIcon(Utils.readIcon("resourcetype/" + resource.getType() + ".png",10,10));
                count++;
                resourceTypeInShelf = resource.getType();
            }
            this.numberOfResoucesPerShelf.put(floor, count);
            this.resourceTypePerShelf.put(floor, resourceTypeInShelf);
            if (this.shelvesButtons.get(floor).size() > this.warehouseModel.getShelves().get(floor).size()) {
                for (int i = this.shelvesButtons.get(floor).size(); i > this.warehouseModel.getShelves().get(floor).size(); i--) {
                    this.shelvesButtons.get(floor).get(i-1).setIcon(Utils.readIcon("warehouse/warehouse_no_resource.png",10,10));

                }
            }
        }
    }

    /**
     * changed the buttons' listener to listener used for the selection of the resources
     */
    public void addSelectResourceListeners() {
        int count;
        for (ShelfFloor floor : this.warehouseModel.getShelves().keySet()) {
            count = 0;
            for (Resource resource : this.warehouseModel.getShelves().get(floor)) {
                this.shelvesButtons.get(floor).get(count).addActionListener(new WarehouseGUISelectResourceListener(this,
                        this.informationsGUI, resource.getType(), floor, count));
                count++;
            }
        }
    }

    /**
     * removes the buttons' listeners used for the selection of the resources
     */
    public void removeSelectResourceListeners() {
        for (ShelfFloor floor : this.shelvesButtons.keySet()) {
            for (JButton button : this.shelvesButtons.get(floor)) {
                for (ActionListener actionListener : button.getActionListeners() ) {
                    button.removeActionListener(actionListener);
                }
            }
        }
        for (ShelfFloor floor : this.shelvesButtons.keySet()) {
            for (JButton button : this.shelvesButtons.get(floor)) {
                button.addActionListener(new ResourceButtonListener(this, floor));
            }
        }
    }

    /**
     * inserts the resources indicated in the chosen shelf and updates the visual representation
     */
    public void insertInShelf(ShelfFloor floor, List<Resource> resourcesToInsert) {
        this.warehouseModel.insertInShelves(floor, resourcesToInsert);
        refresh();
    }

    /**
     * swaps two shelves floor and updates the visual representation
     * it also updates the insertion options in the ResourceInHandler menu
     */
    public void changeShelf(ShelfFloor floorA, ShelfFloor floorB) {
        this.warehouseModel.swapShelves(floorA, floorB);
        refresh();

    }

    public Map<ShelfFloor, Integer> getNumberOfResoucesPerShelf() {
        return numberOfResoucesPerShelf;
    }

    public Map<ShelfFloor, ResourceType> getResourceTypePerShelf() {
        return resourceTypePerShelf;
    }

    public Warehouse getWarehouseModel() {
        return warehouseModel;
    }

    public ExtraDepositsGUI getExtraDepositsGUI() {
        return extraDepositsGUI;
    }

    /**
     * clicking on the button has no effect
     */
    public void disableAllButtons() {
        this.clickable = false;
        this.extraDepositsGUI.disableAllButtons();
    }

    /**
     * clicking on the button has effect
     */
    public void enableAllButtons() {
        this.clickable = true;
        this.extraDepositsGUI.enableAllButtons();
    }

    /**
     * returns true if the button is enabled, this method is used by the listeners to see if they need to perform an action or not
     */
    public boolean isClickable() {
        return this.clickable;
    }

    /**
     * updates the local model and the visual representation based on the input player
     */
    public void setWarehouseByPlayer(Player mePlayer) {
        this.warehouseModel = mePlayer.getWarehouse();
        refresh();
    }
}
