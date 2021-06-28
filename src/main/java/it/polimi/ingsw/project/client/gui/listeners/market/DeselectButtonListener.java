package it.polimi.ingsw.project.client.gui.listeners.market;

import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandlerGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to make you deselect the resources that you selected before during the market move
 */
public class DeselectButtonListener implements ActionListener {
    private final ResourceInHandlerGUI resourceInHandlerGUI;
    private final ResourceInHandGUI resourceInHandGUI;
    public DeselectButtonListener(ResourceInHandlerGUI resourceInHandlerGUI, ResourceInHandGUI resourceInHandGUI) {
        this.resourceInHandlerGUI = resourceInHandlerGUI;
        this.resourceInHandGUI = resourceInHandGUI;
    }

    /**
     * it puts the resources back in the resources collected from the market
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.resourceInHandGUI.addResource(this.resourceInHandlerGUI.getResourceType(),this.resourceInHandlerGUI.getResourceNum());
        this.resourceInHandlerGUI.removeResource();
    }
}
