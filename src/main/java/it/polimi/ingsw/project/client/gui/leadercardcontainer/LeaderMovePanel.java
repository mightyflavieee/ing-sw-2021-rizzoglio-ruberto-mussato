package it.polimi.ingsw.project.client.gui.leadercardcontainer;

import it.polimi.ingsw.project.client.gui.GUI;
import it.polimi.ingsw.project.client.gui.listeners.leadercards.LeaderCardActivateButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.leadercards.LeaderCardDiscardButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.selectcard.SelectLeaderCardProductionListener;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import javax.swing.*;
import java.awt.*;

public class LeaderMovePanel extends JPanel {
    private final JButton discardButton;
    private final JButton activateButton;
    private final JButton productionButton;
    private boolean isActivable;
    private boolean isProductable;
    private final LeaderCardDiscardButtonListener leaderCardDiscardButtonListener;
    private final LeaderCardActivateButtonListener leaderCardActivateButtonListener;
    private final SelectLeaderCardProductionListener selectLeaderCardProductionListener;

    public LeaderMovePanel(LeaderCard leaderCard, GUI gui)  {
        this.setVisible(true);
        this.setPreferredSize(new Dimension(300,300));
        this.setLayout(new GridLayout(2,1));
        Panel leaderPhasePanel = new Panel();
        leaderPhasePanel.setLayout(new GridLayout(1,2));
        this.add(leaderPhasePanel);
        this.discardButton = new JButton("Discard");
        this.leaderCardDiscardButtonListener = new LeaderCardDiscardButtonListener(leaderCard.getId(), gui);
        this.discardButton.addActionListener(this.leaderCardDiscardButtonListener);
        leaderPhasePanel.add(discardButton);
        this.activateButton = new JButton("Activate");
        this.leaderCardActivateButtonListener = new LeaderCardActivateButtonListener(leaderCard.getId(), gui);
        this.activateButton.addActionListener(this.leaderCardActivateButtonListener);
        leaderPhasePanel.add(activateButton);
        this.productionButton = new JButton("Use for Production");

        SelectLeaderCardProductionListener productionActionListener = new SelectLeaderCardProductionListener(gui, leaderCard);
        this.selectLeaderCardProductionListener = productionActionListener;
        this.productionButton.addActionListener(productionActionListener);

        this.add(productionButton);
        this.isActivable = true;
        this.isProductable = false;
    }

    public void setActivationPossible(Boolean value){
        this.activateButton.setEnabled(value);
        this.isActivable = value;
    }

    public void setProductable(boolean productable) {
        isProductable = productable;
    }

    public void disableButtons() {
        this.discardButton.setEnabled(false);
        this.activateButton.setEnabled(false);
        this.productionButton.setEnabled(false);
    }

    public void enableButtonsForLeaderPhase() {
        this.disableButtons();
        this.discardButton.setEnabled(true);
        this.activateButton.setEnabled(this.isActivable);
           }

    public void setID(LeaderCard leaderCard) {
        this.leaderCardActivateButtonListener.setID(leaderCard.getId());
        this.leaderCardDiscardButtonListener.setID(leaderCard.getId());
        this.selectLeaderCardProductionListener.setLeaderCard(leaderCard);
    }

    public void setActivated(boolean b) {
        if(b){
            this.activateButton.setText("Activated");
        }else
        {
            this.activateButton.setText("Activate");
        }
    }

    public void enableButtonsForProduction() {
        this.disableButtons();
        this.productionButton.setEnabled(isProductable);
    }
}
