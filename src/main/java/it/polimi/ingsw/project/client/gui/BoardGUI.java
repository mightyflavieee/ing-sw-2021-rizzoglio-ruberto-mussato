package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.Match;

import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JInternalFrame {
    private FaithMapGUI faithMap;
    private WarehouseGUI warehouse;
    private MapTrayGUI mapTray;
    private ChestGUI chest;
    private InformationsGUI informationsGUI;

    public BoardGUI(String nickname, InformationsGUI informationsGUI){
        this.setTitle(nickname);
        this.setVisible(true);
        this.setLayout(new GridBagLayout());

        this.informationsGUI = informationsGUI;
        this.faithMap = new FaithMapGUI();
        this.warehouse = new WarehouseGUI(this.informationsGUI);
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
        this.add(this.mapTray, constraints);
    }
}
