package it.polimi.ingsw.project.client.gui.listeners.players;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.PlayersBarGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to show your view when you click on the button with your name
 */
public class MyButtonListener implements ActionListener {
    private final GUI gui;
    private final PlayersBarGUI playersBarGUI;

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
