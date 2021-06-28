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
    private Board boardModel;

    public BoardGUI(String nickname, InformationsGUI informationsGUI, Board board, boolean isSinglePlayer){
        this.setTitle(nickname + " - VP: 0");
        this.setVisible(true);

        this.boardModel = board;
        this.faithMap = new FaithMapGUI(board.getFaithMap(), isSinglePlayer);
        this.warehouse = new WarehouseGUI(informationsGUI, this.boardModel.getWarehouse());
        this.mapTray = new MapTrayGUI(informationsGUI, this.boardModel);
        this.chest = new ChestGUI(informationsGUI, this.boardModel);

        JInternalFrame depositsFrame = new JInternalFrame("Deposits");
        depositsFrame.setVisible(true);

        depositsFrame.setLayout(new GridLayout(1, 2));
        depositsFrame.add(this.warehouse);
        depositsFrame.add(this.chest);

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

    public MapTrayGUI getMapTrayGUI() { return mapTray; }

    public Board getBoardModel() { return this.boardModel; }

    public Warehouse getWarehouseModel() {
      return this.warehouse.getWarehouseModel();
    }

    /**
     * @param boardModel updates the local model shows the visual representation of the received Board
     */
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

    /**
     * @param nickname it is showed as a title of the JinternalFrame
     * @param victoryPoints it is showed next to the nickname
     */
    public void setBoardTitle(String nickname, int victoryPoints) {
        this.setTitle(nickname + " - VP:" + victoryPoints);
    }

    public void disableAllButtons() {
        this.warehouse.disableAllButtons();
        this.mapTray.disableAllButtons();
        this.chest.disableAllButtons();
    }

    /**
     * updates the marker position of the local model and updates the visual representation
     */
    public void moveForward() {
        this.faithMap.moveForward();
    }

    /**
     * @param mePlayer shows the visual representation of the input player, it is used to see an opponent player's board
     */
    public void setBoardByPlayer(Player mePlayer) {
        this.setTitle(mePlayer.getNickname() + " - VP:" + mePlayer.getVictoryPoints());
        this.warehouse.setWarehouseByPlayer(mePlayer);
        this.chest.setChestByPlayer(mePlayer);
        this.mapTray.setMapTrayByPlayer(mePlayer);
        this.faithMap.setFaithMapByPlayer(mePlayer);
    }

    /**
     * changes the size of the pictures of the maptray
     */
    public void refreshSize(int width, int height) {
        this.mapTray.refreshSize(
                (int) (width*0.4), (int) (height*0.6));
    }

    /**
     * @param b if true you can change the shelves, it is used in the Market move
     */
    public void setCanChangeShelves(boolean b) {
        this.warehouse.setCanChangeShelves(b);
    }
}
