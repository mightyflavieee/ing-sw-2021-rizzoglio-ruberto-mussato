package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.ResourceInHandGUI;
import it.polimi.ingsw.project.client.gui.TrayGUI;
import it.polimi.ingsw.project.model.resource.Resource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ArrowListener implements ActionListener {
    private TrayGUI trayGui;
    private int axis, position;
    private ResourceInHandGUI resourceInHandGUI;
    private GUI gui;
    public ArrowListener(TrayGUI trayGui, int axis, int position, ResourceInHandGUI resourceInHandGUI, GUI gui) {
        this.trayGui = trayGui;
        this.axis = axis;
        this.position = position;
        this.resourceInHandGUI = resourceInHandGUI;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Resource> resourceList = this.trayGui.insertMarble(axis,position);
        this.trayGui.refresh();
        this.resourceInHandGUI.refresh(resourceList);
        this.trayGui.stopTray();
        this.gui.showMarketInformations();
    }
}
