package it.polimi.ingsw.project.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderCardGUIListener implements ActionListener {
    private LeaderCardGUI leaderCardGUI;
    private LeaderMoveHandler leaderMoveHandler;

    public LeaderCardGUIListener(LeaderCardGUI leaderCardGUI) {
        this.leaderCardGUI = leaderCardGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //this.leaderCardGUI.setEnabled(false);
        if(this.leaderMoveHandler == null){
            this.leaderMoveHandler = new LeaderMoveHandler(this.leaderCardGUI);
        }
        if (!this.leaderMoveHandler.isShowing()) {
            this.leaderMoveHandler = new LeaderMoveHandler(this.leaderCardGUI);
        }
    }
}
