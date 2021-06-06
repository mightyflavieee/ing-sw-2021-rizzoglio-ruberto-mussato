package it.polimi.ingsw.project.client.gui.listeners.informations;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActivateProductionButtonListener implements ActionListener {
    private GUI gui;

    public ActivateProductionButtonListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getjTextArea().setText("Select Production type:");
        this.gui.getInformationsGUI().getMainPhaseHandler().goToProductionButtons();
        this.gui.disableForProduction();
    }
}
