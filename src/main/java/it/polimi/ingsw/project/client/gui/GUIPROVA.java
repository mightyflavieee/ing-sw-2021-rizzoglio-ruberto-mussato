package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUIPROVA {
    private BoardGUI boardGUI;
    private MatchGUI matchGUI;
    private MarketGUI marketGUI;
//    private MarketButton marketButton;
//    private MarketButtonListener marketButtonListener;
    private LeaderCardPlaceGUI leaderCardPlaceGui;
    private JFrame jFrame;
    private CardContainerGUI cardContainerGUI;
    private FaithMapGUI faithMapGUI;
    private WarehouseGUI warehouseGUI;
    private ChestGUI chestGUI;
    private MapTrayGUI mapTrayGUI;
    private Player mePlayer;
    private List<Player> opponentsPlayer;
    private InformationsGUI informationsGUI;


    public GUIPROVA() {
        jFrame = new JFrame();
        jFrame.setLayout(new GridLayout(4,2));
       // jFrame.setLayout(new BorderLayout());
        marketGUI = new MarketGUI();
      //  boardGUI = new BoardGUI("Board");
        cardContainerGUI = new CardContainerGUI();
        leaderCardPlaceGui = new LeaderCardPlaceGUI();
        faithMapGUI = new FaithMapGUI();
        warehouseGUI = new WarehouseGUI();
        chestGUI = new ChestGUI();
        mapTrayGUI = new MapTrayGUI();
        informationsGUI = new InformationsGUI();

//        marketButton = new MarketButton("market button");
//        marketButtonListener = new MarketButtonListener(marketGUI);
//        marketButton.addActionListener(marketButtonListener);
//        jFrame.add(marketButton);
//        jFrame.add(new MarketGUI("altro market ma potrebbe essere board"));
//        jFrame.add(new MarketGUI("altro market ma potrebbe essere warehouse"));
//        marketGUI.doubleSize();

//        jFrame.add(marketGUI, BorderLayout.EAST);
//        jFrame.add(boardGUI, BorderLayout.NORTH);
//        jFrame.add(cardContainerGUI, BorderLayout.CENTER);
//        jFrame.add(leaderCardPlaceGui, BorderLayout.WEST);
        jFrame.add(faithMapGUI);
        jFrame.add(warehouseGUI);
        jFrame.add(chestGUI);
        jFrame.add(mapTrayGUI);
        jFrame.add(informationsGUI);
        jFrame.add(marketGUI);
        jFrame.add(cardContainerGUI);
        jFrame.add(leaderCardPlaceGui);
        //jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
    }

    public static void main(String[] args){
        new GUIPROVA();
    }
}
