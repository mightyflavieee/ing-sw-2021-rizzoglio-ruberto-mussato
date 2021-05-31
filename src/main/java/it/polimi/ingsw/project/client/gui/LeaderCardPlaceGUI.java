package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeaderCardPlaceGUI extends JInternalFrame {
    private LeaderCardGUI leaderCardGUI1, leaderCardGUI2;
    public LeaderCardPlaceGUI(List<LeaderCard> leaderCards) {
        leaderCardGUI1 = new LeaderCardGUI(leaderCards.get(0).getId());
        leaderCardGUI2 = new LeaderCardGUI(leaderCards.get(1).getId());
        this.setTitle("My LeaderCards");
        this.setLayout(new GridLayout(1,2));
        this.add(leaderCardGUI1);
        this.add(leaderCardGUI2);
        this.setVisible(true);
    }


    public void setLeaderCards(List<LeaderCard> leaderCards) {
        if(leaderCards.size() > 0){
            leaderCardGUI1.setID(leaderCards.get(0).getId());
            if(leaderCards.size() == 2){
                leaderCardGUI2.setID(leaderCards.get(1).getId());
            }
        }
    }
}
