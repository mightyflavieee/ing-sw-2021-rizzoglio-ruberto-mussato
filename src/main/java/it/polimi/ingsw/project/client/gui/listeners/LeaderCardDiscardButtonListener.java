package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.playermove.DiscardLeaderCardMove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderCardDiscardButtonListener implements ActionListener {
    private String id;
    private GUI gui;

    public LeaderCardDiscardButtonListener(String id, GUI gui) {
        this.id = id;
        this.gui = gui;
    }

    public void setID(String id) {
        this.id = id;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.send(new DiscardLeaderCardMove(this.id));
    }
}
