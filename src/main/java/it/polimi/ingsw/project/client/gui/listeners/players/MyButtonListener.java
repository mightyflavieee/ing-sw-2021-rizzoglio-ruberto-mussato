package it.polimi.ingsw.project.client.gui.listeners.players;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.PlayersBarGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButtonListener implements ActionListener {
    private GUI gui;
    private PlayersBarGUI playersBarGUI;

    public MyButtonListener(GUI gui, PlayersBarGUI playersBarGUI) {
        this.gui = gui;
        this.playersBarGUI = playersBarGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.showMyView();
        this.playersBarGUI.clickMyButton();
    }
}
