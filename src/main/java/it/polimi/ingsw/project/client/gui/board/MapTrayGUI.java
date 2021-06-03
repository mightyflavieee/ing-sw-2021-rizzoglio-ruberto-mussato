package it.polimi.ingsw.project.client.gui.board;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MapTrayGUI extends JInternalFrame {
    private Map<DevCardPosition, JButton> mapTrayButtons;
    private InformationsGUI informationsGUI;
    private Board boardModel;

    public MapTrayGUI(InformationsGUI informationsGUI, Board boardModel) {
        this.setTitle("Map Tray");
        this.setVisible(true);
        this.setLayout(new GridLayout(1, 3));
        createButtons();
        for (DevCardPosition position : this.mapTrayButtons.keySet()) {
            this.add(this.mapTrayButtons.get(position));
        }
        this.pack();
        this.informationsGUI = informationsGUI;
        this.boardModel = boardModel;
        refresh();
    }

    public void refresh() {
        for (DevCardPosition position : this.boardModel.getMapTray().keySet()) {
            if (this.boardModel.getMapTray().get(position).size() == 0) {
                this.mapTrayButtons.get(position).setIcon(new ImageIcon(new javax.swing
                        .ImageIcon("src/main/resources/developmentcards/retro_devcard.png")
                        .getImage().getScaledInstance(100, 160, Image.SCALE_SMOOTH)));
            } else {
                DevelopmentCard lastCard = this.boardModel.getMapTray().get(position)
                        .get(this.boardModel.getMapTray().get(position).size()-1);
                this.mapTrayButtons.get(position).setIcon(new ImageIcon(new javax.swing
                        .ImageIcon("src/main/resources/developmentcards/" + lastCard.getId() + ".png")
                        .getImage().getScaledInstance(100, 160, Image.SCALE_SMOOTH)));
            }
        }
    }

    private void createButtons() {
        this.mapTrayButtons = new HashMap<>();
        JButton leftButton = new JButton();
        JButton centerButton = new JButton();
        JButton rightButton = new JButton();
        leftButton.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/retro_devcard.png")
                .getImage().getScaledInstance(100, 160, Image.SCALE_SMOOTH)));
        centerButton.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/retro_devcard.png")
                .getImage().getScaledInstance(100, 160, Image.SCALE_SMOOTH)));
        rightButton.setIcon(new ImageIcon(new javax.swing
                .ImageIcon("src/main/resources/retro_devcard.png")
                .getImage().getScaledInstance(100, 160, Image.SCALE_SMOOTH)));
        this.mapTrayButtons.put(DevCardPosition.Left, leftButton);
        this.mapTrayButtons.put(DevCardPosition.Center, centerButton);
        this.mapTrayButtons.put(DevCardPosition.Right, rightButton);
    }
}
