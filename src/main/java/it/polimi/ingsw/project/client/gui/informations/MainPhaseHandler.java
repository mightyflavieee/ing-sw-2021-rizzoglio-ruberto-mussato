package it.polimi.ingsw.project.client.gui.informations;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.listeners.ServantHandListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.AbortMoveListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.ActivateProductionButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.BuyDevCardButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.TakeResourcesFromMarketButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove.CenterPositionPurchaseListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove.GoBackFromPurchaseListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove.LeftPositionPurchaseListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove.RightPositionPurchaseListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.*;
import it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.boardproduction.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainPhaseHandler extends JInternalFrame {
    private static final String MAINPHASESPANEL = "Main Phase";
    private static final String PRODUCTIONPANEL = "Production Move";
    private static final String BUYDEVCARDMOVE = "Buy Dev Card Move";
    private static final String BOARDMANUFACTURINGPANEL = "Board Manufacturing";
    private static final String ABORTMOVEPANEL = "Abort Move";
    private List<JButton> mainPhasesButtons;
    private List<JButton> buyDevCardButtons;
    private List<JButton> productionButtons;
    private List<JButton> boardManufacturingButtons;
    private JButton abortMoveButton;
    private JPanel mainPanel;
    private CardLayout mainPanelLayout;
    private JPanel mainPhasesPanel;
    private JPanel buyDevCardPanel;
    private JPanel productionPanel;
    private JPanel boardManufacturingPanel;
    private JPanel abortMovePanel;
    private GUI gui;


    public MainPhaseHandler(GUI gui) {
        this.gui = gui;
        this.setVisible(true);
        this.setLayout(new GridLayout(1, 1));
        createPanels();
        this.mainPanelLayout = new CardLayout();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(this.mainPanelLayout);
        this.mainPanel.add(MAINPHASESPANEL, this.mainPhasesPanel);
        this.mainPanel.add(BUYDEVCARDMOVE, this.buyDevCardPanel);
        this.mainPanel.add(PRODUCTIONPANEL, this.productionPanel);
        this.mainPanel.add(BOARDMANUFACTURINGPANEL, this.boardManufacturingPanel);
        this.mainPanel.add(ABORTMOVEPANEL, this.abortMovePanel);
        this.mainPanelLayout.show(this.mainPanel, MAINPHASESPANEL);
        this.add(this.mainPanel);
    }

    private void createPanels() {
        createButtons();
        this.mainPhasesPanel = new JPanel();
        this.buyDevCardPanel = new JPanel();
        this.productionPanel = new JPanel();
        this.boardManufacturingPanel = new JPanel();
        this.abortMovePanel = new JPanel();
        this.mainPhasesPanel.setVisible(true);
        this.buyDevCardPanel.setVisible(true);
        this.productionPanel.setVisible(true);
        this.boardManufacturingPanel.setVisible(true);
        this.abortMovePanel.setVisible(true);
        this.mainPhasesPanel.setLayout(new GridLayout(3, 1));
        this.buyDevCardPanel.setLayout(new GridLayout(4, 1));
        this.productionPanel.setLayout(new GridLayout(8, 1));
        this.boardManufacturingPanel.setLayout(new GridLayout(5, 1));
        this.abortMovePanel.setLayout(new BorderLayout());
        for (JButton button : this.mainPhasesButtons) {
            this.mainPhasesPanel.add(button);
        }
        for (JButton button : this.buyDevCardButtons) {
            this.buyDevCardPanel.add(button);
        }
        for (JButton button : this.productionButtons) {
            this.productionPanel.add(button);
        }
        for (JButton button : this.boardManufacturingButtons) {
            this.boardManufacturingPanel.add(button);
        }
        this.abortMovePanel.add(this.abortMoveButton);
    }

    private void createButtons() {
        this.mainPhasesButtons = new ArrayList<>();
        this.buyDevCardButtons = new ArrayList<>();
        this.productionButtons = new ArrayList<>();
        this.boardManufacturingButtons = new ArrayList<>();
        // creates main buttons
        JButton buttonMarket = new JButton("Take Resources From Market");
        JButton buttonPurchase = new JButton("Buy Development Card");
        JButton buttonProduction = new JButton("Activate Production");
        // adds listeners
        buttonMarket.addActionListener(new TakeResourcesFromMarketButtonListener(this.gui));
        buttonPurchase.addActionListener(new BuyDevCardButtonListener(this.gui));
        buttonProduction.addActionListener(new ActivateProductionButtonListener(this.gui));
        // adds JButtons to attribute
        this.mainPhasesButtons.add(buttonMarket);
        this.mainPhasesButtons.add(buttonPurchase);
        this.mainPhasesButtons.add(buttonProduction);
        // creates buy dev card buttons
        JButton buttonLeftPosition = new JButton("Left Position");
        JButton buttonCenterPosition = new JButton("Center Position");
        JButton buttonRightPosition = new JButton("Right Position");
        JButton buttonGoBackBuyDevCard = new JButton("Go Back");
        // adds listeners
        buttonLeftPosition.addActionListener(new LeftPositionPurchaseListener(this.gui));
        buttonCenterPosition.addActionListener(new CenterPositionPurchaseListener(this.gui));
        buttonRightPosition.addActionListener(new RightPositionPurchaseListener(this.gui));
        buttonGoBackBuyDevCard.addActionListener(new GoBackFromPurchaseListener(this.gui));
        // adds JButtons to attribute
        this.buyDevCardButtons.add(buttonLeftPosition);
        this.buyDevCardButtons.add(buttonCenterPosition);
        this.buyDevCardButtons.add(buttonRightPosition);
        this.buyDevCardButtons.add(buttonGoBackBuyDevCard);
        // creates production buttons
        JButton buttonBoardProduction = new JButton("Board Production");
        JButton buttonDevCardProduction = new JButton("Dev Card Production");
        JButton buttonLeaderCardProduction = new JButton("Leader Card Production");
        JButton buttonBoardAndDevCardProduction = new JButton("Board, Dev Card Production");
        JButton buttonBoardAndLeaderCardProduction = new JButton("Board, Leader Card Production");
        JButton buttonDevCardAndLeaderCardProduction = new JButton("Dev Card, Leader Card Production");
        JButton buttonBoardAndDevCardAndLeaderCardProduction = new JButton("Board, Dev Card, Leader Card Production");
        JButton buttonGoBackProduction = new JButton("Go Back");
        // adds listeners
        buttonBoardProduction.addActionListener(new BoardProductionListener(this.gui));
        buttonDevCardProduction.addActionListener(new DevCardProductionListener(this.gui));
        buttonLeaderCardProduction.addActionListener(new LeaderCardProductionListener(this.gui));
        buttonBoardAndDevCardProduction.addActionListener(new BuyDevCardButtonListener(this.gui));
        buttonBoardAndLeaderCardProduction.addActionListener(new BoardLeaderCardProductionListener(this.gui));
        buttonDevCardAndLeaderCardProduction.addActionListener(new DevLeaderCardsProductionListener(this.gui));
        buttonBoardAndDevCardAndLeaderCardProduction.addActionListener(new BoardDevLeaderCardsProductionListener(this.gui));
        buttonGoBackProduction.addActionListener(new GoBackFromProductionListener(this.gui));
        // adds JButtons to attribute
        this.productionButtons.add(buttonBoardProduction);
        this.productionButtons.add(buttonDevCardProduction);
        this.productionButtons.add(buttonLeaderCardProduction);
        this.productionButtons.add(buttonBoardAndDevCardProduction);
        this.productionButtons.add(buttonBoardAndLeaderCardProduction);
        this.productionButtons.add(buttonDevCardAndLeaderCardProduction);
        this.productionButtons.add(buttonBoardAndDevCardAndLeaderCardProduction);
        this.productionButtons.add(buttonGoBackProduction);
        // creates Board manufacturing buttons
        JButton buttonCoin = new JButton();
        JButton buttonServant = new JButton();
        JButton buttonShield = new JButton();
        JButton buttonStone = new JButton();
        JButton buttonGoBackBoardManufacturing = new JButton("Go Back");
        // sets resources icons
        buttonCoin.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Coin.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonServant.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Servant.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonShield.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Shield.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonStone.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Stone.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        // adds listeners
        buttonCoin.addActionListener(new CoinBoardProductionListener(this.gui));
        buttonServant.addActionListener(new ServantBoardProductionListener(this.gui));
        buttonShield.addActionListener(new ShieldBoardProductionListener(this.gui));
        buttonStone.addActionListener(new StoneBoardProductionListener(this.gui));
        buttonGoBackBoardManufacturing.addActionListener(new GoBackFromBoardProduction(this.gui));
        // adds JButtons to attribute
        this.boardManufacturingButtons.add(buttonCoin);
        this.boardManufacturingButtons.add(buttonServant);
        this.boardManufacturingButtons.add(buttonShield);
        this.boardManufacturingButtons.add(buttonStone);
        this.boardManufacturingButtons.add(buttonGoBackBoardManufacturing);
        // creates Abort Move button
        this.abortMoveButton = new JButton("Abort Move");
        // adds listeners
        this.abortMoveButton.addActionListener(new AbortMoveListener(this.gui));
    }

    public void goToMainButtons() {
        this.mainPanelLayout.show(this.mainPanel, MAINPHASESPANEL);
    }

    public void goToBuyDevCardButtons() {
        this.mainPanelLayout.show(this.mainPanel, BUYDEVCARDMOVE);
    }

    public void goToProductionButtons() {
        this.mainPanelLayout.show(this.mainPanel, PRODUCTIONPANEL);
    }

    public void goToBoardProductionButtons() { this.mainPanelLayout.show(this.mainPanel, BOARDMANUFACTURINGPANEL); }

    public void goToAbortMovePanel() { this.mainPanelLayout.show(this.mainPanel, ABORTMOVEPANEL); }
}
