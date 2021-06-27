package it.polimi.ingsw.project.client.gui.board;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.listeners.selectcard.SelectDevCardProductionListener;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapTrayGUI extends JInternalFrame {
    private Map<DevCardPosition, JButton> mapTrayButtons;
    private final InformationsGUI informationsGUI;
    private Board boardModel;
    private boolean clickable;
    private int width = 100, height = 160;

    public MapTrayGUI(InformationsGUI informationsGUI, Board boardModel) {
        this.setTitle("Map Tray");
        this.setVisible(true);
        this.setLayout(new GridLayout(1, 3));
        createButtons();
        this.add(this.mapTrayButtons.get(DevCardPosition.Left));
        this.add(this.mapTrayButtons.get(DevCardPosition.Center));
        this.add(this.mapTrayButtons.get(DevCardPosition.Right));
        this.pack();
        this.informationsGUI = informationsGUI;
        this.boardModel = boardModel;
        this.clickable = false;
        refresh();
    }

    public void refresh() {
        for (DevCardPosition position : this.boardModel.getMapTray().keySet()) {
            if (this.boardModel.getMapTray().get(position).size() == 0) {
                // shows back of the devcard in that DevCardPosition
                this.mapTrayButtons.get(position).setIcon(Utils.readIcon("developmentcards/retro_devcard.png",width,height));
                // if present, removes ActionListener
                if (this.mapTrayButtons.get(position).getActionListeners().length > 0) {
                    ActionListener actionListener = this.mapTrayButtons.get(position).getActionListeners()[0];
                    this.mapTrayButtons.get(position).removeActionListener(actionListener);
                }
            } else {
                // shows the last devcard in that DevCardPosition
                DevelopmentCard lastCard = this.boardModel.getMapTray().get(position)
                        .get(this.boardModel.getMapTray().get(position).size()-1);
                this.mapTrayButtons.get(position).setIcon(Utils.readIcon("developmentcards/" + lastCard.getId() + ".png",width,height));
                // if not present, adds ActionListener. If present, removes old ActionListener and adds a new one
                if (Arrays.stream(this.mapTrayButtons.get(position).getActionListeners()).count() == 0) {
                    this.mapTrayButtons.get(position).addActionListener(
                            new SelectDevCardProductionListener(this.informationsGUI.getGUI(), lastCard));
                } else {
                    ActionListener actionListener = this.mapTrayButtons.get(position).getActionListeners()[0];
                    this.mapTrayButtons.get(position).removeActionListener(actionListener);
                    this.mapTrayButtons.get(position).addActionListener(
                            new SelectDevCardProductionListener(this.informationsGUI.getGUI(), lastCard));
                }
            }
        }
    }

    private void createButtons() {
        this.mapTrayButtons = new HashMap<>();
        JButton leftButton = new JButton();
        JButton centerButton = new JButton();
        JButton rightButton = new JButton();
        leftButton.setIcon(Utils.readIcon("developmentcards/retro_devcard.png",width,height));
        centerButton.setIcon(Utils.readIcon("developmentcards/retro_devcard.png",width,height));
        rightButton.setIcon(Utils.readIcon("developmentcards/retro_devcard.png",width,height));

        this.mapTrayButtons.put(DevCardPosition.Left, leftButton);
        this.mapTrayButtons.put(DevCardPosition.Center, centerButton);
        this.mapTrayButtons.put(DevCardPosition.Right, rightButton);
    }

    public void setBoardModel(Board boardModel) { this.boardModel = boardModel; }

    public void disableAllButtons() {
        this.clickable = false;
    }

    public void enableAllButtons() {
        this.clickable = true;
    }

    public boolean isClickable() {
        return this.clickable;
    }

    public void setMapTrayByPlayer(Player mePlayer) {
        this.boardModel = mePlayer.getBoard();
        refresh();
    }

    public void refreshSize(int width, int height) {
        this.width = width/3;
        this.height = height;
        this.refresh();
    }
}
