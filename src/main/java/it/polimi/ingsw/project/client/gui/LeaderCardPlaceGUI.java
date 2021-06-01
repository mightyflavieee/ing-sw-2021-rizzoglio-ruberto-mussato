package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderCardPlaceGUI extends JInternalFrame {
    private List<LeaderCardJlabelGUI> leaderCardJlabelGUIList;
    private List<LeaderMovePanel> leaderMovePanelList;
    public LeaderCardPlaceGUI(List<LeaderCard> leaderCards) {
        this.setTitle("My LeaderCards");
        this.setLayout(new GridLayout(2,2));
        this.leaderCardJlabelGUIList = new ArrayList<>();
        this.leaderMovePanelList = new ArrayList<>();
        for(int i = 0; i < leaderCards.size(); i++) {
            leaderCardJlabelGUIList.add(new LeaderCardJlabelGUI(leaderCards.get(i).getId()));
            this.leaderMovePanelList.add(new LeaderMovePanel(leaderCards.get(i).getId()));
        }
        for(int i = 0; i < leaderCardJlabelGUIList.size(); i++){
            this.add(leaderCardJlabelGUIList.get(i));
        }
        for(int i = 0; i < leaderMovePanelList.size(); i++){
            this.add(leaderMovePanelList.get(i));
        }

        this.setVisible(true);
    }


    public void setLeaderCards(List<LeaderCard> leaderCards) {
        for(int i = 0; i < leaderCards.size(); i++){
            leaderCardJlabelGUIList.get(i).setID(leaderCards.get(i).getId());
        }
//        if(leaderCards.size() > 0){
//            leaderCardGUI1.setID(leaderCards.get(0).getId());
//            if(leaderCards.size() == 2){
//                leaderCardGUI2.setID(leaderCards.get(1).getId());
//            }
//        }
    }
}
