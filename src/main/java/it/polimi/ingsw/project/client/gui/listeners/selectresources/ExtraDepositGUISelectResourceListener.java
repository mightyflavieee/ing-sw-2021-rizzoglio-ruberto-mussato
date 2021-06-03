package it.polimi.ingsw.project.client.gui.listeners.selectresources;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.board.ChestGUI;
import it.polimi.ingsw.project.client.gui.board.ExtraDepositsGUI;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtraDepositGUISelectResourceListener implements ActionListener {
    private ExtraDepositsGUI extraDepositsGUI;
    private InformationsGUI informationsGUI;
    private ResourceType resourceType;

    public ExtraDepositGUISelectResourceListener (ExtraDepositsGUI extraDepositsGUI,
                                                  InformationsGUI informationsGUI,
                                                  ResourceType resourceType) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
