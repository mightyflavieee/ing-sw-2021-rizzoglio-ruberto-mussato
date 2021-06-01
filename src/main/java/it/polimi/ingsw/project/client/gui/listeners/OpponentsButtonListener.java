package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.PlayersBarGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpponentsButtonListener implements ActionListener {
    private GUI gui;
    private PlayersBarGUI playersBarGUI;
    private int index;

    public OpponentsButtonListener(GUI gui, PlayersBarGUI playersBarGUI,int index) {
        this.gui = gui;
        this.playersBarGUI = playersBarGUI;
        this.index = index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.showOpponentView(this.index);
        this.playersBarGUI.clickOpponentButton(this.index);
    }
}