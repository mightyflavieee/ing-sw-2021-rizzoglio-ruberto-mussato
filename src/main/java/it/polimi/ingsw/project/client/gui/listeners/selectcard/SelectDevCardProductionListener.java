package it.polimi.ingsw.project.client.gui.listeners.selectcard;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectDevCardProductionListener implements ActionListener {
    private final GUI gui;
    private final DevelopmentCard developmentCard;

    public SelectDevCardProductionListener(GUI gui, DevelopmentCard developmentCard) {
        this.gui = gui;
        this.developmentCard = developmentCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.gui.getBoardGUI().getMapTrayGUI().isClickable()) {
            this.gui.getInformationsGUI().getProductionMoveHandler().setDevCard(this.developmentCard);
            this.gui.getBoardGUI().getMapTrayGUI().disableAllButtons();
            switch (this.gui.getInformationsGUI().getProductionMoveHandler().getProductionType()) {
                case DevCard:
                case BoardAndDevCard:
                    this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                    this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                    this.gui.getInformationsGUI().createSelectResourcesHandlerForProduction();
                    break;
                case DevCardAndLeader:
                case BoardAndDevCardAndLeaderCard:
                    if (this.gui.getInformationsGUI().getProductionMoveHandler().getLeaderCard() != null) {
                        this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                        this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                        this.gui.getInformationsGUI().createSelectResourcesHandlerForProduction();
                        this.gui.getInformationsGUI().getMainPhaseHandler().goToAbortMovePanel();
                        this.gui.getInformationsGUI().showProductionInfo();
                    }
                    break;
            }
        }
    }
}
