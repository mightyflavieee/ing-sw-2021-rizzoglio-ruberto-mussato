package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.listeners.endgame.StartNewGameListener;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class EndGameHandler extends JPanel {
    private static final String SINGLEPLAYERPANEL = "SINGLEPLAYERPANEL";
    private static final String MULTIPLAYERPANEL = "MULTIPLAYERPANEL";

    private ClientGUI clientGUI;
    private Match matchModel;

    private JPanel mainPanel;
    private CardLayout mainLayout;
    private JPanel singlePlayerPanel;
    private JPanel multiPlayerPanel;

    public EndGameHandler(Match matchModel, ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        this.matchModel = matchModel;

        this.mainLayout = new CardLayout();

        this.mainPanel = new JPanel();

        this.mainPanel.setLayout(this.mainLayout);

        //this.mainPanel.setLayout(new GridLayout(3, 1));

        createPanels();
        this.mainPanel.add(SINGLEPLAYERPANEL, this.singlePlayerPanel);
        //this.mainPanel.add(MULTIPLAYERPANEL, this.multiPlayerPanel);
        this.mainLayout.show(this.mainPanel, SINGLEPLAYERPANEL);

        /*JPanel titlePanel = new JPanel();
        JPanel leaderboardPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        createTitlePanel(titlePanel);
        createLeaderboardPanel(leaderboardPanel);
        createBottomPanel(bottomPanel);
        this.mainPanel.add(titlePanel);
        this.mainPanel.add(leaderboardPanel);
        this.mainPanel.add(bottomPanel);*/

        this.add(this.mainPanel);
    }

    private void createPanels() {
        createSinglePlayerPanel();
        //createMultiPlayerPanel();
    }

    private void createSinglePlayerPanel() {
        JPanel titlePanel = new JPanel();
        JPanel singlePlayerResultsPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        createTitlePanel(titlePanel);
        createSinglePlayerResultsPanel(singlePlayerResultsPanel);
        createBottomPanel(bottomPanel);
        this.singlePlayerPanel = new JPanel();
        this.singlePlayerPanel.setLayout(new GridLayout(3, 1));
        this.singlePlayerPanel.add(titlePanel);
        this.singlePlayerPanel.add(singlePlayerResultsPanel);
        this.singlePlayerPanel.add(bottomPanel);
    }

    private void createMultiPlayerPanel() {
        JPanel titlePanel = new JPanel();
        JPanel leaderboardPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        createTitlePanel(titlePanel);
        createLeaderboardPanel(leaderboardPanel);
        createBottomPanel(bottomPanel);
        this.multiPlayerPanel = new JPanel();
        this.multiPlayerPanel.setLayout(new GridLayout(3, 1));
        this.multiPlayerPanel.add(titlePanel);
        this.multiPlayerPanel.add(leaderboardPanel);
        this.multiPlayerPanel.add(bottomPanel);
    }

    private void createTitlePanel(JPanel titlePanel) {
        titlePanel.setLayout(new BorderLayout());
        JLabel title = new JLabel("Masters of Renaissance");
        title.setFont(new Font("Serif", Font.BOLD, 32));
        titlePanel.add(title, BorderLayout.CENTER);
    }

    private void createLeaderboardPanel(JPanel leaderboardPanel) {
        leaderboardPanel.setLayout(new BorderLayout());
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(1, 3));
        leaderboardPanel.add(new JLabel("Leaderboard:"), BorderLayout.NORTH);
        leaderboardPanel.add(middlePanel, BorderLayout.CENTER);
        LinkedHashMap<Integer, JLabel> leaderboardNicknames = new LinkedHashMap<>();
        LinkedHashMap<Integer, JLabel> leaderboardVictoryPoints = new LinkedHashMap<>();
        LinkedHashMap<Integer, Player> leaderboard = this.matchModel.getLeaderboard();
        for (Integer position : leaderboard.keySet()) {
            leaderboardNicknames.put(position, new JLabel(leaderboard.get(position).getNickname()));
            int vp = leaderboard.get(position).getVictoryPoints();
            leaderboardVictoryPoints.put(position, new JLabel(Integer.toString(vp)));
        }
        JPanel leftPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(4, 1));
        centerPanel.setLayout(new GridLayout(4, 1));
        rightPanel.setLayout(new GridLayout(4, 1));
        leftPanel.add(new JLabel("1."));
        leftPanel.add(new JLabel("2."));
        leftPanel.add(new JLabel("3."));
        leftPanel.add(new JLabel("4."));
        for (Integer position : leaderboard.keySet()) {
           centerPanel.add(leaderboardNicknames.get(position));
           rightPanel.add(leaderboardVictoryPoints.get(position));
        }
        middlePanel.add(leftPanel);
        middlePanel.add(centerPanel);
        middlePanel.add(rightPanel);
        leaderboardPanel.add(middlePanel);
    }

    private void createSinglePlayerResultsPanel(JPanel singlePlayerResultsPanel) {
        singlePlayerResultsPanel.setLayout(new FlowLayout());
        JLabel resultsLabel = new JLabel();
        if (this.matchModel.getLorenzoWon()) {
            resultsLabel.setText("Lorenzo il Magnifico Wins!");
        } else {
            resultsLabel.setText("VICTORY!");
        }
        resultsLabel.setFont(new Font("Serif", Font.BOLD, 25));
        resultsLabel.setForeground(Color.RED);
        singlePlayerResultsPanel.add(resultsLabel);
    }

    private void createBottomPanel(JPanel bottomPanel) {
        bottomPanel.setLayout(new BorderLayout());
        JButton buttonGoBack = new JButton("Start New Game");
        buttonGoBack.addActionListener(new StartNewGameListener(this.clientGUI));
        bottomPanel.add(buttonGoBack, BorderLayout.SOUTH);
    }
}