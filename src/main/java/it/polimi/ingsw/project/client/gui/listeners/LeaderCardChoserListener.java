package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.LeaderCardChoserGUI;
import it.polimi.ingsw.project.client.gui.LeaderCardGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderCardChoserListener implements ActionListener {
    private LeaderCardChoserGUI leaderCardChoserGUI;
    private LeaderCardGUI leaderCardGUI;

    public LeaderCardChoserListener(LeaderCardGUI leaderCardGUI, LeaderCardChoserGUI leaderCardChoserGUI) {
        this.leaderCardChoserGUI = leaderCardChoserGUI;
        this.leaderCardGUI = leaderCardGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.leaderCardGUI.setEnabled(false);
        this.leaderCardChoserGUI.selectID(this.leaderCardGUI.getID());
    }
}
