package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizeListener extends ComponentAdapter {
    private GUI gui;

    public ResizeListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        this.gui.refreshSize();
    }
}
