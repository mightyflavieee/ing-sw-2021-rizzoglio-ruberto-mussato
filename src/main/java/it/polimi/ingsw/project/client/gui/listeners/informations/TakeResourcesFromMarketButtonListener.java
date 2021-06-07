package it.polimi.ingsw.project.client.gui.listeners.informations;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TakeResourcesFromMarketButtonListener implements ActionListener {
    private GUI gui;

    public TakeResourcesFromMarketButtonListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getjTextArea().setText("Select row or column in the Market!");
        this.gui.getInformationsGUI().getMainPhaseHandler().disableAllButtons();
        this.gui.disableForTakeFromMarket();
        this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
    }
}
