package it.polimi.ingsw.project.client.gui.leadercardcontainer;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.Warehouse;
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
        this.setLayout(new BorderLayout());
        JPanel imagesPanel = new JPanel();
        imagesPanel.setLayout(new GridLayout(1,2));
        this.add(imagesPanel,BorderLayout.NORTH);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1,2));
        this.add(buttonsPanel,BorderLayout.CENTER);
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
        for(int i = 0; i < leaderCards.size(); i++){
            leaderMovePanelList.get(i).setActivationPossible(false);
        }
        this.setVisible(true);
    }


    public void setMyLeaderCards(Player mePlayer) {
        List<LeaderCard> leaderCards = mePlayer.getLeaderCards();
        this.setTitle("My Leader Cards");
        int i;
        for(i = 0; i < leaderCards.size(); i++){
            leaderCardJlabelGUIList.get(i).setID(leaderCards.get(i).getId());
            this.leaderMovePanelList.get(i).setActivationPossible(leaderCards.get(i).getStatus()== Status.Inactive
            && mePlayer.isFeasibleActivateLeaderCardMove(leaderCards.get(i).getId()));
        }
        for(int j = i; j < 2; j++){
            this.leaderCardJlabelGUIList.get(j).setVisible(false);
            this.leaderMovePanelList.get(j).setVisible(false);
            this.leaderMovePanelList.get(j).setEnabled(false);
        }

    }
    public void setOpponentLeaderCards(List<LeaderCard> leaderCards, String opponentNickName){
        for(int i = 0; i < leaderCards.size(); i++){
            leaderCardJlabelGUIList.get(i).setID(leaderCards.get(i).getId());
        }        this.setTitle(opponentNickName + "'s Leader Cards");
        this.disableButtons();
    }

    public void disableButtons() {
        this.leaderMovePanelList.forEach(LeaderMovePanel::disableButtons);
    }

    public void enableButtons() {
        this.leaderMovePanelList.forEach(LeaderMovePanel::enableButtons);
    }
}