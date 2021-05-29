package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.DevCardPosition;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BoardGUI extends JInternalFrame {
    private FaithMapGUI faithMap;
    private WarehouseGUI warehouse;
    private MapTrayGUI mapTray;
    private ChestGUI chest;

    public BoardGUI(String nickname){
        this.setTitle(nickname);
        this.setVisible(true);
        this.setLayout(new GridBagLayout());

        this.faithMap = new FaithMapGUI();
        this.warehouse = new WarehouseGUI();
        this.mapTray = new MapTrayGUI();
        this.chest = new ChestGUI();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        this.add(this.faithMap, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipadx = 300;
        JInternalFrame depositsFrame = new JInternalFrame("Deposits");
        depositsFrame.setVisible(true);
        depositsFrame.setLayout(new BorderLayout());
        depositsFrame.add(this.warehouse, BorderLayout.NORTH);
        depositsFrame.add(this.chest, BorderLayout.SOUTH);
        this.add(depositsFrame, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.ipady = 300;
        this.add(this.mapTray, constraints);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BoardGUI("GIGGINO"));
        frame.pack();
    }


}
