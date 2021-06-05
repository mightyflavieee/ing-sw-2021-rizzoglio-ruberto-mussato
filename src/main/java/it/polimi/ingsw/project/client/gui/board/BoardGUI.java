package it.polimi.ingsw.project.client.gui.board;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.Warehouse;

import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JInternalFrame {
    private FaithMapGUI faithMap;
    private WarehouseGUI warehouse;
    private MapTrayGUI mapTray;
    private ChestGUI chest;
    private InformationsGUI informationsGUI;
    private Board boardModel;

    public BoardGUI(String nickname, InformationsGUI informationsGUI, Board board){
        this.setTitle(nickname);
        this.setVisible(true);

        // this.setLayout(new GridBagLayout());

        this.informationsGUI = informationsGUI;
        this.boardModel = board;
        this.faithMap = new FaithMapGUI(board.getFaithMap());
        this.warehouse = new WarehouseGUI(this.informationsGUI, this.boardModel.getWarehouse());
        this.mapTray = new MapTrayGUI(this.informationsGUI, this.boardModel);
        this.chest = new ChestGUI(this.informationsGUI, this.boardModel);

//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        constraints.gridwidth = 3;
//        constraints.gridheight = 1;
//        this.add(this.faithMap, constraints);

//        constraints.gridx = 0;
//        constraints.gridy = 1;
//        constraints.gridwidth = 1;
//        constraints.gridheight = 1;

        JInternalFrame depositsFrame = new JInternalFrame("Deposits");
        depositsFrame.setVisible(true);

        /*depositsFrame.setLayout(new BorderLayout());
        depositsFrame.add(this.warehouse, BorderLayout.NORTH);
        depositsFrame.add(this.chest, BorderLayout.SOUTH);*/

        depositsFrame.setLayout(new GridLayout(1, 2));
        depositsFrame.add(this.warehouse);
        depositsFrame.add(this.chest);

//        this.add(depositsFrame, constraints);

//        constraints.gridx = 1;
//        constraints.gridy = 1;
//        constraints.gridwidth = 2;
//        constraints.gridheight = 1;
//        this.add(this.mapTray, constraints);

        this.setLayout(new BorderLayout());
        this.add(faithMap, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,2));
        centerPanel.add(depositsFrame);
        centerPanel.add(mapTray);
        this.add(centerPanel,BorderLayout.CENTER);
    }

    public WarehouseGUI getWarehouseGUI() {
        return warehouse;
    }

    public Warehouse getWarehouseModel() {
      return   this.warehouse.getWarehouseModel();
    }
}
