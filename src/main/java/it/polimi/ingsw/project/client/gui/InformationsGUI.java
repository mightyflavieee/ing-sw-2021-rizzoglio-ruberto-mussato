package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.market.ResourceInHandlerGUI;
import it.polimi.ingsw.project.model.TurnPhase;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
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
    private SelectResourcesHandler selectResourcesHandler;
    private BuyDevCardMoveHandler buyDevCardMoveHandler;
    private ProductionMoveBuilder productionMoveHandler;
    private GUI gui;

    public InformationsGUI(GUI gui, TurnPhase turnPhase) {
        this.gui = gui;
        this.setTitle("Informations");
        this.setLayout(new FlowLayout());
        this.jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        this.add(this.jTextArea);
        this.turnPhase = turnPhase;
        //this.resourceInHandler = new ResourceInHandlerGUI();
        this.refresh();
        this.setVisible(true);
        this.pack();
    }

    public JTextArea getjTextArea() { return this.jTextArea; }

    public void refresh() {
        switch (turnPhase) {
            case InitialPhase:
                if(this.phaseFrame!=null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to go on",this.gui);
                this.add(this.phaseFrame);
                break;
            case EndPhase:
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
                this.jTextArea.setText("You must choose and perform one of the following actions:" +
                        "\nTake Resources from the Market" +
                        "\nBuy one Development Card" +
                        "\nActivate the Production");
                break;
            case WaitPhase:
            default:
                if(this.phaseFrame != null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(true);
                this.jTextArea.setText("It's not your turn");
                break;
        }
    }

    public void showMarketInformations(Boolean hasfaith){
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

    //todo da finire
    public void createSelectResourcesHandlerForProduction(String devCardID, String leaderCardID) {
        this.productionMoveHandler = new ProductionMoveBuilder();
        if (devCardID != null) {
            this.productionMoveHandler.setDevCardID(devCardID);
            if (leaderCardID != null) {
                this.productionMoveHandler.setLeaderCardID(leaderCardID);
            }
        }

        this.selectResourcesHandler = new SelectResourcesHandler();

        // mostrare risorse richieste produzione
    }

    public void createSelectResourcesHandlerForPurchase(DevelopmentCard developmentCard) {
        this.buyDevCardMoveHandler = new BuyDevCardMoveHandler();
        this.buyDevCardMoveHandler.setDevelopmentCard(developmentCard);
        this.selectResourcesHandler = new SelectResourcesHandler();
        showDevCardPurchaseInfo();
    }

    public void showDevCardPurchaseInfo() {
        // creates the string for the informationsGUI
        StringBuilder selectedResourcesFromWarehouse = new StringBuilder();
        StringBuilder selectedResourcesFromChest = new StringBuilder();
        for (ResourceType type : this.selectResourcesHandler.getResourcesFromWarehouse().keySet()) {
            selectedResourcesFromWarehouse.append(type).append(": ")
                    .append(this.selectResourcesHandler.getResourcesFromWarehouse().get(type)).append("\n");
        }
        for (ResourceType type : this.selectResourcesHandler.getResourcesFromChest().keySet()) {
            selectedResourcesFromChest.append(type).append(": ")
                    .append(this.selectResourcesHandler.getResourcesFromWarehouse().get(type)).append("\n");
        }
        Map<ResourceType, Integer> insertedResources = countResources(this.selectResourcesHandler);
        if (verifyResourcesTargetReached(insertedResources,
                this.buyDevCardMoveHandler.getDevelopmentCard().getRequiredResources())) {
            this.gui.sendBuyDevCardMove(this.buyDevCardMoveHandler);
        } else {
            Map<ResourceType, Integer> missingResources = calculateMissingResources(insertedResources,
                    this.buyDevCardMoveHandler.getDevelopmentCard().getRequiredResources());
            StringBuilder missingResourceString = new StringBuilder();
            for (ResourceType type : missingResources.keySet()) {
                missingResourceString.append(type).append(": ")
                        .append(missingResources.get(type)).append("\n");
            }
            this.jTextArea.setText("You selected this resources:\n"
                + "From Warehouse:\n" + selectedResourcesFromWarehouse + "From Chest:\n" + selectedResourcesFromChest
                + "You stll need this resources:\n" + missingResourceString
                + "Select the resources required from the Warehouse and/or the chest.");
        }
    }

    private boolean verifyResourcesTargetReached(Map<ResourceType, Integer> yourResources, Map<ResourceType, Integer> target) {
        for (ResourceType type : target.keySet()) {
            if (yourResources.containsKey(type)) {
                if (!yourResources.get(type).equals(target.get(type))) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private Map<ResourceType, Integer> calculateMissingResources(Map<ResourceType, Integer> yourResources, Map<ResourceType, Integer> target) {
        Map<ResourceType, Integer> missingResources = new HashMap<>();
        for (ResourceType type : target.keySet()) {
            if (!yourResources.containsKey(type)) {
                missingResources.put(type, target.get(type));
            } else {
                if (!yourResources.get(type).equals(target.get(type))) {
                    missingResources.put(type, target.get(type) - yourResources.get(type));
                }
            }
        }
        return missingResources;
    }

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

    public void updateSelectResourcesHandler(ResourceType resourceType, boolean isFromWarehouse) {
        if (isFromWarehouse) {
            this.selectResourcesHandler.incrementResourcesFromWarehouse(resourceType);
        } else {
            this.selectResourcesHandler.incrementResourcesFromChest(resourceType);
        }
    }

    public void resetSelectResourcesHandler() {
        this.selectResourcesHandler = null;
    }

    public void resetBuyDevCardMoveHandler() {
        this.buyDevCardMoveHandler = null;
    }

    public void resetProductionMoveHandler() {
        this.productionMoveHandler = null;
    }

    public void addCoin() { this.resourceInHandler.addCoin(); }
    public void addStone() {
        this.resourceInHandler.addStone();
    }
    public void addShield() {
        this.resourceInHandler.addShield();
    }
    public void addServant() {
        this.resourceInHandler.addServant();
    }
}
