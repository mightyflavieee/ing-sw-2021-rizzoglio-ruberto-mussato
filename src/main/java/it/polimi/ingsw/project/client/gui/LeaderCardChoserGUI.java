package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderCardChoserGUI extends JFrame {
    private List<LeaderCardGUI> leaderCardGUIList;
    private List<String> leadercardsIDs, chosedIDs;
    public LeaderCardChoserGUI(List<String> leadercardIDs) {
        this.setTitle("Leader Card Choser");
        this.leadercardsIDs = leadercardIDs;
        this.chosedIDs = new ArrayList<>();
        this.leaderCardGUIList = new ArrayList<>();
        this.setLayout(new GridLayout(2,2));
        for(String id : leadercardIDs){
            LeaderCardGUI leaderCardGUI = new LeaderCardGUI(id, this);
            this.leaderCardGUIList.add(leaderCardGUI);
            this.add(leaderCardGUI);
        }
        this.setVisible(true);
        this.pack();
    }


}