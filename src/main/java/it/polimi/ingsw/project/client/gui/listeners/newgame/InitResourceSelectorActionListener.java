package it.polimi.ingsw.project.client.gui.listeners.newgame;

import it.polimi.ingsw.project.client.gui.InitResourceSelectorPanel;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitResourceSelectorActionListener implements ActionListener {
    private ResourceType resourceType;
    private InitResourceSelectorPanel initResourceSelectorPanel;

    public InitResourceSelectorActionListener(ResourceType resourceType, InitResourceSelectorPanel initResourceSelectorPanel) {
        this.resourceType = resourceType;
        this.initResourceSelectorPanel = initResourceSelectorPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.initResourceSelectorPanel.addResource(resourceType);


    }
}
