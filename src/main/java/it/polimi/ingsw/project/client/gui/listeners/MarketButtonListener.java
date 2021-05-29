package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.MarketGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarketButtonListener implements ActionListener {
    private MarketGUI marketGUI;

    public MarketButtonListener(MarketGUI marketGUI) {
        this.marketGUI = marketGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.marketGUI.isShowing()) {
            this.marketGUI.hide();
        } else {
            this.marketGUI.show();
        }
    }
}
