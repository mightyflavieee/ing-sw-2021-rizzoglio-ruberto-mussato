package it.polimi.ingsw.project.client.gui.listeners.leadercards;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.messages.DiscardLeaderCardMove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used when you want to activate a leader card
 */
public class LeaderCardDiscardButtonListener implements ActionListener {
    private String id;
    private final GUI gui;

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
