package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.leadercardcontainer.LeaderCardChoserGUI;
import it.polimi.ingsw.project.client.gui.listeners.newgame.GoBackFromJoinListener;
import it.polimi.ingsw.project.client.gui.listeners.newgame.JoinGameListener;
import it.polimi.ingsw.project.client.gui.listeners.newgame.SelectGameTypeListener;
import it.polimi.ingsw.project.client.gui.listeners.newgame.SelectNicknameListener;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NewGameHandler extends JPanel {
    private static final String SELECTNICKNAME = "SELECT_NICKNAME";
    private static final String SELECTTYPEOFGAME = "SELECT_TYPE_OF_GAME";
    private static final String SELECTJOINGAMEID = "SELECT_JOIN_GAME_ID";
    private static final String WAITINGROOM = "WAITING_ROOM";
    private static final String LEADERCARDCHOOSER = "LEADERCARDCHOOSER";
    private static final String RESOURCESELECTOR = "RESOURCESELECTOR";
    private JPanel mainPanel;
    private CardLayout mainLayout;

    private JLabel selectNicknameTitle;
    private JLabel selectNicknameLabel;
    private JTextField selectNicknameTextField;
    private JButton selectNicknameButton;

    private JLabel selectGameTypeTitle;
    private JPanel selectGameTypeButtonGroupPanel;
    private ButtonGroup selectGameTypeRadioButtonGroup;
    private List<JRadioButton> selectGameTypeRadioButtons;
    private List<JButton> selectGameTypeButtons;

    private JLabel selectJoinGameIDTitle;
    private JLabel selectJoinGameIDLabel;
    private JTextField selectJoinGameIDTextField;
    private List<JButton> selectJoinGameIDButtons;

    private JLabel waitingRoomTitle;
    private JLabel waitingRoomLabel;

    private JPanel selectNicknamePanel;
    private JPanel selectGameTypePanel;
    private JPanel selectJoinGameIDPanel;
    private JPanel waitingRoomPanel;

    private InitResourceSelectorPanel initResourceSelectorPanel;


    private LeaderCardChoserGUI leaderCardChooser;
    private ClientGUI clientGUI;

    public NewGameHandler(ClientGUI clientGUI) {
        this.setVisible(true);
        this.setLayout(new GridLayout(1, 1));
        this.clientGUI = clientGUI;
        createPanels();
        this.mainLayout = new CardLayout();
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(this.mainLayout);
        this.mainPanel.add(SELECTNICKNAME, this.selectNicknamePanel);
        this.mainPanel.add(SELECTTYPEOFGAME, this.selectGameTypePanel);
        this.mainPanel.add(SELECTJOINGAMEID, this.selectJoinGameIDPanel);
        this.mainPanel.add(WAITINGROOM, this.waitingRoomPanel);
        this.mainLayout.show(this.mainPanel, SELECTNICKNAME);
        this.add(this.mainPanel);
    }

    private void createPanels() {
        createSelectNicknamePanel();
        createSelectGameTypePanel();
        createSelectJoinGameIDPanel();
        createWaitingRoomPanel();

    }


    private void createSelectNicknamePanel() {
        this.selectNicknamePanel = new JPanel();
        this.selectNicknamePanel.setLayout(new GridLayout(3, 1));
        createSelectNicknameComponents();
        JPanel titlePanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        middlePanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout());
        titlePanel.add(this.selectNicknameTitle);
        middlePanel.add(this.selectNicknameLabel, BorderLayout.CENTER);
        middlePanel.add(this.selectNicknameTextField, BorderLayout.SOUTH);
        bottomPanel.add(this.selectNicknameButton, BorderLayout.SOUTH);
        this.selectNicknamePanel.add(titlePanel);
        this.selectNicknamePanel.add(middlePanel);
        this.selectNicknamePanel.add(bottomPanel);
    }

    private void createSelectGameTypePanel() {
        this.selectGameTypePanel = new JPanel();
        this.selectGameTypePanel.setLayout(new GridLayout(3, 1));
        createSelectGameTypeComponents();
        JPanel titlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        bottomPanel.setLayout(new BorderLayout());
        buttonsPanel.setLayout(new GridLayout(1, 2));
        titlePanel.add(this.selectGameTypeTitle);
        this.selectGameTypeButtonGroupPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "How many players do you want to play with?"));
        for (JButton button : this.selectGameTypeButtons) {
            buttonsPanel.add(button);
        }
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.selectGameTypePanel.add(titlePanel);
        this.selectGameTypePanel.add(this.selectGameTypeButtonGroupPanel);
        this.selectGameTypePanel.add(bottomPanel);
    }

    private void createSelectJoinGameIDPanel() {
        this.selectJoinGameIDPanel = new JPanel();
        this.selectJoinGameIDPanel.setLayout(new GridLayout(3, 1));
        createSelectJoinGameIDComponents();
        JPanel titlePanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        middlePanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new BorderLayout());
        buttonsPanel.setLayout(new GridLayout(1, 2));
        titlePanel.add(this.selectJoinGameIDTitle);
        middlePanel.add(this.selectJoinGameIDLabel, BorderLayout.CENTER);
        middlePanel.add(this.selectJoinGameIDTextField, BorderLayout.SOUTH);
        for (JButton button : this.selectJoinGameIDButtons) {
            buttonsPanel.add(button);
        }
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);
        this.selectJoinGameIDPanel.add(titlePanel);
        this.selectJoinGameIDPanel.add(middlePanel);
        this.selectJoinGameIDPanel.add(bottomPanel);
    }

    private void createWaitingRoomPanel() {
        this.waitingRoomPanel = new JPanel();
        this.waitingRoomPanel.setLayout(new GridLayout(3, 1));
        createWaitingRoomComponents();
        JPanel titlePanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel voidPanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        middlePanel.setLayout(new BorderLayout());
        titlePanel.add(this.waitingRoomTitle);
        middlePanel.add(this.waitingRoomLabel, BorderLayout.CENTER);
        this.waitingRoomPanel.add(titlePanel);
        this.waitingRoomPanel.add(middlePanel);
        this.waitingRoomPanel.add(voidPanel);
    }

    private void createSelectNicknameComponents() {
        this.selectNicknameTitle = new JLabel();
        this.selectNicknameTitle.setText("Masters of Renaissance");
        this.selectNicknameTitle.setFont(new Font("Serif", Font.BOLD, 32));
        this.selectNicknameLabel = new JLabel("Choose a nickname:");
        this.selectNicknameTextField = new JTextField();
        this.selectNicknameButton = new JButton("Confirm Nickname");
        this.selectNicknameButton.addActionListener(new SelectNicknameListener(this.clientGUI, this));
    }

    private void createSelectGameTypeComponents() {
        ButtonGroup radioButtonGroup = new ButtonGroup();
        JRadioButton button1Player, button2Players, button3Players, button4Players;
        button1Player = new JRadioButton("Single Player");
        button2Players = new JRadioButton("2 Players");
        button3Players = new JRadioButton("3 Players");
        button4Players = new JRadioButton("4 Players");
        radioButtonGroup.add(button1Player);
        radioButtonGroup.add(button2Players);
        radioButtonGroup.add(button3Players);
        radioButtonGroup.add(button4Players);
        JButton createGameButton = new JButton("Create Game");
        JButton joinGameButton = new JButton("Join Game");
        createGameButton.addActionListener(new SelectGameTypeListener(this.clientGUI, this, true));
        joinGameButton.addActionListener(new SelectGameTypeListener(this.clientGUI,this, false));
        this.selectGameTypeRadioButtonGroup = radioButtonGroup;
        this.selectGameTypeButtons = new ArrayList<>();
        this.selectGameTypeButtons.add(createGameButton);
        this.selectGameTypeButtons.add(joinGameButton);
        this.selectGameTypeRadioButtons = new ArrayList<>();
        this.selectGameTypeRadioButtons.add(button1Player);
        this.selectGameTypeRadioButtons.add(button2Players);
        this.selectGameTypeRadioButtons.add(button3Players);
        this.selectGameTypeRadioButtons.add(button4Players);
        this.selectGameTypeButtonGroupPanel = new JPanel();
        this.selectGameTypeButtonGroupPanel.setLayout(new GridLayout(4, 1));
        this.selectGameTypeButtonGroupPanel.add(this.selectGameTypeRadioButtons.get(0));
        this.selectGameTypeButtonGroupPanel.add(this.selectGameTypeRadioButtons.get(1));
        this.selectGameTypeButtonGroupPanel.add(this.selectGameTypeRadioButtons.get(2));
        this.selectGameTypeButtonGroupPanel.add(this.selectGameTypeRadioButtons.get(3));
        this.selectGameTypeTitle = new JLabel();
        this.selectGameTypeTitle.setText("Masters of Renaissance");
        this.selectGameTypeTitle.setFont(new Font("Serif", Font.BOLD, 32));
    }

    private void createSelectJoinGameIDComponents() {
        this.selectJoinGameIDTitle = new JLabel();
        this.selectJoinGameIDTitle.setText("Masters of Renaissance");
        this.selectJoinGameIDTitle.setFont(new Font("Serif", Font.BOLD, 32));
        this.selectJoinGameIDLabel = new JLabel("Insert the Game ID:");
        this.selectJoinGameIDTextField = new JTextField();
        JButton buttonGoBack = new JButton("Go Back");
        JButton buttonJoin = new JButton("Join");
        buttonGoBack.addActionListener(new GoBackFromJoinListener(this));
        buttonJoin.addActionListener(new JoinGameListener(this.clientGUI,this));
        this.selectJoinGameIDButtons = new ArrayList<>();
        this.selectJoinGameIDButtons.add(buttonGoBack);
        this.selectJoinGameIDButtons.add(buttonJoin);
    }

    private void createWaitingRoomComponents() {
        this.waitingRoomTitle = new JLabel();
        this.waitingRoomTitle.setText("Masters of Renaissance");
        this.waitingRoomTitle.setFont(new Font("Serif", Font.BOLD, 32));
        this.waitingRoomLabel = new JLabel();
    }

    public void goToSelectNickname() { this.mainLayout.show(this.mainPanel, SELECTNICKNAME); }

    public void goToSelectGameType() { this.mainLayout.show(this.mainPanel, SELECTTYPEOFGAME); }

    public void goTOSelectJoinGameID() { this.mainLayout.show(this.mainPanel, SELECTJOINGAMEID); }

    public void goToWaitingRoom(String gameID) {
        this.waitingRoomLabel.setText("Wait for the other players\n Your game ID is: " + gameID);
        this.mainLayout.show(this.mainPanel, WAITINGROOM);
    }

    public void goToLeaderCardChooser(List<LeaderCard> leaderCards) {
        if(this.leaderCardChooser!=null){
            this.leaderCardChooser.dispose();
            this.mainLayout.removeLayoutComponent(leaderCardChooser);
        }
        this.leaderCardChooser = new LeaderCardChoserGUI(leaderCards, this.clientGUI, this);
//        if (this.mainPanel.getComponentCount() == 4) {
//            this.mainPanel.add(LEADERCARDCHOOSER, this.leaderCardChooser);
//        }

        this.mainPanel.add(LEADERCARDCHOOSER, this.leaderCardChooser);

        this.mainLayout.show(this.mainPanel, LEADERCARDCHOOSER);
        this.leaderCardChooser.pack();
    }

    public JTextField getNicknameTextField() { return this.selectNicknameTextField; }

    public List<JRadioButton> getRadioButtons() { return this.selectGameTypeRadioButtons; }

    public ButtonGroup getRadioButtonGroup() { return this.selectGameTypeRadioButtonGroup; }

    public JTextField getSelectJoinGameIDTextField() {
        return selectJoinGameIDTextField;
    }

    public void setSelectJoinGameIDTextField(JTextField selectJoinGameIDTextField) {
        this.selectJoinGameIDTextField = selectJoinGameIDTextField;
    }

    public void goToResourceSelector(Integer numberOfResourcesToChoose){
        if(this.initResourceSelectorPanel!=null){
            this.mainLayout.removeLayoutComponent(leaderCardChooser);
        }
        this.initResourceSelectorPanel = new InitResourceSelectorPanel(clientGUI,this,numberOfResourcesToChoose);
        this.mainPanel.add(RESOURCESELECTOR,this.initResourceSelectorPanel);
        this.mainLayout.show(this.mainPanel,RESOURCESELECTOR);
    }
}
