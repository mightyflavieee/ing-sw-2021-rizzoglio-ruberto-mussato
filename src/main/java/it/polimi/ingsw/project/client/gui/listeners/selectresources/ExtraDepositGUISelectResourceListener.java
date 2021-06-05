package it.polimi.ingsw.project.client.gui.listeners.selectresources;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.client.gui.board.ChestGUI;
import it.polimi.ingsw.project.client.gui.board.ExtraDepositsGUI;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtraDepositGUISelectResourceListener implements ActionListener {
    private ExtraDepositsGUI extraDepositsGUI;
    private InformationsGUI informationsGUI;
    private ResourceType resourceType;
    private int numOfExtraDeposit;
    private int buttonNumber;

    public ExtraDepositGUISelectResourceListener (ExtraDepositsGUI extraDepositsGUI,
                                                  InformationsGUI informationsGUI,
                                                  ResourceType resourceType,
                                                  int numOfExtraDeposit,
                                                  int buttonNumber) {
        this.extraDepositsGUI = extraDepositsGUI;
        this.informationsGUI = informationsGUI;
        this.resourceType = resourceType;
        this.numOfExtraDeposit = numOfExtraDeposit;
        this.buttonNumber = buttonNumber;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.extraDepositsGUI.isClickable()) {
            if (this.numOfExtraDeposit == 1) {
                this.extraDepositsGUI.getFirstExtraDepositButtons().get(buttonNumber).setIcon(new ImageIcon(
                        new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
            }
            if (this.numOfExtraDeposit == 2) {
                this.extraDepositsGUI.getSecondExtraDepositButtons().get(buttonNumber).setIcon(new ImageIcon(
                        new ImageIcon("src/main/resources/warehouse/warehouse_no_resource.png")
                                .getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH)));
            }
            this.informationsGUI.updateSelectResourcesHandler(this.resourceType, true);
        }
    }
}
