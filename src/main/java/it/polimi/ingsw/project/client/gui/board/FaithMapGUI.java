package it.polimi.ingsw.project.client.gui.board;

import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.faithMap.FaithMap;
import it.polimi.ingsw.project.model.board.faithMap.PapalSlotStatus;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FaithMapGUI extends JInternalFrame {
    private final List<JButton> tiles;
    private final List<JButton> papalCuoncilTiles;
    private int markerPosition;
    private int blackMarkerPosition;
    private FaithMap faithMapModel;
    private final boolean isSinglePlayer;

    public FaithMapGUI(FaithMap faithMap, boolean isSinglePlayer) {
        this.setTitle("FaithMap");
        this.setVisible(true);
        this.setLayout(new GridLayout(2, 24));
        this.isSinglePlayer = isSinglePlayer;
        this.markerPosition = 0;
        this.blackMarkerPosition = 0;
        this.faithMapModel = faithMap;
        this.tiles = new ArrayList<>();
        this.papalCuoncilTiles = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            JButton button = new JButton();
            button.setBackground(new Color(255, 255, 255));
            switch (i+1) {
                case 3:
                    button.setBackground(Color.YELLOW);
                    button.setText("1");
                    break;
                case 6:
                    button.setBackground(Color.YELLOW);
                    button.setText("2");
                    break;
                case 9:
                    button.setBackground(Color.YELLOW);
                    button.setText("4");
                    break;
                case 12:
                    button.setBackground(Color.YELLOW);
                    button.setText("6");
                    break;
                case 15:
                    button.setBackground(Color.YELLOW);
                    button.setText("9");
                    break;
                case 18:
                    button.setBackground(Color.YELLOW);
                    button.setText("12");
                    break;
                case 21:
                    button.setBackground(Color.YELLOW);
                    button.setText("16");
                    break;
                default:
                    button.setText(String.valueOf(i+1));
                    break;
            }
            this.tiles.add(button);
        }
        for (int i = 0; i < 23; i++) {
            JButton button = new JButton();
            if (i != 5 && i != 13 && i != 20) {
                button.setVisible(false);
            }
            if (i == 5) {
                button.setIcon(Utils.readIcon("tiles/quadrato giallo.png",30,30));
            }
            if (i == 13) {
                button.setIcon(Utils.readIcon("tiles/quadrato arancione.png",30,30));
            }
            if (i == 20) {
                button.setIcon(Utils.readIcon("tiles/quadrato rosso.png",30,30));
            }
            this.papalCuoncilTiles.add(button);
        }
        setBordersForCouncil();
        for (JButton button : this.tiles) {
            this.add(button);
        }
        for (JButton button : this.papalCuoncilTiles) {
            this.add(button);
        }
        refresh();
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

    /**
     * @param boardModel is set as the local model
     */
    public void setBoardModel(Board boardModel) { this.faithMapModel = boardModel.getFaithMap(); }

    /**
     * it updates the local representation based on the local model
     */
    public void refresh() {
        this.markerPosition = this.faithMapModel.getMarkerPosition();
        this.blackMarkerPosition = this.faithMapModel.getBlackMarkerPosition();
        for (int i = 0; i < this.tiles.size(); i++) {
            setDefaulStyleOfTile(this.tiles.get(i), i);
        }
        for (int i = 0; i < this.faithMapModel.getPapalFavourSlots().size(); i++) {
            if (this.faithMapModel.getPapalFavourSlots().get(i).getStatus() == PapalSlotStatus.Taken) {
                switch (i) {
                    case 0:
                        this.papalCuoncilTiles.get(5).setIcon(null);
                        this.papalCuoncilTiles.get(5).setBackground(Color.GREEN);
                        this.papalCuoncilTiles.get(5).setText("VP:2");
                        break;
                    case 1:
                        this.papalCuoncilTiles.get(13).setIcon(null);
                        this.papalCuoncilTiles.get(13).setBackground(Color.GREEN);
                        this.papalCuoncilTiles.get(13).setText("VP:3");
                        break;
                    case 2:
                        this.papalCuoncilTiles.get(20).setIcon(null);
                        this.papalCuoncilTiles.get(20).setBackground(Color.GREEN);
                        this.papalCuoncilTiles.get(20).setText("VP:4");
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        this.papalCuoncilTiles.get(5).setText("");
                        this.papalCuoncilTiles.get(5).setIcon(Utils.readIcon("tiles/quadrato giallo.png",30,30));
                        break;
                    case 1:
                        this.papalCuoncilTiles.get(5).setText("");
                        this.papalCuoncilTiles.get(13).setIcon(Utils.readIcon("tiles/quadrato arancione.png",30,30));
                        break;
                    case 2:
                        this.papalCuoncilTiles.get(5).setText("");
                        this.papalCuoncilTiles.get(20).setIcon(Utils.readIcon("tiles/quadrato rosso.png",30,30));
                        break;
                }
            }
        }
        if(blackMarkerPosition > 0) {
            if (this.isSinglePlayer) {
                this.tiles.get(this.blackMarkerPosition-1).setBackground(new Color(0, 0, 0));
                this.tiles.get(this.blackMarkerPosition-1).setForeground(new Color(255, 255, 255));
            }
        }
        if(markerPosition > 0) {
            this.tiles.get(this.markerPosition-1).setBackground(new Color(105, 105, 105));
            this.tiles.get(this.markerPosition-1).setForeground(new Color(255, 255, 255));
        }
    }

    private void setDefaulStyleOfTile(JButton jButton, int i) {
        switch (i+1) {
            case 3:
            case 6:
            case 9:
            case 12:
            case 15:
            case 18:
            case 21:
                jButton.setBackground(Color.YELLOW);
                this.tiles.get(i).setForeground(new Color(0, 0, 0));
                break;
            default:
                this.tiles.get(i).setBackground(new Color(255, 255, 255));
                this.tiles.get(i).setForeground(new Color(0, 0, 0));
                break;
        }
    }

    /**
     * updates the marker position of the local model and updates the visual representation
     */
    public void moveForward() {
        this.faithMapModel.moveForward();
        refresh();
    }

    /**
     * updates the local model and the visual representation based on the input player
     */
    public void setFaithMapByPlayer(Player mePlayer) {
        this.faithMapModel = mePlayer.getBoard().getFaithMap();
        refresh();
    }
}
