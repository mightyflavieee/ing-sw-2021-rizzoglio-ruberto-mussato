package it.polimi.ingsw.project.client.gui.leadercardcontainer;

import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;

public class LeaderCardJlabelGUI extends JLabel {
    private String id;
    private int width = 200, height = 325;


    public LeaderCardJlabelGUI(String id) { //used for the leadercard place
        this.id = id;
        this.setIcon(Utils.readIcon("leadercards/" + this.id + ".png",width,height));

        this.setVisible(true);
    }

    public void setID(String id) {
        this.id = id;
        this.refresh();
    }

    private void refresh() {
        this.setIcon(Utils.readIcon("leadercards/" + this.id + ".png",width,height));

    }

    /**
     * changes the size of the pictures
     */
    public void refreshSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.refresh();
    }
}
