package it.polimi.ingsw.project.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArrowObserver implements ActionListener {
    private TrayGui trayGui;
    private int axis, position;

    public ArrowObserver(TrayGui trayGui, int axis, int position) {
        this.trayGui = trayGui;
        this.axis = axis;
        this.position = position;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.trayGui.insertMarble(axis,position);
        this.trayGui.refresh();
    }
}
