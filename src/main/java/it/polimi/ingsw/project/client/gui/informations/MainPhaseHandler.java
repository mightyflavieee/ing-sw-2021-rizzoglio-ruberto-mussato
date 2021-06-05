package it.polimi.ingsw.project.client.gui.informations;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.listeners.informations.ActivateProductionButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.BuyDevCardButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.TakeResourcesFromMarketButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainPhaseHandler extends JInternalFrame {
    private List<JButton> mainButtons;
    private List<JButton> productionButtons;
    private JPanel mainPanel;
    private JPanel productionPanel;
    private GUI gui;

    public MainPhaseHandler(GUI gui) {
        this.gui = gui;
        this.setVisible(true);
        this.setLayout(new GridLayout(1, 1));
        createPanels();
        this.add(this.mainPanel);
        //this.add(this.productionPanel);
        //goToMainButtons();
    }

    private void createPanels() {
        createButtons();
        this.mainPanel = new JPanel();
        this.productionPanel = new JPanel();
        this.mainPanel.setVisible(true);
        this.productionPanel.setVisible(true);
        this.mainPanel.setLayout(new GridLayout(3, 1));
        this.productionPanel.setLayout(new GridLayout(8, 1));
        for (JButton button : this.mainButtons) {
            this.mainPanel.add(button);
        }
        for (JButton button : this.productionButtons) {
            this.productionPanel.add(button);
        }
    }

    private void createButtons() {
        this.mainButtons = new ArrayList<>();
        this.productionButtons = new ArrayList<>();
        // creates main buttons
        JButton buttonMarket = new JButton("Take Resources From Market");
        JButton buttonPurchase = new JButton("Buy Development Card");
        JButton buttonProduction = new JButton("Activate Production");
        // adds listeners
        buttonMarket.addActionListener(new TakeResourcesFromMarketButtonListener(this.gui));
        buttonPurchase.addActionListener(new BuyDevCardButtonListener(this.gui));
        buttonProduction.addActionListener(new ActivateProductionButtonListener(this.gui));
        // adds JButtons to attribute
        this.mainButtons.add(buttonMarket);
        this.mainButtons.add(buttonPurchase);
        this.mainButtons.add(buttonProduction);
        // creates production buttons
        JButton buttonBoardProduction = new JButton("Board Production");
        JButton buttonDevCardProduction = new JButton("Dev Card Production");
        JButton buttonLeaderCardProduction = new JButton("Leader Card Production");
        JButton buttonBoardAndDevCardProduction = new JButton("Board, Dev Card Production");
        JButton buttonBoardAndLeaderCardProduction = new JButton("Board, Leader Card Production");
        JButton buttonDevCardAndLeaderCardProduction = new JButton("Dev Card, Leader Card Production");
        JButton buttonBoardAndDevCardAndLeaderCardProduction = new JButton("Board, Dev Card, Leader Card Production");
        JButton buttonGoBack = new JButton("Go Back");
        // adds listeners
        buttonBoardProduction.addActionListener(new BoardProductionListener(this.gui));
        buttonDevCardProduction.addActionListener(new DevCardProductionListener(this.gui));
        buttonLeaderCardProduction.addActionListener(new LeaderCardProductionListener(this.gui));
        buttonBoardAndDevCardProduction.addActionListener(new BuyDevCardButtonListener(this.gui));
        buttonBoardAndLeaderCardProduction.addActionListener(new BoardLeaderCardProductionListener(this.gui));
        buttonDevCardAndLeaderCardProduction.addActionListener(new DevLeaderCardsProductionListener(this.gui));
        buttonBoardAndDevCardAndLeaderCardProduction.addActionListener(new BoardDevLeaderCardsProductionListener(this.gui));
        buttonGoBack.addActionListener(new GoBackFromProductionListener(this.gui));
        // add JButtons to attribute
        this.productionButtons.add(buttonBoardProduction);
        this.productionButtons.add(buttonDevCardProduction);
        this.productionButtons.add(buttonLeaderCardProduction);
        this.productionButtons.add(buttonBoardAndDevCardProduction);
        this.productionButtons.add(buttonBoardAndLeaderCardProduction);
        this.productionButtons.add(buttonDevCardAndLeaderCardProduction);
        this.productionButtons.add(buttonBoardAndDevCardAndLeaderCardProduction);
        this.productionButtons.add(buttonGoBack);
    }

    public void goToMainButtons() {
        this.add(this.mainPanel);
        this.remove(this.productionPanel);

        //this.productionPanel.setVisible(false);
        //this.mainPanel.setVisible(true);

        /*for (JButton button : this.productionButtons) {
            this.remove(button);
        }
        for (JButton button : this.mainButtons) {
            this.setLayout(new GridLayout(3, 1));
            this.add(button);
        }*/
    }

    public void goToProductionButtons() {
        this.add(this.productionPanel);
        this.remove(this.mainPanel);

        //this.mainPanel.setVisible(false);
        //this.productionPanel.setVisible(true);
    }
}
