package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.LeaderCardChoserGUI;
import it.polimi.ingsw.project.client.gui.LeaderCardButtonGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderCardChoserListener implements ActionListener {
    private LeaderCardChoserGUI leaderCardChoserGUI;
    private LeaderCardButtonGUI leaderCardButtonGUI;

    public LeaderCardChoserListener(LeaderCardButtonGUI leaderCardButtonGUI, LeaderCardChoserGUI leaderCardChoserGUI) {
        this.leaderCardChoserGUI = leaderCardChoserGUI;
        this.leaderCardButtonGUI = leaderCardButtonGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.leaderCardButtonGUI.setEnabled(false);
        this.leaderCardChoserGUI.selectID(this.leaderCardButtonGUI.getID());
    }
}
