package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.TurnPhase;

import javax.swing.*;
import java.awt.*;

public class InformationsGUI extends JInternalFrame {
    private TurnPhase turnPhase;
    private JLabel jLabel;
    private JInternalFrame phaseFrame;

    public InformationsGUI() {
        this.setTitle("Informations");
        this.jLabel = new JLabel();
        this.add(this.jLabel);
        this.turnPhase = TurnPhase.WaitPhase;
        //this.turnPhase = TurnPhase.MainPhase;
        this.refresh();
        this.setVisible(true);
        this.pack();
    }

    public void refresh() {
        switch (turnPhase) {
            case InitialPhase:
                this.jLabel.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to go on");
                this.add(this.phaseFrame);
                break;
            case EndPhase:
                this.jLabel.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to end the turn");
                this.add(this.phaseFrame);
                break;
            case MainPhase:
                if(this.phaseFrame != null){
                    this.phaseFrame.dispose();
                }
                this.jLabel.setVisible(true);
                this.jLabel.setText("You must choose and perform one of the following actions: \nTake Resources from the Market \nBuy one Development Card \n Activate the Production");
                break;
            case WaitPhase:
            default:
                if(this.phaseFrame != null){
                    this.phaseFrame.dispose();
                }
                this.jLabel.setVisible(true);
                this.jLabel.setText("It's not your turn");
                break;
        }
    }
}
