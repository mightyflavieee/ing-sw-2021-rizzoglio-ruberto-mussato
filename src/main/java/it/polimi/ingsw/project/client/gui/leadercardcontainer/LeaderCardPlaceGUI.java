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
    private final List<LeaderCardJlabelGUI> leaderCardJlabelGUIList;
    private final List<LeaderMovePanel> leaderMovePanelList;
    private final JPanel imagesPanel;
    private final JPanel buttonsPanel;
    private final GUI gui;

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
        for (LeaderCard leaderCard : leaderCards) {
            this.leaderCardJlabelGUIList.add(new LeaderCardJlabelGUI(leaderCard.getId()));
            this.leaderMovePanelList.add(new LeaderMovePanel(leaderCard, gui));
        }

        for (LeaderCardJlabelGUI leaderCardJlabelGUI : leaderCardJlabelGUIList) {
            imagesPanel.add(leaderCardJlabelGUI);
        }
        for (LeaderMovePanel leaderMovePanel : leaderMovePanelList) {
            buttonsPanel.add(leaderMovePanel);
        }
        for(int i = 0; i < leaderCards.size(); i++){
            leaderMovePanelList.get(i).setActivationPossible(false);
            this.leaderMovePanelList.get(i).setActivated(leaderCards.get(i).getStatus()== Status.Active);
        }
        this.setVisible(true);
    }


    /**
     * shows the player's leaderCards and sets all the buttons
     */
    public void setMyLeaderCards(Player mePlayer) {
        List<LeaderCard> leaderCards = mePlayer.getLeaderCards();
        this.setTitle("My Leader Cards");
        refreshShowed(leaderCards);
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

    private void refreshShowed(List<LeaderCard> leaderCards) {
        for (int k = leaderCardJlabelGUIList.size(); k < leaderCards.size(); k++){
            LeaderCardJlabelGUI leaderCardJlabelGUI = new LeaderCardJlabelGUI(leaderCards.get(k).getId());
            leaderCardJlabelGUIList.add(leaderCardJlabelGUI);
            imagesPanel.add(leaderCardJlabelGUI);
            LeaderMovePanel leaderMovePanel = new LeaderMovePanel(leaderCards.get(k),gui);
            leaderMovePanelList.add(leaderMovePanel);
            buttonsPanel.add(leaderMovePanel);
            this.leaderMovePanelList.get(k).setActivated(leaderCards.get(k).getStatus()== Status.Active);
        }
    }

    /**
     * shows an opponent's leadercards and set all buttons to disabled
     */
    public void setOpponentLeaderCards(List<LeaderCard> leaderCards, String opponentNickName){
        int i;
        refreshShowed(leaderCards);
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
        this.leaderMovePanelList.forEach(LeaderMovePanel::enableButtonsForLeaderPhase);
    }

    /**
     * updates the size of the displayed pictures
     */
    public void refreshSize(int width, int height) {
        if(width < 2){
            width = 2;
        }
        if(height < 2){
            width = 2;
        }
        for (LeaderCardJlabelGUI leaderCardJlabelGUI : this.leaderCardJlabelGUIList) {
            leaderCardJlabelGUI.refreshSize(width / 2, (int) (height * 0.8));
        }
    }

    public void enableButtonsForProduction() {
        this.leaderMovePanelList.forEach(LeaderMovePanel::enableButtonsForProduction);
    }
}
