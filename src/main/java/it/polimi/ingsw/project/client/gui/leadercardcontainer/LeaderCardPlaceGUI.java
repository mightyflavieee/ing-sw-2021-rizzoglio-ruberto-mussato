package it.polimi.ingsw.project.client.gui.leadercardcontainer;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.Status;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.PerkType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderCardPlaceGUI extends JInternalFrame {
    private List<LeaderCardJlabelGUI> leaderCardJlabelGUIList;
    private List<LeaderMovePanel> leaderMovePanelList;
    private JPanel imagesPanel, buttonsPanel;
    private GUI gui;

    public LeaderCardPlaceGUI(List<LeaderCard> leaderCards, GUI gui) {
        this.gui = gui;
        this.setTitle("My Leader Cards");
        this.setLayout(new BorderLayout());
        imagesPanel = new JPanel();
        imagesPanel.setLayout(new GridLayout(1,2));
        this.add(imagesPanel,BorderLayout.NORTH);
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1,2));
        this.add(buttonsPanel,BorderLayout.CENTER);
        this.leaderCardJlabelGUIList = new ArrayList<>();
        this.leaderMovePanelList = new ArrayList<>();
        for(int i = 0; i < leaderCards.size(); i++) {
            this.leaderCardJlabelGUIList.add(new LeaderCardJlabelGUI(leaderCards.get(i).getId()));
            this.leaderMovePanelList.add(new LeaderMovePanel(leaderCards.get(i), gui));
        }

        for(int i = 0; i < leaderCardJlabelGUIList.size(); i++){
            imagesPanel.add(leaderCardJlabelGUIList.get(i));
        }
        for(int i = 0; i < leaderMovePanelList.size(); i++){
            buttonsPanel.add(leaderMovePanelList.get(i));
        }
        for(int i = 0; i < leaderCards.size(); i++){
            leaderMovePanelList.get(i).setActivationPossible(false);
            this.leaderMovePanelList.get(i).setActivated(leaderCards.get(i).getStatus()== Status.Active);
        }
        this.setVisible(true);
    }


    public void setMyLeaderCards(Player mePlayer) {
        List<LeaderCard> leaderCards = mePlayer.getLeaderCards();
        this.setTitle("My Leader Cards");
        for (int k = leaderCardJlabelGUIList.size(); k < leaderCards.size(); k++){
            LeaderCardJlabelGUI leaderCardJlabelGUI = new LeaderCardJlabelGUI(leaderCards.get(k).getId());
            leaderCardJlabelGUIList.add(leaderCardJlabelGUI);
            imagesPanel.add(leaderCardJlabelGUI);
            LeaderMovePanel leaderMovePanel = new LeaderMovePanel(leaderCards.get(k),gui);
            leaderMovePanelList.add(leaderMovePanel);
            buttonsPanel.add(leaderMovePanel);
            this.leaderMovePanelList.get(k).setActivated(leaderCards.get(k).getStatus()== Status.Active);
        }

        int i;
        for(i = 0; i < leaderCards.size(); i++){
            leaderCardJlabelGUIList.get(i).setID(leaderCards.get(i).getId());
            leaderCardJlabelGUIList.get(i).setVisible(true);
            this.leaderMovePanelList.get(i).setID(leaderCards.get(i));
            this.leaderMovePanelList.get(i).setActivationPossible(leaderCards.get(i).getStatus()== Status.Inactive
            && mePlayer.isFeasibleActivateLeaderCardMove(leaderCards.get(i).getId()));
            this.leaderMovePanelList.get(i).setProductable(leaderCards.get(i).getStatus()== Status.Active && leaderCards.get(i).getPerk().getType() == PerkType.Production);
            this.leaderMovePanelList.get(i).setActivated(leaderCards.get(i).getStatus()== Status.Active);
            this.leaderMovePanelList.get(i).setVisible(true);
        }
        for(int j = i; j < leaderCardJlabelGUIList.size(); j++){
            this.leaderCardJlabelGUIList.get(j).setVisible(false);
            this.leaderMovePanelList.get(j).setVisible(false);
            this.leaderMovePanelList.get(j).disableButtons();
        }

    }
    public void setOpponentLeaderCards(List<LeaderCard> leaderCards, String opponentNickName){
        for (int k = leaderCardJlabelGUIList.size(); k < leaderCards.size(); k++){
            LeaderCardJlabelGUI leaderCardJlabelGUI = new LeaderCardJlabelGUI(leaderCards.get(k).getId());
            leaderCardJlabelGUIList.add(leaderCardJlabelGUI);
            imagesPanel.add(leaderCardJlabelGUI);
            LeaderMovePanel leaderMovePanel = new LeaderMovePanel(leaderCards.get(k),gui);
            leaderMovePanelList.add(leaderMovePanel);
            buttonsPanel.add(leaderMovePanel);
            this.leaderMovePanelList.get(k).setActivated(leaderCards.get(k).getStatus()== Status.Active);
        }
        int i;
        for(i = 0; i < leaderCards.size(); i++){
            leaderCardJlabelGUIList.get(i).setID(leaderCards.get(i).getId());
            leaderCardJlabelGUIList.get(i).setVisible(true);
            leaderMovePanelList.get(i).setID(leaderCards.get(i));
            leaderMovePanelList.get(i).setVisible(true);
            leaderMovePanelList.get(i).disableButtons();
            this.leaderMovePanelList.get(i).setActivated(leaderCards.get(i).getStatus()== Status.Active);
        }        this.setTitle(opponentNickName + "'s Leader Cards");
        for(int j = i; j < leaderCardJlabelGUIList.size(); j++){
            leaderCardJlabelGUIList.get(i).setVisible(false);
            this.leaderMovePanelList.get(j).setVisible(false);
            this.leaderMovePanelList.get(i).disableButtons();

        }
        this.disableButtons();
    }

    public void disableButtons() {
        this.leaderMovePanelList.forEach(LeaderMovePanel::disableButtons);
    }

    public void enableButtonsForLeaderPhase() {
        this.leaderMovePanelList.forEach(x -> x.enableButtonsForLeaderPhase());
    }

    public void refreshSize(int width, int height) {
        if(width < 2){
            width = 2;
        }
        if(height < 2){
            width = 2;
        }
        for(int j = 0; j < this.leaderCardJlabelGUIList.size(); j++){
            this.leaderCardJlabelGUIList.get(j).refreshSize(width/2, (int) (height*0.8));
        }
    }

    public void enableButtonsForProduction() {
        this.leaderMovePanelList.forEach(x -> x.enableButtonsForProduction());
    }
}
