package it.polimi.ingsw.project.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderCardGUIListener implements ActionListener {
    private LeaderCardGUI leaderCardGUI;

    public LeaderCardGUIListener(LeaderCardGUI leaderCardGUI) {
        this.leaderCardGUI = leaderCardGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.leaderCardGUI.setVisible(false);
    }
}
