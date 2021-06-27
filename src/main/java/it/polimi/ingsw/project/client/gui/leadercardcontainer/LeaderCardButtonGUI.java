package it.polimi.ingsw.project.client.gui.leadercardcontainer;

import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;

public class LeaderCardButtonGUI extends JButton {
    private final String id;

    public LeaderCardButtonGUI(String id, LeaderCardChoserGUI leaderCardChoserGUI) { //used at the beginning of the game when you chose 2 leadercards
        this.id = id;
        this.setVisible(true);


        this.setIcon(Utils.readIcon("leadercards/" + this.id + ".png",200,300));

        // this.refresh();
    }

    public String getID() {
        return this.id;
    }

}
