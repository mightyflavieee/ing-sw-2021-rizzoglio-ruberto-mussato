package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;

public class GUIPROVA {
    private BoardGUI board;
    private MatchGUI match;
    private MarketGUI marketGUI;
    private MarketButton marketButton;
    private MarketButtonListener marketButtonListener;

    public GUIPROVA() {
        marketGUI = new MarketGUI("market");
//        marketButton = new MarketButton("market button");
//        marketButtonListener = new MarketButtonListener(marketGUI);
//        marketButton.addActionListener(marketButtonListener);
        JFrame jFrame = new JFrame();
//        jFrame.setLayout(new GridLayout(2,2));
//        jFrame.add(marketButton);
        jFrame.add(marketGUI);
       // jFrame.add(cardcontainerGUi);

       // jFrame.add(new MarketGUI("altro market ma potrebbe essere board"));
       // jFrame.add(new MarketGUI("altro market ma potrebbe essere warehouse"));
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    marketGUI.doubleSize();
    }

    public static void main(String[] args){


        new GUIPROVA();
    }
}
