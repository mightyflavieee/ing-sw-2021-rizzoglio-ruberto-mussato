package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.playermove.ActivateLeaderCardMove;
import it.polimi.ingsw.project.model.playermove.DiscardLeaderCardMove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderCardActivateButtonListener implements ActionListener {
    private String id;
    private GUI gui;

    public LeaderCardActivateButtonListener(String id, GUI gui) {
        this.id = id;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.send(new ActivateLeaderCardMove(this.id));
    }
}