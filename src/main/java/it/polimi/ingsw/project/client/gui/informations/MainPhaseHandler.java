package it.polimi.ingsw.project.client.gui.informations;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.listeners.informations.*;
import it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove.CenterPositionPurchaseListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove.GoBackFromPurchaseListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove.LeftPositionPurchaseListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.buydevcardmove.RightPositionPurchaseListener;
import it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.*;
import it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.boardproduction.*;
import it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.leaderproduction.*;
import it.polimi.ingsw.project.client.gui.listeners.informations.productionmove.productiontypes.*;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainPhaseHandler extends JInternalFrame {
    private static final String MAINPHASESPANEL = "Main Phase";
    private static final String PRODUCTIONPANEL = "Production Move";
    private static final String BUYDEVCARDMOVE = "Buy Dev Card Move";
    private static final String LEADERMANUFACTURINGPANEL = "Leader Card Manufacturing";
    private static final String BOARDMANUFACTURINGPANEL = "Board Manufacturing";
    private static final String BOARDREQUIREDRESOURCESPANEL = "Board Required Resources";
    private static final String ABORTMOVEPANEL = "Abort Move";
    private static final String TRANSMUTATIONPANEL = "Select Transmutation Perk";
    private List<JButton> mainPhasesButtons;
    private List<JButton> buyDevCardButtons;
    private List<JButton> productionButtons;
    private List<JButton> leaderManufacturingButtons;
    private List<JButton> boardManufacturingButtons;
    private List<JButton> boardRequiredResourcesButtons;
    private List<JButton> transmutationButtons;
    private JButton abortMoveButton;
    private JPanel mainPanel;
    private CardLayout mainPanelLayout;
    private JPanel mainPhasesPanel;
    private JPanel buyDevCardPanel;
    private JPanel productionPanel;
    private JPanel leaderManufacturingPanel;
    private JPanel boardManufacturingPanel;
    private JPanel boardRequiredResourcesPanel;
    private JPanel abortMovePanel;
    private JPanel transmutationPanel;
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
        this.mainPanel.add(LEADERMANUFACTURINGPANEL, this.leaderManufacturingPanel);
        this.mainPanel.add(BOARDMANUFACTURINGPANEL, this.boardManufacturingPanel);
        this.mainPanel.add(BOARDREQUIREDRESOURCESPANEL, this.boardRequiredResourcesPanel);
        this.mainPanel.add(TRANSMUTATIONPANEL,this.transmutationPanel);
        this.mainPanel.add(ABORTMOVEPANEL, this.abortMovePanel);
        this.mainPanelLayout.show(this.mainPanel, MAINPHASESPANEL);
        this.add(this.mainPanel);
    }

    private void createPanels() {
        createButtons();
        this.mainPhasesPanel = new JPanel();
        this.buyDevCardPanel = new JPanel();
        this.productionPanel = new JPanel();
        this.leaderManufacturingPanel = new JPanel();
        this.boardManufacturingPanel = new JPanel();
        this.boardRequiredResourcesPanel = new JPanel();
        JPanel boardReqResHelperPanel = new JPanel();
        this.transmutationPanel = new JPanel();
        this.abortMovePanel = new JPanel();
        this.mainPhasesPanel.setVisible(true);
        this.buyDevCardPanel.setVisible(true);
        this.productionPanel.setVisible(true);
        this.leaderManufacturingPanel.setVisible(true);
        this.boardManufacturingPanel.setVisible(true);
        this.boardRequiredResourcesPanel.setVisible(true);
        boardReqResHelperPanel.setVisible(true);
        this.transmutationPanel.setVisible(true);
        this.abortMovePanel.setVisible(true);
        this.mainPhasesPanel.setLayout(new GridLayout(3, 1));
        this.buyDevCardPanel.setLayout(new GridLayout(4, 1));
        this.productionPanel.setLayout(new GridLayout(8, 1));
        this.leaderManufacturingPanel.setLayout(new GridLayout(5, 1));
        this.boardManufacturingPanel.setLayout(new GridLayout(5, 1));
        this.boardRequiredResourcesPanel.setLayout(new BorderLayout());
        boardReqResHelperPanel.setLayout(new GridLayout(4, 2));
        this.transmutationPanel.setLayout(new GridLayout(2,1));
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
        for (JButton button : this.leaderManufacturingButtons) {
            this.leaderManufacturingPanel.add(button);
        }
        for (JButton button : this.boardManufacturingButtons) {
            this.boardManufacturingPanel.add(button);
        }
        for (JButton button : this.boardRequiredResourcesButtons) {
            if (button.getText().equals("Go Back")) {
                this.boardRequiredResourcesPanel.add(button, BorderLayout.SOUTH);
            } else {
                boardReqResHelperPanel.add(button);
            }
        }
        this.boardRequiredResourcesPanel.add(boardReqResHelperPanel, BorderLayout.CENTER);
        for(int i = 0; i < this.transmutationButtons.size(); i++){
            this.transmutationPanel.add(this.transmutationButtons.get(i));
        }
        this.abortMovePanel.add(this.abortMoveButton);
    }

    private void createButtons() {
        this.mainPhasesButtons = new ArrayList<>();
        this.buyDevCardButtons = new ArrayList<>();
        this.productionButtons = new ArrayList<>();
        this.leaderManufacturingButtons = new ArrayList<>();
        this.boardManufacturingButtons = new ArrayList<>();
        this.boardRequiredResourcesButtons = new ArrayList<>();
        this.transmutationButtons = new ArrayList<>();
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
        buttonBoardAndDevCardProduction.addActionListener(new BoardDevCardProductionListener(this.gui));
        buttonBoardAndLeaderCardProduction.addActionListener(new BoardLeaderCardProductionListener(this.gui));
        buttonDevCardAndLeaderCardProduction.addActionListener(new DevLeaderCardsProductionListener(this.gui));
        buttonBoardAndDevCardAndLeaderCardProduction.addActionListener(new BoardDevLeaderCardsProdListener(this.gui));
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
        // creates Leader manufacturing buttons
        JButton buttonCoinLeader = new JButton();
        JButton buttonServantLeader = new JButton();
        JButton buttonShieldLeader = new JButton();
        JButton buttonStoneLeader = new JButton();
        JButton buttonGoBackLeaderManufacturing = new JButton("Go Back");
        // sets resources icons
        buttonCoinLeader.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Coin.png")
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonServantLeader.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Servant.png")
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonShieldLeader.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Shield.png")
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonStoneLeader.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Stone.png")
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        // adds listeners
        buttonCoinLeader.addActionListener(new CoinLeaderProductionListener(this.gui));
        buttonServantLeader.addActionListener(new ServantLeaderProductionListener(this.gui));
        buttonShieldLeader.addActionListener(new ShieldLeaderProductionListener(this.gui));
        buttonStoneLeader.addActionListener(new StoneLeaderProductionListener(this.gui));
        buttonGoBackLeaderManufacturing.addActionListener(new GoBackLeaderProductionListener(this.gui));
        // adds JButtons to attribute
        this.leaderManufacturingButtons.add(buttonCoinLeader);
        this.leaderManufacturingButtons.add(buttonServantLeader);
        this.leaderManufacturingButtons.add(buttonShieldLeader);
        this.leaderManufacturingButtons.add(buttonStoneLeader);
        this.leaderManufacturingButtons.add(buttonGoBackLeaderManufacturing);
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
        buttonCoin.addActionListener(new CoinBoardProductionListener(this.gui, true));
        buttonServant.addActionListener(new ServantBoardProductionListener(this.gui, true));
        buttonShield.addActionListener(new ShieldBoardProductionListener(this.gui, true));
        buttonStone.addActionListener(new StoneBoardProductionListener(this.gui, true));
        buttonGoBackBoardManufacturing.addActionListener(new GoBackFromBoardProduction(this.gui, true));
        // adds JButtons to attribute
        this.boardManufacturingButtons.add(buttonCoin);
        this.boardManufacturingButtons.add(buttonServant);
        this.boardManufacturingButtons.add(buttonShield);
        this.boardManufacturingButtons.add(buttonStone);
        this.boardManufacturingButtons.add(buttonGoBackBoardManufacturing);
        // creates Board required resources buttons
        JButton buttonCoin1 = new JButton();
        JButton buttonCoin2 = new JButton();
        JButton buttonServant1 = new JButton();
        JButton buttonServant2 = new JButton();
        JButton buttonShield1 = new JButton();
        JButton buttonShield2 = new JButton();
        JButton buttonStone1 = new JButton();
        JButton buttonStone2 = new JButton();
        JButton buttonGoBack = new JButton("Go Back");
        // sets resources icons
        buttonCoin1.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Coin.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonCoin2.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Coin.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonServant1.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Servant.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonServant2.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Servant.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonShield1.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Shield.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonShield2.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Shield.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonStone1.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Stone.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        buttonStone2.setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/Stone.png")
                .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        // adds listeners
        buttonCoin1.addActionListener(new CoinBoardProductionListener(this.gui, false));
        buttonCoin2.addActionListener(new CoinBoardProductionListener(this.gui, false));
        buttonServant1.addActionListener(new ServantBoardProductionListener(this.gui, false));
        buttonServant2.addActionListener(new ServantBoardProductionListener(this.gui, false));
        buttonShield1.addActionListener(new ShieldBoardProductionListener(this.gui, false));
        buttonShield2.addActionListener(new ShieldBoardProductionListener(this.gui, false));
        buttonStone1.addActionListener(new StoneBoardProductionListener(this.gui, false));
        buttonStone2.addActionListener(new StoneBoardProductionListener(this.gui, false));
        buttonGoBack.addActionListener(new GoBackFromBoardProduction(this.gui, false));
        // adds JButtons to attribute
        this.boardRequiredResourcesButtons.add(buttonCoin1);
        this.boardRequiredResourcesButtons.add(buttonCoin2);
        this.boardRequiredResourcesButtons.add(buttonServant1);
        this.boardRequiredResourcesButtons.add(buttonServant2);
        this.boardRequiredResourcesButtons.add(buttonShield1);
        this.boardRequiredResourcesButtons.add(buttonShield2);
        this.boardRequiredResourcesButtons.add(buttonStone1);
        this.boardRequiredResourcesButtons.add(buttonStone2);
        this.boardRequiredResourcesButtons.add(buttonGoBack);
        // creates Transmutation Perk Selection buttons
        JButton transmutationButton1 = new JButton();
        JButton transmutationButton2 = new JButton();
        // adds listeners
        transmutationButton1.addActionListener(new TransmutationSelectorListener(transmutationButton1,gui));
        transmutationButton2.addActionListener(new TransmutationSelectorListener(transmutationButton2,gui));
        // adds JButtons to attribute
        this.transmutationButtons.add(transmutationButton1);
        this.transmutationButtons.add(transmutationButton2);
        // creates Abort Move button
        this.abortMoveButton = new JButton("Abort Move");
        // adds listeners
        this.abortMoveButton.addActionListener(new AbortMoveListener(this.gui));
    }

    public void goToMainButtons() {
        this.mainPanelLayout.show(this.mainPanel, MAINPHASESPANEL);
        this.gui.getInformationsGUI().getjTextArea().setText("You must choose and perform one of the following actions:");
    }

    public void goToBuyDevCardButtons() {
        this.mainPanelLayout.show(this.mainPanel, BUYDEVCARDMOVE);
    }

    public void goToProductionButtons() {
        this.mainPanelLayout.show(this.mainPanel, PRODUCTIONPANEL);
    }

    public void goToLeaderProductionButtons() { this.mainPanelLayout.show(this.mainPanel, LEADERMANUFACTURINGPANEL); }

    public void goToBoardProductionButtons() { this.mainPanelLayout.show(this.mainPanel, BOARDMANUFACTURINGPANEL); }

    public void goToBoardRequiredResourcesButtons() { this.mainPanelLayout.show(this.mainPanel, BOARDREQUIREDRESOURCESPANEL); }

    public void goToTransmutationPanel() {
        List<ResourceType> transmutationPerks = this.gui.getTransmutationPerks();
        this.transmutationButtons.get(0).setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/" + transmutationPerks.get(0) + ".png")
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        this.transmutationButtons.get(1).setIcon(new ImageIcon(
                new ImageIcon("src/main/resources/resourcetype/" + transmutationPerks.get(1) + ".png")
                        .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        this.mainPanelLayout.show(this.mainPanel, TRANSMUTATIONPANEL);
    }

    public void goToAbortMovePanel() { this.mainPanelLayout.show(this.mainPanel, ABORTMOVEPANEL); }
}
