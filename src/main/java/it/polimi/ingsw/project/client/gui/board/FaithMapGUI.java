package it.polimi.ingsw.project.client.gui.board;

import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.faithMap.FaithMap;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FaithMapGUI extends JInternalFrame {
    private final List<JButton> tiles;
    private final List<JButton> papalCuoncilTiles;
    private int markerPosition;
    private FaithMap faithMapModel;
    private boolean clickable;

    public FaithMapGUI(FaithMap faithMap) {
        this.setTitle("FaithMap");
        this.setVisible(true);
        this.setLayout(new GridLayout(2, 24));
        this.markerPosition = 0;
        this.faithMapModel = faithMap;
        this.tiles = new ArrayList<>();
        this.papalCuoncilTiles = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            JButton button = new JButton(String.valueOf(i+1));
            button.setBackground(new Color(255, 255, 255));
            this.tiles.add(button);
        }
        for (int i = 0; i < 23; i++) {
            JButton button = new JButton();
            if (i != 5 && i != 13 && i != 20) {
                button.setVisible(false);
            }
            if (i == 5) {
                button.setIcon(new ImageIcon(
                        new javax.swing.ImageIcon("src/main/resources/tiles/quadrato giallo.png")
                        .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            }
            if (i == 13) {
                button.setIcon(new ImageIcon(
                        new javax.swing.ImageIcon("src/main/resources/tiles/quadrato arancione.png")
                        .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            }
            if (i == 20) {
                button.setIcon(new ImageIcon(
                        new javax.swing.ImageIcon("src/main/resources/tiles/quadrato rosso.png")
                        .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            }
            this.papalCuoncilTiles.add(button);
        }
        setBordersForCouncil();
        for (JButton button : this.tiles) {
            this.add(button);
        }
        this.tiles.get(0).setBackground(new Color(105, 105, 105));
        for (JButton button : this.papalCuoncilTiles) {
            this.add(button);
        }
    }

    private void setBordersForCouncil() {
        for (JButton button : this.tiles) {
            if (this.tiles.indexOf(button) == 4) {
                MatteBorder border = new MatteBorder(2, 2, 2, 0, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 5) {
                MatteBorder border = new MatteBorder(2, 0, 0, 0, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 6) {
                MatteBorder border = new MatteBorder(2, 0, 2, 0, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 7) {
                MatteBorder border = new MatteBorder(2, 0, 2, 2, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 11) {
                MatteBorder border = new MatteBorder(2, 2, 2, 0, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 13) {
                MatteBorder border = new MatteBorder(2, 0, 0, 0, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 12 || this.tiles.indexOf(button) == 14) {
                MatteBorder border = new MatteBorder(2, 0, 2, 0, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 15) {
                MatteBorder border = new MatteBorder(2, 0, 2, 2, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 18) {
                MatteBorder border = new MatteBorder(2, 2, 2, 0, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 20) {
                MatteBorder border = new MatteBorder(2, 0, 0, 0, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 19 || this.tiles.indexOf(button) == 21 || this.tiles.indexOf(button) == 22) {
                MatteBorder border = new MatteBorder(2, 0, 2, 0, Color.red);
                button.setBorder(border);
            }
            if (this.tiles.indexOf(button) == 23) {
                MatteBorder border = new MatteBorder(2, 0, 2, 2, Color.red);
                button.setBorder(border);
            }
        }
        for (JButton button : this.papalCuoncilTiles) {
            if (button.isVisible()) {
                MatteBorder border = new MatteBorder(0, 2, 2, 2, Color.red);
                button.setBorder(border);
            }
        }
    }

    public void setBoardModel(Board boardModel) { this.faithMapModel = boardModel.getFaithMap(); }

    public void refresh() {
        this.markerPosition = this.faithMapModel.getMarkerPosition();
        for (JButton button : this.tiles) {
            button.setBackground(new Color(255, 255, 255));
        }
        this.tiles.get(this.markerPosition).setBackground(new Color(105, 105, 105));
    }

    public void moveForward() {
        this.faithMapModel.moveForward();
        this.tiles.get(this.markerPosition).setBackground(new Color(255, 255, 255));
        this.markerPosition++;
        this.tiles.get(this.markerPosition).setBackground(new Color(105, 105, 105));
    }

    public void disableAllButtons() {
        this.clickable = false;
    }

    public void enableAllButtons() {
        this.clickable = true;
    }

    public void setFaithMapByPlayer(Player mePlayer) {
        this.faithMapModel = mePlayer.getBoard().getFaithMap();
        refresh();
    }
}
