package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.listeners.endgame.StartNewGameListener;
import it.polimi.ingsw.project.model.Match;

import javax.swing.*;
import java.awt.*;

public class EndGameHandler extends JPanel {
    private static final String SINGLEPLAYERPANEL = "SINGLEPLAYERPANEL";

    private final ClientGUI clientGUI;
    private final Match matchModel;

    private JPanel singlePlayerPanel;

    public EndGameHandler(Match matchModel, ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        this.matchModel = matchModel;

        CardLayout mainLayout = new CardLayout();

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(mainLayout);

        //this.mainPanel.setLayout(new GridLayout(3, 1));

        createPanels();
        mainPanel.add(SINGLEPLAYERPANEL, this.singlePlayerPanel);
        //this.mainPanel.add(MULTIPLAYERPANEL, this.multiPlayerPanel);
        mainLayout.show(mainPanel, SINGLEPLAYERPANEL);

        this.add(mainPanel);
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

    private void createTitlePanel(JPanel titlePanel) {
        titlePanel.setLayout(new BorderLayout());
        JLabel title = new JLabel("Masters of Renaissance");
        title.setFont(new Font("Serif", Font.BOLD, 32));
        titlePanel.add(title, BorderLayout.CENTER);
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
