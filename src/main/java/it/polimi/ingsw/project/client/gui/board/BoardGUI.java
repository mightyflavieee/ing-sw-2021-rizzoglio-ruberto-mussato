package it.polimi.ingsw.project.client.gui.board;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.Warehouse;

import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JInternalFrame {
    private final FaithMapGUI faithMap;
    private final WarehouseGUI warehouse;
    private final MapTrayGUI mapTray;
    private final ChestGUI chest;
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

    public ChestGUI getChestGUI() {
        return chest;
    }

    public Warehouse getWarehouseModel() {
      return   this.warehouse.getWarehouseModel();
    }

    public void refresh(Board boardModel) {
        this.boardModel = boardModel;
        this.warehouse.setWarehouseModel(boardModel.getWarehouse());
        this.warehouse.refresh();
        this.chest.setBoardModel(boardModel);
        this.chest.refresh();
        this.mapTray.setBoardModel(boardModel);
        this.mapTray.refresh();
        this.faithMap.setBoardModel(boardModel);
        this.faithMap.refresh();
    }

    public void disableAllButtons() {
        this.faithMap.disableAllButtons();
        this.warehouse.disableAllButtons();
        this.mapTray.disableAllButtons();
        this.chest.disableAllButtons();
    }

    public void enableAllButtons() {
        this.faithMap.enableAllButtons();
        this.warehouse.enableAllButtons();
        this.mapTray.enableAllButtons();
        this.chest.enableAllButtons();
    }

    public void moveForward() {
        this.faithMap.moveForward();
    }

    public void setBoardByPlayer(Player mePlayer) {
        this.setTitle(mePlayer.getNickname());
        this.warehouse.setWarehouseByPlayer(mePlayer);
        this.chest.setChestByPlayer(mePlayer);
        this.mapTray.setMapTrayByPlayer(mePlayer);
        this.faithMap.setFaithMapByPlayer(mePlayer);
    }

    public void refreshSize(int width, int height) {
        this.mapTray.refreshSize(width,height);
    }
}
