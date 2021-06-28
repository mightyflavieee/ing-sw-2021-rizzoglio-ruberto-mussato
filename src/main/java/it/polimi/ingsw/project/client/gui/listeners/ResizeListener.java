package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.GUI;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * it is used to change the dimensions of each picture of the GUI when you change the side of the outer Jframe
 */
public class ResizeListener extends ComponentAdapter {
    private final GUI gui;

    public ResizeListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        this.gui.refreshSize();
    }
}
