package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchGUI extends JInternalFrame {
    private JPanel panel;
    private final List<BoardGUI> boards;
    private MarketGUI market;

    public MatchGUI(String matchName, List<String> nicknames, int numberOfPlayers) {
        this.setTitle(matchName);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(1100, 850));
        try {
            this.panel = new JPanelWithBackground("src/main/resources/table.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.boards = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            this.boards.add(new BoardGUI(nicknames.get(i)));
        }
        this.market = new MarketGUI("Market");
        createTable(nicknames);
        for (int i = 0; i < numberOfPlayers; i++) {
            this.add(this.boards.get(i), i);
            this.boards.get(i).hide();
        }
        this.add(this.panel, 4);
    }

    public List<BoardGUI> getBoards() {
        return boards;
    }

    public JPanel getPanel() {
        return panel;
    }

    private void createTable(List<String> nicknames) {
        JPanel upperPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel  = new JPanel();
        List<JPanel> structurePanelList = new ArrayList<>();
        structurePanelList.add(upperPanel);
        structurePanelList.add(middlePanel);
        structurePanelList.add(bottomPanel);
        for (JPanel panel : structurePanelList) {
            panel.setOpaque(false);
            panel.setLayout(new FlowLayout());
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        upperPanel.setPreferredSize(new Dimension(screenSize.width, 100));
        middlePanel.setPreferredSize(new Dimension(screenSize.width, screenSize.height-300));
        bottomPanel.setPreferredSize(new Dimension(screenSize.width, 100));

        BoardGUIButton buttonUpperPlayer = addBoardButton(nicknames.get(0), 0);
        BoardGUIButton buttonLeftPlayer = addBoardButton(nicknames.get(1), 1);
        BoardGUIButton buttonRightPlayer = addBoardButton(nicknames.get(2), 2);
        BoardGUIButton buttonBottomPlayer = addBoardButton(nicknames.get(3), 3);
        buttonUpperPlayer.setPreferredSize(new Dimension(150, 20));
        buttonLeftPlayer.setPreferredSize(new Dimension(150, 20));
        buttonRightPlayer.setPreferredSize(new Dimension(150, 20));
        buttonBottomPlayer.setPreferredSize(new Dimension(150, 20));
        upperPanel.add(buttonUpperPlayer);
        bottomPanel.add(buttonBottomPlayer);

        JPanel middleLeftPanel = new JPanel();
        JPanel middleCenterPanel = new JPanel();
        JPanel middleRightPanel = new JPanel();
        middleLeftPanel.setOpaque(false);
        middleCenterPanel.setOpaque(false);
        middleRightPanel.setOpaque(false);
        middleLeftPanel.setPreferredSize(new Dimension(200, screenSize.height-300));
        middleCenterPanel.setPreferredSize(new Dimension(screenSize.width-450, screenSize.height-300));
        middleRightPanel.setPreferredSize(new Dimension(200, screenSize.height-300));
        middleLeftPanel.setLayout(new FlowLayout());
        middleCenterPanel.setLayout(new FlowLayout());
        middleRightPanel.setLayout(new FlowLayout());
        JPanel spaceLeftPanel = new JPanel();
        JPanel spaceRightPanel = new JPanel();
        spaceLeftPanel.setOpaque(false);
        spaceRightPanel.setOpaque(false);
        spaceLeftPanel.setPreferredSize(new Dimension(200, 240));
        spaceRightPanel.setPreferredSize(new Dimension(200, 240));
        middleLeftPanel.add(spaceLeftPanel);
        middleLeftPanel.add(buttonLeftPlayer);
        middleRightPanel.add(spaceRightPanel);
        middleRightPanel.add(buttonRightPlayer);

        middlePanel.add(middleLeftPanel);
        middlePanel.add(middleCenterPanel);
        middlePanel.add(middleRightPanel);

        /*middleLeftPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        middleCenterPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        middleRightPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        upperPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        middlePanel.setBorder(BorderFactory.createLineBorder(Color.red));
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.red));*/

        this.panel.setLayout(new FlowLayout());
        this.panel.add(upperPanel);
        this.panel.add(middlePanel);
        this.panel.add(bottomPanel);
    }

    private BoardGUIButton addBoardButton(String nickname, int playerNumber) {
        BoardGUIButton button = new BoardGUIButton(nickname);
        button.addActionListener(new BoardGUIButtonListener(this.boards.get(playerNumber), this));
        return button;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(0,0, screenSize.width, screenSize.height);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        List<String> nicknames = new ArrayList<>();
        nicknames.add("PEPPE");
        nicknames.add("BOBBI");
        nicknames.add("ERFAINA");
        nicknames.add("SALVINI");
        frame.add(new MatchGUI("Match di prova", nicknames, 4));
    }

}
