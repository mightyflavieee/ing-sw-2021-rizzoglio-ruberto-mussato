package it.polimi.ingsw.project.client.gui.listeners.selectcard;

import it.polimi.ingsw.project.client.gui.InformationsGUI;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * it is used to select a development card from the common CardContainer when you want to buy it
 */
public class SelectCardForPurchaseListener implements ActionListener {
    private final InformationsGUI informationsGUI;
    private final DevelopmentCard developmentCard;

    public SelectCardForPurchaseListener(InformationsGUI informationsGUI, DevelopmentCard developmentCard) {
        this.informationsGUI = informationsGUI;
        this.developmentCard = developmentCard;
    }

    /**
     * enables the button in the board in order to select the resources that you need to buy the development card
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.informationsGUI.createSelectResourcesHandlerForPurchase(this.developmentCard);
        this.informationsGUI.getGUI().getBoardGUI().getWarehouseGUI().enableAllButtons();
        this.informationsGUI.getGUI().getBoardGUI().getChestGUI().enableAllButtons();
    }
}
