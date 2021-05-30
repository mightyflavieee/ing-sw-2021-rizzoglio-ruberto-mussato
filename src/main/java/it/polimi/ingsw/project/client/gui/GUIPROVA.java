package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.MarketButtonListener;

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
    private FaithMapGUI faithMapGUI;
    private WarehouseGUI warehouseGUI;
    private ChestGUI chestGUI;
    private MapTrayGUI mapTrayGUI;


    public GUIPROVA() {
        jFrame = new JFrame();
        //jFrame.setLayout(new GridLayout(2,2));
        jFrame.setLayout(new BorderLayout());
        marketGUI = new MarketGUI();
        board = new BoardGUI("Board");
        cardContainerGUI = new CardContainerGUI();
        leaderCardPlaceGui = new LeaderCardPlaceGUI();
        faithMapGUI = new FaithMapGUI();
        warehouseGUI = new WarehouseGUI();
        chestGUI = new ChestGUI();
        mapTrayGUI = new MapTrayGUI();

//        marketButton = new MarketButton("market button");
//        marketButtonListener = new MarketButtonListener(marketGUI);
//        marketButton.addActionListener(marketButtonListener);
//        jFrame.add(marketButton);
//        jFrame.add(new MarketGUI("altro market ma potrebbe essere board"));
//        jFrame.add(new MarketGUI("altro market ma potrebbe essere warehouse"));
//        marketGUI.doubleSize();

        jFrame.add(marketGUI, BorderLayout.EAST);
        jFrame.add(board, BorderLayout.NORTH);
        jFrame.add(cardContainerGUI, BorderLayout.CENTER);
        jFrame.add(leaderCardPlaceGui, BorderLayout.WEST);
        //jFrame.add(faithMapGUI);
        //jFrame.add(warehouseGUI);
        //jFrame.add(chestGUI);
        //jFrame.add(mapTrayGUI);
        //jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
    }

    public static void main(String[] args){
        new GUIPROVA();
    }
}
