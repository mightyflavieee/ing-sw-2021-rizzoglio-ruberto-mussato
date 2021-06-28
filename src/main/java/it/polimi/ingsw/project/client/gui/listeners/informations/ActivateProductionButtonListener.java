package it.polimi.ingsw.project.client.gui.listeners.informations;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used during the main phase to make you choose which type of move you want to do
 */
public class ActivateProductionButtonListener implements ActionListener {
    private final GUI gui;

    public ActivateProductionButtonListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getBoardGUI().getWarehouseGUI().addSelectResourceListeners();
        this.gui.getInformationsGUI().getjTextArea().setText("Select Production type:");
        this.gui.getInformationsGUI().getMainPhaseHandler().goToProductionButtons();
    }
}
