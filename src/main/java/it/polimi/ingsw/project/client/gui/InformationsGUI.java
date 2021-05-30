package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.TurnPhase;

import javax.swing.*;

public class InformationsGUI extends JInternalFrame {
    private TurnPhase turnPhase;
    private JTextArea jTextArea;
    private JInternalFrame phaseFrame;
    private GUI gui;

    public InformationsGUI(GUI gui) {
        this.gui = gui;
        this.setTitle("Informations");
        this.jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        this.add(this.jTextArea);
        this.turnPhase = TurnPhase.WaitPhase;
      //  this.turnPhase = TurnPhase.InitialPhase;
        this.refresh();
        this.setVisible(true);
        this.pack();
    }

    public void refresh() {
        switch (turnPhase) {
            case InitialPhase:
                this.jTextArea.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to go on",this.gui);
                this.add(this.phaseFrame);
                break;
            case EndPhase:
                this.jTextArea.setVisible(false);
                this.phaseFrame = new NoMoveHandlerGUI("to end the turn", this.gui);
                this.add(this.phaseFrame);
                break;
            case MainPhase:
                if(this.phaseFrame != null){
                    this.phaseFrame.dispose();
                }
                this.jTextArea.setVisible(true);
                this.jTextArea.setText("You must choose and perform one of the following actions: \nTake Resources from the Market \nBuy one Development Card \n Activate the Production");
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
}
