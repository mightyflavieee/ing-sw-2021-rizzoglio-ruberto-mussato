package it.polimi.ingsw.project.client.gui.listeners.selectcard;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectDevCardProductionListener implements ActionListener {
    private GUI gui;
    private DevelopmentCard developmentCard;

    public SelectDevCardProductionListener(GUI gui, DevelopmentCard developmentCard) {
        this.gui = gui;
        this.developmentCard = developmentCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getProductionMoveHandler().setDevCard(this.developmentCard);
        this.gui.getBoardGUI().getMapTrayGUI().disableAllButtons();
        switch (this.gui.getInformationsGUI().getProductionMoveHandler().getProductionType()) {
            case DevCard:
            case BoardAndDevCard:
                this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                this.gui.getInformationsGUI().showProductionInfo();
                break;
            case DevCardAndLeader:
            case BoardAndDevCardAndLeaderCard:
                if (this.gui.getInformationsGUI().getProductionMoveHandler().getLeaderCard() != null) {
                    this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                    this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                    this.gui.getInformationsGUI().showProductionInfo();
                }
                break;
        }
    }
}
