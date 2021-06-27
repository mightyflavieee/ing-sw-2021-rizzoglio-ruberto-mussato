package it.polimi.ingsw.project.client.gui.listeners.login;

import it.polimi.ingsw.project.client.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreeRadioButtonListener implements ActionListener {
    private final ClientGUI clientGUI;
    private final JRadioButton oneRadioButton;
    private final JRadioButton twoRadioButton;
    private final JRadioButton threeRadioButton;
    private final JRadioButton fourRadiobutton;

    public ThreeRadioButtonListener(ClientGUI clientGUI, JRadioButton oneRadioButton, JRadioButton twoRadioButton, JRadioButton threeRadioButton, JRadioButton fourRadiobutton) {
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
        this.clientGUI.setNumPlayers(3);

    }
}
