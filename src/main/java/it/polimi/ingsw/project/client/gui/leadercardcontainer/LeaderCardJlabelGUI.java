package it.polimi.ingsw.project.client.gui.leadercardcontainer;

import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import java.awt.*;

public class LeaderCardJlabelGUI extends JLabel {
    private String id;
    private int width = 200, height = 325;


    public LeaderCardJlabelGUI(String id) { //used for the leadercard place
        this.id = id;
        //this.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/leadercards/"+ this.id + ".png").getImage().getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH)));
        this.setIcon(Utils.readIcon("leadercards/" + this.id + ".png",width,height));

        this.setVisible(true);
    }

    public void setID(String id) {
        this.id = id;
        this.refresh();
    }

    private void refresh() {
       // this.setIcon(new ImageIcon(new javax.swing.ImageIcon("src/main/resources/leadercards/"+ this.id + ".png").getImage().getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH)));
        this.setIcon(Utils.readIcon("leadercards/" + this.id + ".png",width,height));

    }

    public void refreshSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.refresh();
    }
}
