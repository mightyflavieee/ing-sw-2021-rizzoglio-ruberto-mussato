package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.resource.Resource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class ArrowObserver implements ActionListener {
    private TrayGui trayGui;
    private int axis, position;
    private ResourceInHandGUI resourceInHandGUI;
    public ArrowObserver(TrayGui trayGui, int axis, int position, ResourceInHandGUI resourceInHandGUI) {
        this.trayGui = trayGui;
        this.axis = axis;
        this.position = position;
        this.resourceInHandGUI = resourceInHandGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Resource> resourceList = this.trayGui.insertMarble(axis,position);
        this.trayGui.refresh();
        this.resourceInHandGUI.refresh(resourceList);
        this.trayGui.stopTray();
    }
}
