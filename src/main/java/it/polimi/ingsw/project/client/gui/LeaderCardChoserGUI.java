package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.listeners.LeaderCardChoserListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderCardChoserGUI extends JFrame {
    private List<LeaderCardGUI> leaderCardGUIList;
    private List<String> leadercardsIDs, chosedIDs;
    private ClientGUI clientGUI;
    public LeaderCardChoserGUI(List<String> leadercardsIDs, ClientGUI clientGUI) {
        this.setTitle("Leader Card Choser");
        this.clientGUI = clientGUI;
        this.leadercardsIDs = leadercardsIDs;
        this.chosedIDs = new ArrayList<>();
        this.leaderCardGUIList = new ArrayList<>();
        this.setLayout(new GridLayout(2,2));
        for(String id : this.leadercardsIDs){
            LeaderCardGUI leaderCardGUI = new LeaderCardGUI(id, this);
            leaderCardGUI.addActionListener(new LeaderCardChoserListener(leaderCardGUI,this));
            this.leaderCardGUIList.add(leaderCardGUI);
            this.add(leaderCardGUI);
        }
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public void selectID(String id) {
        this.chosedIDs.add(id);
        if(chosedIDs.size()==2){
            //todo inserire clientGUI.inviaLeaderCards
            this.dispose();
        }

    }
}