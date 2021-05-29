package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SubmitButtonListener implements ActionListener {//it is uset for the submit button at the end of clientGUI.createGame
    private ClientGUI clientGUI;
    private JFrame menuFrame;
    private List<JComponent> previousButtons;

    public SubmitButtonListener(ClientGUI clientGUI, JFrame menuFrame, List<JComponent> previousButtons) {
        this.clientGUI = clientGUI;
        this.menuFrame = menuFrame;
        this.previousButtons = previousButtons;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean allperformed = true;
        for(JComponent jComponent : previousButtons){
            allperformed = allperformed && !jComponent.isEnabled();
        }
        if(!allperformed){
            return;
        }else {
            menuFrame.dispose();
            this.clientGUI.createOrJoinGame();
        }

    }
}
