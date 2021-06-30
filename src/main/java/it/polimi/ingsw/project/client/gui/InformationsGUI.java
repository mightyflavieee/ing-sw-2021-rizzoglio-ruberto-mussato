package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.informations.MainPhaseHandler;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandlerGUI;
import it.polimi.ingsw.project.model.TurnPhase;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformationsGUI extends JInternalFrame {
    private TurnPhase turnPhase;
    private final JTextArea jTextArea;
    private JInternalFrame phaseFrame;
    private ResourceInHandlerGUI resourceInHandler;
    private MainPhaseHandler mainPhaseHandler;
    private SelectResourcesHandler selectResourcesHandler;
    private BuyDevCardMoveHandler buyDevCardMoveHandler;
    private ProductionMoveHandler productionMoveHandler;
    private final GUI gui;

    public InformationsGUI(GUI gui, TurnPhase turnPhase) {
        this.gui = gui;
        this.setTitle("Informations");
        //this.setLayout(new GridLayout(2,1));
        this.setLayout(new BorderLayout());
        this.jTextArea = new JTextArea();
        this.jTextArea.setEditable(false);
        this.jTextArea.setLineWrap(true);
        this.jTextArea.setWrapStyleWord(true);
        this.add(this.jTextArea,BorderLayout.NORTH);
        this.turnPhase = turnPhase;
        //this.resourceInHandler = new ResourceInHandlerGUI();
        this.refresh();
        this.setVisible(true);
        this.pack();
    }

    public JTextArea getjTextArea() { return this.jTextArea; }

    public MainPhaseHandler getMainPhaseHandler() { return mainPhaseHandler; }

    public ResourceInHandlerGUI getResourceInHandler() { return resourceInHandler; }

    public ProductionMoveHandler getProductionMoveHandler() { return productionMoveHandler; }

    public GUI getGUI() { return gui; }

    /**
     * shows the initial message for each turn phase
     */
    public void refresh() {
        switch (turnPhase) {
            case InitialPhase:

                if (this.mainPhaseHandler != null) {
                    //this.mainPhaseHandler.setVisible(false);
                    this.mainPhaseHandler.dispose();
                }
                if(this.resourceInHandler!=null){
                    this.resourceInHandler.dispose();
                }
                if(this.phaseFrame!=null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to go on",this.gui);
                this.add(this.phaseFrame);
                break;
            case EndPhase:

                if (this.mainPhaseHandler != null) {
                   // this.mainPhaseHandler.setVisible(false);
                    this.mainPhaseHandler.dispose();
                }


                if(this.phaseFrame!=null){
                    this.phaseFrame.dispose();
                }
                if(this.resourceInHandler!=null){
                    this.resourceInHandler.dispose();
                }
                this.jTextArea.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to end the turn", this.gui);
                this.add(this.phaseFrame);
                break;
            case MainPhase:
                if(this.phaseFrame != null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(true);

                this.jTextArea.setText("You must choose and perform one of the following actions:");
                if (this.mainPhaseHandler == null) {
                    this.mainPhaseHandler = new MainPhaseHandler(this.gui);
                    this.add(this.mainPhaseHandler);
                }
                this.mainPhaseHandler.setVisible(true);

                break;
            case WaitPhase:
            default:
                if(this.mainPhaseHandler!=null) {
                  //  this.mainPhaseHandler.setVisible(false);
                    this.mainPhaseHandler.dispose();
                }
                if(this.resourceInHandler!=null){
                    this.resourceInHandler.dispose();
                }

                if(this.phaseFrame != null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(true);
                this.jTextArea.setText("It's not your turn");
                break;
        }
    }

    /**
     * shows the message when you collected some resources from the market
     */
    public void showMarketInformations(Boolean hasfaith){

        this.mainPhaseHandler.setVisible(false);

        if(hasfaith){
            this.jTextArea.setText("You collected some resources from the Market!\n" +
                    "You can see them in the Resources in Hand panel" +
                    "\nStore them in the Shelves" +
                    "\nYou collected also a faith point!");
        }else{
            this.jTextArea.setText("You collected some resources from the Market!\n" +
                "You can see them in the Resources in Hand panel" +
                "\nStore them in the Shelves");}
        if(this.resourceInHandler==null){
            this.resourceInHandler = new ResourceInHandlerGUI(this.gui.getBoardGUI().getWarehouseGUI(),
                    this.gui.getResourceInHandGUI(),gui);
        }else{
            this.resourceInHandler.reset();
        }
        this.resourceInHandler.setVisible(true);
        this.add(resourceInHandler);

    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
        this.refresh();
    }

    /**
     * show a message that informs you that you are watching the view of an opponent of yours
     */
    public void showOpponentView(String nickname) {
        this.jTextArea.setText("Your are watching "+ nickname +" view");
        this.jTextArea.setVisible(true);
    }

    /**
     * creates the ProductionMoveHandler
     */
    public void createProductionMoveHandler(ProductionType productionType) {
        this.productionMoveHandler = new ProductionMoveHandler();
        this.productionMoveHandler.setProductionType(productionType);
    }

    /**
     * creates the SelectResourcesHandler and updates the informationsGUI
     */
    public void createSelectResourcesHandlerForProduction() {
        this.selectResourcesHandler = new SelectResourcesHandler();
        this.productionMoveHandler.setSelectResourcesHandler(this.selectResourcesHandler);
        showProductionInfo();
    }

    /**
     * Shows on the informationsGUI the necessary information for the BuyDevCardMove
     */
    public void showProductionInfo() {
        String selectedResourcesFromWarehouse = convertResourcesToString(this.selectResourcesHandler.getResourcesFromWarehouse());
        String selectResourcesFromExtraDeposit = convertResourcesToString(this.selectResourcesHandler.getResourcesFromExtraDeposit());
        String selectedResourcesFromChest = convertResourcesToString(this.selectResourcesHandler.getResourcesFromChest());
        Map<ResourceType, Integer> insertedResources = countResources(this.selectResourcesHandler);
        Map<ResourceType, Integer> resourcesRequired = this.productionMoveHandler.calculateResourcesRequired();
        if (verifyResourcesTargetReached(insertedResources, this.productionMoveHandler.getResourcesRequired())) {
            this.gui.sendProductionMove(this.productionMoveHandler);
            this.productionMoveHandler = null;
        } else {
            Map<ResourceType, Integer> missingResources = calculateMissingResources(insertedResources, resourcesRequired);
            String missingResourceString = convertResourcesToString(missingResources);
            this.jTextArea.setText("You selected this resources:\n"
                    + "From Warehouse:\n" + selectedResourcesFromWarehouse
                    + "From Extra Deposit:\n" + selectResourcesFromExtraDeposit
                    + "From Chest:\n" + selectedResourcesFromChest
                    + "You stll need this resources:\n" + missingResourceString
                    + "Select the resources required from the Warehouse and/or the chest.");
        }
    }

    /**
     * initializes the buffer class buyDevCardMoveHandler after you selected a position for the development card
     */
    public void createBuyDevCardHandler(DevCardPosition position) {
        this.buyDevCardMoveHandler = new BuyDevCardMoveHandler();
        this.buyDevCardMoveHandler.setPosition(position);
    }

    /**
     * creates the BuyDevCardMoveHandler and the SelectResourcesHandler and updates
     * the informationsGUI
     */
    public void createSelectResourcesHandlerForPurchase(DevelopmentCard developmentCard) {
        this.selectResourcesHandler = new SelectResourcesHandler();
        this.buyDevCardMoveHandler.setDevelopmentCard(developmentCard);
        this.buyDevCardMoveHandler.setSelectResourcesHandler(this.selectResourcesHandler);
        showDevCardPurchaseInfo();
    }

    /**
     * Shows on the informationsGUI the necessary information for the BuyDevCardMove
     */
    public void showDevCardPurchaseInfo() {
        // creates the string for the informationsGUI for the selected resources from warehouse
        // and selected resources from the chest
        String selectedResourcesFromWarehouse = convertResourcesToString(this.selectResourcesHandler.getResourcesFromWarehouse());
        String selectResourcesFromExtraDeposit = convertResourcesToString(this.selectResourcesHandler.getResourcesFromExtraDeposit());
        String selectedResourcesFromChest = convertResourcesToString(this.selectResourcesHandler.getResourcesFromChest());
        // counts the inserted resources, if they are enough, cunstructs the BuyDevCardMove, otherwise
        // prints in the informationsGUI the necessary information
        Map<ResourceType, Integer> insertedResources = countResources(this.selectResourcesHandler);
        if (verifyResourcesTargetReached(insertedResources,
                this.buyDevCardMoveHandler.getDevelopmentCard().getRequiredResources())) {
            this.gui.sendBuyDevCardMove(this.buyDevCardMoveHandler);
            this.buyDevCardMoveHandler = null;
        } else {
            Map<ResourceType, Integer> missingResources = calculateMissingResources(insertedResources,
                    this.buyDevCardMoveHandler.getDevelopmentCard().getRequiredResources());
            String missingResourceString = convertResourcesToString(missingResources);
            this.jTextArea.setText("You selected this resources:\n"
                    + "From Warehouse:\n" + selectedResourcesFromWarehouse
                    + "From Extra Deposit:\n" + selectResourcesFromExtraDeposit
                    + "From Chest:\n" + selectedResourcesFromChest
                    + "You stll need this resources:\n" + missingResourceString
                    + "Select the resources required from the Warehouse and/or the chest.");
        }
    }

    /**
     * verifies if yourResources has reached the target of resources
     */
    private boolean verifyResourcesTargetReached(Map<ResourceType, Integer> yourResources, Map<ResourceType, Integer> target) {
        for (ResourceType type : target.keySet()) {
            if (yourResources.containsKey(type)) {
                if (yourResources.get(type) < target.get(type)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * calculates the missing resources from target
     */
    private Map<ResourceType, Integer> calculateMissingResources(Map<ResourceType, Integer> yourResources, Map<ResourceType, Integer> target) {
        Map<ResourceType, Integer> missingResources = new HashMap<>();
        List<ResourceType> discounts = this.gui.getBoardGUI().getBoardModel().getDiscounts();
        if (discounts.size() > 0) {
            for (ResourceType targetType : target.keySet()) {
                for (ResourceType discountType : discounts) {
                    if (targetType == discountType) {
                        target.put(targetType, target.get(targetType) - 1);
                    }
                }
            }
        }
        for (ResourceType type : target.keySet()) {
            if (!yourResources.containsKey(type)) {
                missingResources.put(type, target.get(type));
            } else {
                if (yourResources.get(type) < target.get(type)) {
                    missingResources.put(type, target.get(type) - yourResources.get(type));
                }
            }
        }
        return missingResources;
    }

    /**
     * counts the current resources selected
     */
    public Map<ResourceType, Integer> countResources(SelectResourcesHandler selectResourcesHandler) {
        Map<ResourceType, Integer> insertedResources = new HashMap<>();
        List<ResourceType> resourceTypeList = new ArrayList<>();
        resourceTypeList.add(ResourceType.Coin);
        resourceTypeList.add(ResourceType.Servant);
        resourceTypeList.add(ResourceType.Shield);
        resourceTypeList.add(ResourceType.Stone);
        for (ResourceType type : resourceTypeList) {
            if (selectResourcesHandler.getResourcesFromWarehouse().containsKey(type)) {
                if (selectResourcesHandler.getResourcesFromChest().containsKey(type)) {
                    if (selectResourcesHandler.getResourcesFromExtraDeposit().containsKey(type)) {
                        insertedResources.put(type,
                                selectResourcesHandler.getResourcesFromWarehouse().get(type) +
                                selectResourcesHandler.getResourcesFromExtraDeposit().get(type) +
                                selectResourcesHandler.getResourcesFromChest().get(type));
                    } else {
                        insertedResources.put(type,
                                selectResourcesHandler.getResourcesFromWarehouse().get(type) +
                                selectResourcesHandler.getResourcesFromChest().get(type));
                    }
                } else {
                    if (selectResourcesHandler.getResourcesFromExtraDeposit().containsKey(type)) {
                        insertedResources.put(type,
                                selectResourcesHandler.getResourcesFromWarehouse().get(type) +
                                selectResourcesHandler.getResourcesFromExtraDeposit().get(type));
                    } else {
                        insertedResources.put(type, selectResourcesHandler.getResourcesFromWarehouse().get(type));
                    }
                }
            } else {
                if (selectResourcesHandler.getResourcesFromChest().containsKey(type)) {
                    if (selectResourcesHandler.getResourcesFromExtraDeposit().containsKey(type)) {
                        insertedResources.put(type,
                                selectResourcesHandler.getResourcesFromChest().get(type) +
                                selectResourcesHandler.getResourcesFromExtraDeposit().get(type));
                    } else {
                        insertedResources.put(type, selectResourcesHandler.getResourcesFromChest().get(type));
                    }
                } else {
                    if (selectResourcesHandler.getResourcesFromExtraDeposit().containsKey(type)) {
                        insertedResources.put(type, selectResourcesHandler.getResourcesFromExtraDeposit().get(type));
                    }
                }
            }
        }
        return insertedResources;
    }

    /**
     * Creates the string for the informationsGUI for the selected resources
     */
    private String convertResourcesToString(Map<ResourceType, Integer> selectedResources) {
        StringBuilder selectedResourcesString = new StringBuilder();
        for (ResourceType type : selectedResources.keySet()) {
            selectedResourcesString.append(type).append(": ").append(selectedResources.get(type)).append("\n");
        }
        return selectedResourcesString.toString();
    }

    /**
     * updates the SelectResourcesHandler incrementing the current resources chosen
     */
    public void updateSelectResourcesHandler(ResourceType resourceType, String location) {
        switch (location) {
            case "Warehouse":
                this.selectResourcesHandler.incrementResourcesFromWarehouse(resourceType);
                break;
            case "Chest":
                this.selectResourcesHandler.incrementResourcesFromChest(resourceType);
                break;
            case "ExtraDeposit":
                this.selectResourcesHandler.incrementResourcesFromExtraDeposit(resourceType);
                break;
        }
    }

    /**
     * adds a coin to the resource selected in the resource in handler frame
     */
    public void addCoin() { this.resourceInHandler.addCoin(); }

    /**
     * adds a stone to the resource selected in the resource in handler frame
     */
    public void addStone() { this.resourceInHandler.addStone(); }

    /**
     * adds a shield to the resource selected in the resource in handler frame
     */
    public void addShield() { this.resourceInHandler.addShield(); }

    /**
     * adds a servant to the resource selected in the resource in handler frame
     */
    public void addServant() { this.resourceInHandler.addServant(); }

    public void goToTransmutationPanel() { this.mainPhaseHandler.goToTransmutationPanel(); }
}
