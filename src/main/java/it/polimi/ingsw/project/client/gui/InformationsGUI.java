package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.TurnPhase;

import javax.swing.*;
import java.awt.*;

public class InformationsGUI extends JInternalFrame {
    private TurnPhase turnPhase;
    private JTextArea jTextArea;
    private JInternalFrame phaseFrame;
    private GUI gui;

    public InformationsGUI(GUI gui, TurnPhase turnPhase) {
        this.gui = gui;
        this.setTitle("Informations");
        this.setLayout(new FlowLayout());
        this.jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        this.add(this.jTextArea);
        this.turnPhase = turnPhase;
        this.refresh();
        this.setVisible(true);
        this.pack();
    }

    public void refresh() {
        switch (turnPhase) {
            case InitialPhase:
                if(this.phaseFrame!=null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to go on",this.gui);
                this.add(this.phaseFrame);
                break;
            case EndPhase:
                if(this.phaseFrame!=null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to end the turn", this.gui);
                this.add(this.phaseFrame);
                break;
            case MainPhase:
                if(this.phaseFrame != null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(true);
                this.jTextArea.setText("You must choose and perform one of the following actions:" +
                        "\nTake Resources from the Market" +
                        "\nBuy one Development Card" +
                        "\nActivate the Production");
                break;
            case WaitPhase:
            default:
                if(this.phaseFrame != null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(true);
                this.jTextArea.setText("It's not your turn");
                break;
        }
    }

    public void showMarketInformations(){
        this.jTextArea.setText("You collected some resources from the Market!\n" +
                "You can see them in the Resources in Hand panel" +
                "\nStore them in the Shelves");

    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
        this.refresh();
    }
}
