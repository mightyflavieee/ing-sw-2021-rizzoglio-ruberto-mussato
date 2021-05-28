package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class FaithMapGUI extends JInternalFrame {
    private final List<JButton> tiles;
    private final List<JButton> papalCuoncilTiles;

    public FaithMapGUI() {
        this.setTitle("FaithMap");
        this.setVisible(true);
        this.setLayout(new GridLayout(2, 24));
        this.tiles = new ArrayList<>();
        this.papalCuoncilTiles = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            this.tiles.add(new JButton());
        }
        for (int i = 0; i < 23; i++) {
            JButton button = new JButton();
            if (i != 5 && i != 13 && i != 20) {
                button.setVisible(false);
            }
            this.papalCuoncilTiles.add(button);
        }
        setTilesImages();
        setBordersForCouncil();
        for (JButton button : this.tiles) {
            this.add(button);
        }
        for (JButton button : this.papalCuoncilTiles) {
            this.add(button);
        }
    }

    private void setTilesImages() {
        setTileImage(this.papalCuoncilTiles.get(0), "src/main/resources/quadrato giallo.png");
        setTileImage(this.papalCuoncilTiles.get(1), "src/main/resources/quadrato arancione.png");
        setTileImage(this.papalCuoncilTiles.get(2), "src/main/resources/quadrato rosso.png");
    }

    private void setTileImage(JButton tile, String file) {
        tile.setIcon(new ImageIcon(new javax.swing.ImageIcon(file).getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
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

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(true);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FaithMapGUI());
    }
}
