package it.polimi.ingsw.project.client.gui.listeners;


import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.NoMoveHandlerGUI;
import it.polimi.ingsw.project.model.playermove.NoMove;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoMoveButtonListener implements ActionListener {
    private GUI gui;
    private NoMoveHandlerGUI noMoveHandlerGUI;

    public NoMoveButtonListener(GUI gui, NoMoveHandlerGUI noMoveHandlerGUI) {
        this.gui = gui;
        this.noMoveHandlerGUI = noMoveHandlerGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.noMoveHandlerGUI.dispose();
        this.gui.send(new NoMove());
    }
}
