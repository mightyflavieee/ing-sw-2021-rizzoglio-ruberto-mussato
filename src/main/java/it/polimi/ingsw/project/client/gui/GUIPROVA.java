package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.CardContainer;

import javax.swing.*;
import java.awt.*;

public class GUIPROVA {
    private BoardGUI board;
    private MatchGUI match;
    private MarketGUI marketGUI;
    private MarketButton marketButton;
    private MarketButtonListener marketButtonListener;
    private LeaderCardPlaceGUI leaderCardPlaceGui;
    private JFrame jFrame;
    private CardContainerGUI cardContainerGUI;

    public GUIPROVA() {
        marketGUI = new MarketGUI("market");
        board = new BoardGUI();
        cardContainerGUI = new CardContainerGUI();
//        marketButton = new MarketButton("market button");
//        marketButtonListener = new MarketButtonListener(marketGUI);
//        marketButton.addActionListener(marketButtonListener);
        jFrame = new JFrame();
        jFrame.setLayout(new GridLayout(2,2));
//        jFrame.add(marketButton);
        jFrame.add(marketGUI);
        jFrame.add(board);
       // jFrame.add(cardcontainerGUi);
        jFrame.add(cardContainerGUI);
       // jFrame.add(new MarketGUI("altro market ma potrebbe essere board"));
       // jFrame.add(new MarketGUI("altro market ma potrebbe essere warehouse"));
        leaderCardPlaceGui = new LeaderCardPlaceGUI();
        jFrame.add(leaderCardPlaceGui);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    marketGUI.doubleSize();
    }

    public static void main(String[] args){


        new GUIPROVA();
    }
}
