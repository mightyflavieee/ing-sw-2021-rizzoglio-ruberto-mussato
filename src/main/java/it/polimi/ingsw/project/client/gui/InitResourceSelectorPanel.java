package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.listeners.newgame.InitResourceSelectorActionListener;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InitResourceSelectorPanel extends JInternalFrame {
    private int coinInt, shieldInt, stoneInt, servantInt;
        private final ClientGUI clientGUI;
        private final int numberOfResourcesToChoose;
        private final NewGameHandler newGameHandler;
    public InitResourceSelectorPanel(ClientGUI clientGUI, NewGameHandler newGameHandler, Integer numberOfResourcesToChoose) {
        this.setTitle("Select " + numberOfResourcesToChoose + " resources");
        this.clientGUI = clientGUI;
        this.setLayout(new GridLayout(2,2));
        ResourceButton coinButton = new ResourceButton(ResourceType.Coin);
        ResourceButton shieldButton = new ResourceButton(ResourceType.Shield);
        ResourceButton stoneButton = new ResourceButton(ResourceType.Stone);
        ResourceButton servantButton = new ResourceButton(ResourceType.Servant);
        this.coinInt = 0;
        this.shieldInt = 0;
        this.stoneInt = 0;
        this.servantInt = 0;
        coinButton.addActionListener(new InitResourceSelectorActionListener(ResourceType.Coin,this));
        shieldButton.addActionListener(new InitResourceSelectorActionListener(ResourceType.Shield,this));
        servantButton.addActionListener(new InitResourceSelectorActionListener(ResourceType.Servant,this));
        stoneButton.addActionListener(new InitResourceSelectorActionListener(ResourceType.Stone,this));
        this.add(coinButton);
        this.add(shieldButton);
        this.add(stoneButton);
        this.add(servantButton);
        this.numberOfResourcesToChoose = numberOfResourcesToChoose;
        this.newGameHandler = newGameHandler;

    }

    public void addResource(ResourceType resourceType) {
        switch (resourceType){
            case Coin:
                coinInt++;
                break;
            case Stone:
                stoneInt++;
                break;
            case Shield:
                shieldInt++;
                break;
            case Servant:
                servantInt++;
                break;
            default:
                break;
        }
        if(coinInt + stoneInt + shieldInt + servantInt == this.numberOfResourcesToChoose){
            List<ResourceType> resourceTypeList = new ArrayList<>();
            for(int i = 0; i < coinInt; i++){
                resourceTypeList.add(ResourceType.Coin);
            }
            for(int i = 0; i < stoneInt; i++){
                resourceTypeList.add(ResourceType.Stone);
            }
            for(int i = 0; i < shieldInt; i++){
                resourceTypeList.add(ResourceType.Shield);
            }
            for(int i = 0; i < servantInt; i++){
                resourceTypeList.add(ResourceType.Servant);
            }
            clientGUI.sendListOfChosenResources(resourceTypeList);
            this.newGameHandler.goToWaitingRoom(clientGUI.getGameId());
        }
    }


}
