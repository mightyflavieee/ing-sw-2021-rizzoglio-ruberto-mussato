package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.informations.MainPhaseHandler;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandlerGUI;
import it.polimi.ingsw.project.model.TurnPhase;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
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
    private JTextArea jTextArea;
    private JInternalFrame phaseFrame;
    private ResourceInHandlerGUI resourceInHandler;
    private MainPhaseHandler mainPhaseHandler;
    private SelectResourcesHandler selectResourcesHandler;
    private BuyDevCardMoveHandler buyDevCardMoveHandler;
    private ProductionMoveHandler productionMoveHandler;
    private GUI gui;

    public InformationsGUI(GUI gui, TurnPhase turnPhase) {
        this.gui = gui;
        this.setTitle("Informations");
        //this.setLayout(new GridLayout(2,1));
        this.setLayout(new BorderLayout());
        this.jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        this.add(this.jTextArea,BorderLayout.NORTH);
        this.turnPhase = turnPhase;
        //this.resourceInHandler = new ResourceInHandlerGUI();
        this.refresh();
        this.setVisible(true);
        this.pack();
    }

    public JTextArea getjTextArea() { return this.jTextArea; }

    public MainPhaseHandler getMainPhaseHandler() { return mainPhaseHandler; }

    public void refresh() {
        switch (turnPhase) {
            case InitialPhase:

                if (this.mainPhaseHandler != null) {
                    this.mainPhaseHandler.setVisible(false);
                }

                if(this.phaseFrame!=null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to go on",this.gui);
                this.add(this.phaseFrame);
                break;
            case EndPhase:

                this.mainPhaseHandler.setVisible(false);
                this.remove(this.mainPhaseHandler);
                this.mainPhaseHandler = null;

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
                /*this.jTextArea.setText("You must choose and perform one of the following actions:" +
                        "\nTake Resources from the Market" +
                        "\nBuy one Development Card" +
                        "\nActivate the Production");*/

                this.jTextArea.setText("You must choose and perform one of the following actions:");
                if (this.mainPhaseHandler == null) {
                    this.mainPhaseHandler = new MainPhaseHandler(this.gui);
                    this.add(this.mainPhaseHandler);
                }
                this.mainPhaseHandler.setVisible(true);

                break;
            case WaitPhase:
            default:

                this.mainPhaseHandler.setVisible(false);

                if(this.phaseFrame != null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(true);
                this.jTextArea.setText("It's not your turn");
                break;
        }
    }

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
        //todo creare resourceinHandler se serve
        if(this.resourceInHandler==null){
            this.resourceInHandler = new ResourceInHandlerGUI(gui.getWarehouseGUI(),gui.getResourceInHandGUI(),gui);
        }
        this.resourceInHandler.setVisible(true);
        this.add(resourceInHandler);

    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
        this.refresh();
    }

    public void showOpponentView(String nickname) {
        this.jTextArea.setText("Your are watching "+ nickname +" view");
        this.jTextArea.setVisible(true);
    }

    // creates the ProductionMoveHandler and the SelectResourcesHandler and updates
    // the informationsGUI
    public void createSelectResourcesHandlerForProduction(DevelopmentCard developmentCard,
                                                          LeaderCard leaderCard,
                                                          boolean isBoardProductionPresent,
                                                          ResourceType resourceToManufactureForLeaderCardProduction,
                                                          ResourceType resourceToManufactureForBoardProduction) {
        this.productionMoveHandler = new ProductionMoveHandler();
        if (developmentCard != null) {
            this.productionMoveHandler.setDevCard(developmentCard);
        }
        if (leaderCard != null) {
            this.productionMoveHandler.setLeaderCard(leaderCard);
            this.productionMoveHandler.setBoardOrPerkManufacturedResource(resourceToManufactureForLeaderCardProduction);
        }
        if (isBoardProductionPresent) {
            this.productionMoveHandler.setBoardOrPerkManufacturedResource(resourceToManufactureForBoardProduction);
        }
        this.productionMoveHandler.setProductionType(determineProductionType(developmentCard.getId(),
                leaderCard.getId(),
                isBoardProductionPresent));
        this.selectResourcesHandler = new SelectResourcesHandler();
        this.productionMoveHandler.setSelectResourcesHandler(this.selectResourcesHandler);
        showProductionInfo();
    }

    // Shows on the informationsGUI the necessary information for the BuyDevCardMove
    private void showProductionInfo() {
        String selectedResourcesFromWarehouse = convertResourcesToString(this.selectResourcesHandler.getResourcesFromWarehouse());
        String selectedResourcesFromChest = convertResourcesToString(this.selectResourcesHandler.getResourcesFromChest());
        Map<ResourceType, Integer> insertedResources = countResources(this.selectResourcesHandler);
        Map<ResourceType, Integer> resourcesRequired = this.productionMoveHandler.calculateResourcesRequired(
                this.productionMoveHandler.getDevCard(),
                this.productionMoveHandler.getLeaderCard(),
                this.productionMoveHandler.getBoardRequiredResources(),
                this.productionMoveHandler.getProductionType());
        if (verifyResourcesTargetReached(insertedResources, this.productionMoveHandler.getResourcesRequired())) {
            this.gui.sendProductionMove(this.productionMoveHandler);
        } else {
            Map<ResourceType, Integer> missingResources = calculateMissingResources(insertedResources, resourcesRequired);
            String missingResourceString = convertResourcesToString(missingResources);
            this.jTextArea.setText("You selected this resources:\n"
                    + "From Warehouse:\n" + selectedResourcesFromWarehouse + "From Chest:\n" + selectedResourcesFromChest
                    + "You stll need this resources:\n" + missingResourceString
                    + "Select the resources required from the Warehouse and/or the chest.");
        }
    }

    // determines the production type
    private ProductionType determineProductionType(String devCardID, String leaderCardID, boolean isBoardProductionPresent) {
        ProductionType productionType;
        if (devCardID != null) {
            if (leaderCardID != null) {
                if (isBoardProductionPresent) {
                    productionType = ProductionType.BoardAndDevCardAndLeaderCard;
                } else {
                    productionType = ProductionType.DevCardAndLeader;
                }
            } else {
                if (isBoardProductionPresent) {
                    productionType = ProductionType.BoardAndDevCard;
                } else {
                    productionType = ProductionType.DevCard;
                }
            }
        } else {
            if (leaderCardID != null) {
                if (isBoardProductionPresent) {
                    productionType = ProductionType.BoardAndLeaderCard;
                } else {
                    productionType = ProductionType.LeaderCard;
                }
            } else {
                productionType = ProductionType.Board;
            }
        }
        return  productionType;
    }

    public void createBuyDevCardHandler(DevCardPosition position) {
        this.buyDevCardMoveHandler = new BuyDevCardMoveHandler();
        this.buyDevCardMoveHandler.setPosition(position);
    }

    // creates the BuyDevCardMoveHandler and the SelectResourcesHandler and updates
    // the informationsGUI
    public void createSelectResourcesHandlerForPurchase(DevelopmentCard developmentCard) {
        this.selectResourcesHandler = new SelectResourcesHandler();
        this.buyDevCardMoveHandler.setDevelopmentCard(developmentCard);
        this.buyDevCardMoveHandler.setSelectResourcesHandler(this.selectResourcesHandler);
        showDevCardPurchaseInfo();
    }

    // Shows on the informationsGUI the necessary information for the BuyDevCardMove
    public void showDevCardPurchaseInfo() {
        // creates the string for the informationsGUI for the selected resources from warehouse
        // and selected resources from the chest
        String selectedResourcesFromWarehouse = convertResourcesToString(this.selectResourcesHandler.getResourcesFromWarehouse());
        String selectedResourcesFromChest = convertResourcesToString(this.selectResourcesHandler.getResourcesFromChest());
        // counts the inserted resources, if they are enough, cunstructs the BuyDevCardMove, otherwise
        // prints in the informationsGUI the necessary information
        Map<ResourceType, Integer> insertedResources = countResources(this.selectResourcesHandler);
        if (verifyResourcesTargetReached(insertedResources,
                this.buyDevCardMoveHandler.getDevelopmentCard().getRequiredResources())) {
            this.gui.sendBuyDevCardMove(this.buyDevCardMoveHandler);
        } else {
            Map<ResourceType, Integer> missingResources = calculateMissingResources(insertedResources,
                    this.buyDevCardMoveHandler.getDevelopmentCard().getRequiredResources());
            String missingResourceString = convertResourcesToString(missingResources);
            this.jTextArea.setText("You selected this resources:\n"
                + "From Warehouse:\n" + selectedResourcesFromWarehouse + "From Chest:\n" + selectedResourcesFromChest
                + "You stll need this resources:\n" + missingResourceString
                + "Select the resources required from the Warehouse and/or the chest.");
        }
    }

    // verifies if yourResources has reached the target of resources
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

    // calculates the missing resources from target
    private Map<ResourceType, Integer> calculateMissingResources(Map<ResourceType, Integer> yourResources, Map<ResourceType, Integer> target) {
        Map<ResourceType, Integer> missingResources = new HashMap<>();
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

    // counts the current resources selected
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
                    insertedResources.put(type,
                            selectResourcesHandler.getResourcesFromWarehouse().get(type) +
                                    selectResourcesHandler.getResourcesFromChest().get(type));
                } else {
                    insertedResources.put(type, selectResourcesHandler.getResourcesFromWarehouse().get(type));
                }
            } else {
                if (selectResourcesHandler.getResourcesFromChest().containsKey(type)) {
                    insertedResources.put(type, selectResourcesHandler.getResourcesFromChest().get(type));
                }
            }
        }
        return insertedResources;
    }

    // Creates the string for the informationsGUI for the selected resources
    private String convertResourcesToString(Map<ResourceType, Integer> selectedResources) {
        StringBuilder selectedResourcesString = new StringBuilder();
        for (ResourceType type : selectedResources.keySet()) {
            selectedResourcesString.append(type).append(": ").append(selectedResources.get(type)).append("\n");
        }
        return selectedResourcesString.toString();
    }

    // updates the SelectResourcesHandler incrementing the current resources chosen
    public void updateSelectResourcesHandler(ResourceType resourceType, boolean isFromWarehouse) {
        if (isFromWarehouse) {
            this.selectResourcesHandler.incrementResourcesFromWarehouse(resourceType);
        } else {
            this.selectResourcesHandler.incrementResourcesFromChest(resourceType);
        }
    }

    public void addCoin() { this.resourceInHandler.addCoin(); }

    public void addStone() { this.resourceInHandler.addStone(); }

    public void addShield() { this.resourceInHandler.addShield(); }

    public void addServant() { this.resourceInHandler.addServant(); }
}
