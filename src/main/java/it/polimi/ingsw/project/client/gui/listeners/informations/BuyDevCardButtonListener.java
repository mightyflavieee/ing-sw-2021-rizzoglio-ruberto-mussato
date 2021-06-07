package it.polimi.ingsw.project.client.gui.listeners.informations;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyDevCardButtonListener implements ActionListener {
    private GUI gui;

    public BuyDevCardButtonListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getjTextArea().setText("Select the position in which you want\n" +
                "to put the card:");
        this.gui.getInformationsGUI().getMainPhaseHandler().goToBuyDevCardButtons();
    }
}
