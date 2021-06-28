package it.polimi.ingsw.project.client.gui.market;

import it.polimi.ingsw.project.client.TakeMarketResourceBuilder;
import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.listeners.market.CoinHandListener;
import it.polimi.ingsw.project.client.gui.listeners.market.ServantHandListener;
import it.polimi.ingsw.project.client.gui.listeners.market.ShieldHandListener;
import it.polimi.ingsw.project.client.gui.listeners.market.StoneHandListener;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * it shows the resources that you collected from the market during the market move
 */
public class ResourceInHandGUI extends JInternalFrame {
    private final JButton coinButton;
    private final JButton stoneButton;
    private final JButton shieldButton;
    private final JButton servantButton;
    private final JLabel coinLabel;
    private final JLabel stoneLabel;
    private final JLabel shieldLabel;
    private final JLabel servantLabel;
    private int coinInt, stoneInt, shieldInt, servantInt;
    private final TakeMarketResourceBuilder takeMarketResourceBuilder;
    public ResourceInHandGUI(InformationsGUI informationsGUI, TakeMarketResourceBuilder takeMarketResourceBuilder) {
        this.setTitle("Resources in Hand");
        this.setLayout(new GridLayout(2,4));
        this.coinButton = new JButton();
        this.coinButton.setIcon(Utils.readIcon("resourcetype/Coin.png",30,30));
        this.add(coinButton);
        this.coinButton.addActionListener(new CoinHandListener(this,informationsGUI));
        this.stoneButton = new JButton();
        this.stoneButton.setIcon(Utils.readIcon("resourcetype/Stone.png",30,30));
        this.add(stoneButton);
        this.stoneButton.addActionListener(new StoneHandListener(this,informationsGUI));
        this.shieldButton = new JButton();
        this.shieldButton.setIcon(Utils.readIcon("resourcetype/Shield.png",30,30));
        this.add(shieldButton);
        this.shieldButton.addActionListener(new ShieldHandListener(this,informationsGUI));
        this.servantButton = new JButton();
        this.servantButton.setIcon(Utils.readIcon("resourcetype/Servant.png",30,30));
        this.add(servantButton);
        this.servantButton.addActionListener(new ServantHandListener(this,informationsGUI));
        this.coinLabel = new JLabel(String.valueOf(0));
        this.add(coinLabel);
        this.stoneLabel = new JLabel(String.valueOf(0));
        this.add(stoneLabel);
        this.shieldLabel = new JLabel(String.valueOf(0));
        this.add(shieldLabel);
        this.servantLabel = new JLabel(String.valueOf(0));
        this.add(servantLabel);
        this.coinInt = 0;
        this.stoneInt = 0;
        this.shieldInt = 0;
        this.servantInt = 0;
        this.coinButton.setEnabled(false);
        this.stoneButton.setEnabled(false);
        this.shieldButton.setEnabled(false);
        this.servantButton.setEnabled(false);
        this.setVisible(true);
        this.pack();
        this.takeMarketResourceBuilder = takeMarketResourceBuilder;
    }

    /**
     * it is used to set the local model of the resources that you collected from the market move
     */
    public void refresh(List<Resource> resourceList) {
        int coin = 0, stone = 0, shield = 0, servant = 0;
        ResourceType type;
        for (Resource resource : resourceList) {
            type = resource.getType();
            switch (type) {
                case Coin:
                    coin++;
                    break;
                case Stone:
                    stone++;
                    break;
                case Shield:
                    shield++;
                    break;
                case Servant:
                    servant++;
                    break;
                default:
                    break;
            }

        }
        this.coinInt = coin;
        this.stoneInt = stone;
        this.shieldInt = shield;
        this.servantInt = servant;
        this.refresh();

    }

    /**
     * shows the collected resources based on the local model and enables all the button for the selection
     */
    public void refresh(){
        this.coinLabel.setText(String.valueOf(this.coinInt));
        this.stoneLabel.setText(String.valueOf(this.stoneInt));
        this.shieldLabel.setText(String.valueOf(this.shieldInt));
        this.servantLabel.setText(String.valueOf(this.servantInt));
        this.coinButton.setEnabled(this.coinInt != 0);
        this.stoneButton.setEnabled(this.stoneInt != 0);
        this.shieldButton.setEnabled(this.shieldInt != 0);
        this.servantButton.setEnabled(this.servantInt != 0);
        takeMarketResourceBuilder.setMarketClear(coinInt + stoneInt + servantInt + shieldInt == 0);
    }

    /**
     * decreases the number of coins, shows this change and disables all the other buttons in order to select only one type of resource per time
     */
    public void decreaseCoin(){
        this.coinInt --;
        refresh();
        this.stoneButton.setEnabled(false);
        this.shieldButton.setEnabled(false);
        this.servantButton.setEnabled(false);

    }

    /**
     * decreases the number of stones, shows this change and disables all the other buttons in order to select only one type of resource per time
     */
    public void decreaseStone() {
        this.stoneInt --;
        refresh();
        this.coinButton.setEnabled(false);
        this.shieldButton.setEnabled(false);
        this.servantButton.setEnabled(false);
    }

    /**
     * decreases the number of shields, shows this change and disables all the other buttons in order to select only one type of resource per time
     */
    public void decreaseShield() {
        this.shieldInt --;
        refresh();
        this.coinButton.setEnabled(false);
        this.stoneButton.setEnabled(false);
        this.servantButton.setEnabled(false);
    }

    /**
     * decreases the number of servants, shows this change and disables all the other buttons in order to select only one type of resource per time
     */
    public void decreaseServant() {
        this.servantInt --;
        refresh();
        this.coinButton.setEnabled(false);
        this.stoneButton.setEnabled(false);
        this.shieldButton.setEnabled(false);
    }

    /**
     * adds back the resources that have been deselected during the market move and updates the visual representation
     */
    public void addResource(ResourceType resourceType, int n){
        switch (resourceType) {
            case Coin:
                this.coinInt = this.coinInt + n;
                break;
            case Stone:
                this.stoneInt = this.stoneInt + n;
                break;
            case Shield:
                this.shieldInt = this.shieldInt + n;
                break;
            case Servant:
                this.servantInt = this.servantInt + n;
                break;
            default:
                break;
        }
        this.refresh();
    }
}
