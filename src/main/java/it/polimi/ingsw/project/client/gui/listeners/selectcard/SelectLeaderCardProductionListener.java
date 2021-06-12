package it.polimi.ingsw.project.client.gui.listeners.selectcard;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectLeaderCardProductionListener implements ActionListener {
    private GUI gui;
    private LeaderCard leaderCard;

    public SelectLeaderCardProductionListener(GUI gui, LeaderCard leaderCard) {
        this.gui = gui;
        this.leaderCard = leaderCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gui.getInformationsGUI().getProductionMoveHandler().setLeaderCard(this.leaderCard);
        this.gui.getLeaderCardPlaceGUI().disableButtons();
        switch (this.gui.getInformationsGUI().getProductionMoveHandler().getProductionType()) {
            case LeaderCard:
            case BoardAndLeaderCard:
                this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                this.gui.getInformationsGUI().createSelectResourcesHandlerForProduction();
                break;
            case DevCardAndLeader:
            case BoardAndDevCardAndLeaderCard:
                if (this.gui.getInformationsGUI().getProductionMoveHandler().getDevCard() != null) {
                    this.gui.getBoardGUI().getWarehouseGUI().enableAllButtons();
                    this.gui.getBoardGUI().getChestGUI().enableAllButtons();
                    this.gui.getInformationsGUI().createSelectResourcesHandlerForProduction();
                }
                break;
        }
    }
}
