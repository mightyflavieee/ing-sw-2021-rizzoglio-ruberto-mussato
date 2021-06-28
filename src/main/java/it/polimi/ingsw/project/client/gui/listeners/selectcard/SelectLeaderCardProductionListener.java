package it.polimi.ingsw.project.client.gui.listeners.selectcard;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectLeaderCardProductionListener implements ActionListener {
    private final GUI gui;
    private LeaderCard leaderCard;

    public SelectLeaderCardProductionListener(GUI gui, LeaderCard leaderCard) {
        this.gui = gui;
        this.leaderCard = leaderCard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean isAlreadyClicked = false;
        if (this.gui.getInformationsGUI().getProductionMoveHandler().getLeaderCards() != null) {
            for (LeaderCard leaderCard : this.gui.getInformationsGUI().getProductionMoveHandler().getLeaderCards()) {
                if (leaderCard.getId().equals(this.leaderCard.getId())) {
                    isAlreadyClicked = true;
                    break;
                }
            }
        }
        if (!isAlreadyClicked) {
            this.gui.getInformationsGUI().getProductionMoveHandler().setLeaderCard(this.leaderCard);
            this.gui.getInformationsGUI().getjTextArea().setText("Select the resource you want to produce with the Leader Card:" +
                    "\n(additional to the Faith point)");
            //this.gui.getLeaderCardPlaceGUI().disableButtons();
            this.gui.getInformationsGUI().getMainPhaseHandler().goToLeaderProductionButtons();
        }
    }

    public void setLeaderCard(LeaderCard leaderCard) {
        this.leaderCard = leaderCard;
    }
}
