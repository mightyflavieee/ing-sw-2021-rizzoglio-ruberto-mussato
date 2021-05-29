package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OneRadioButtonListener implements ActionListener {
    private ClientGUI clientGUI;
    private JRadioButton oneRadioButton, twoRadioButton, threeRadioButton, fourRadiobutton;

    public OneRadioButtonListener(ClientGUI clientGUI, JRadioButton oneRadioButton, JRadioButton twoRadioButton, JRadioButton threeRadioButton, JRadioButton fourRadiobutton) {
        this.clientGUI = clientGUI;
        this.oneRadioButton = oneRadioButton;
        this.twoRadioButton = twoRadioButton;
        this.threeRadioButton = threeRadioButton;
        this.fourRadiobutton = fourRadiobutton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.oneRadioButton.setEnabled(false);
        this.twoRadioButton.setEnabled(false);
        this.threeRadioButton.setEnabled(false);
        this.fourRadiobutton.setEnabled(false);
        this.clientGUI.setNumPlayers(1);

    }
}
