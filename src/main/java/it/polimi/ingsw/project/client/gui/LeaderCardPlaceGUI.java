package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.Status;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderCardPlaceGUI extends JInternalFrame {
    private List<LeaderCardJlabelGUI> leaderCardJlabelGUIList;
    private List<LeaderMovePanel> leaderMovePanelList;
    public LeaderCardPlaceGUI(List<LeaderCard> leaderCards, GUI gui) {
        this.setTitle("My Leader Cards");
        this.setLayout(new GridLayout(2,1));
        JPanel imagesPanel = new JPanel();
        imagesPanel.setLayout(new GridLayout(1,2));
        this.add(imagesPanel);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1,2));
        this.add(buttonsPanel);
        this.leaderCardJlabelGUIList = new ArrayList<>();
        this.leaderMovePanelList = new ArrayList<>();
        for(int i = 0; i < leaderCards.size(); i++) {
            this.leaderCardJlabelGUIList.add(new LeaderCardJlabelGUI(leaderCards.get(i).getId()));
            this.leaderMovePanelList.add(new LeaderMovePanel(leaderCards.get(i).getId(),gui));
        }

        for(int i = 0; i < leaderCardJlabelGUIList.size(); i++){
            imagesPanel.add(leaderCardJlabelGUIList.get(i));
        }
        for(int i = 0; i < leaderMovePanelList.size(); i++){
            buttonsPanel.add(leaderMovePanelList.get(i));
        }

        this.setVisible(true);
    }


    public void setMyLeaderCards(List<LeaderCard> leaderCards) {
        this.setTitle("My Leader Cards");
        int i;
        for(i = 0; i < leaderCards.size(); i++){
            leaderCardJlabelGUIList.get(i).setID(leaderCards.get(i).getId());
            this.leaderMovePanelList.get(i).setActivationPossible(leaderCards.get(i).getStatus()== Status.Inactive);
        }
        for(int j = i; j < 2; j++){
            this.leaderCardJlabelGUIList.get(j).setVisible(false);
            this.leaderMovePanelList.get(j).setVisible(false);
            this.leaderMovePanelList.get(j).setEnabled(false);
        }

    }
    public void setLeaderCards(List<LeaderCard> leaderCards, String opponentNickName){
        this.setMyLeaderCards(leaderCards);
        this.setTitle(opponentNickName + "'s Leader Cards");
    }
}
