package it.polimi.ingsw.project.client.gui.listeners.selectcard;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectCardForPurchaseListener implements ActionListener {
    private InformationsGUI informationsGUI;
    private DevelopmentCard developmentCard;

    public SelectCardForPurchaseListener(InformationsGUI informationsGUI, DevelopmentCard developmentCard) {
        this.informationsGUI = informationsGUI;
        this.developmentCard = developmentCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.informationsGUI.createSelectResourcesHandlerForPurchase(this.developmentCard);
        this.informationsGUI.getGUI().getBoardGUI().getWarehouseGUI().enableAllButtons();
        this.informationsGUI.getGUI().getBoardGUI().getChestGUI().enableAllButtons();
    }
}
