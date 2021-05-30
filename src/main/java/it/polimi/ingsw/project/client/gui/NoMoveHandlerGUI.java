package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.NoMoveButtonListener;

import javax.swing.*;
import java.awt.*;

public class NoMoveHandlerGUI extends JInternalFrame {
    private JButton noMoveButton;

    public NoMoveHandlerGUI(String string, GUI gui) {
        this.setTitle("Leader Card Action Phase");
        this.setVisible(true);
        this.setLayout(new GridLayout(2,1));
        JTextArea jTextArea = new JTextArea("You can Activate or Discard a Leader Card by clicking on it.\n" +
                " If you want " + string + " and you don't want to perform a Leader Card Action click on Next Phase");
        jTextArea.setEditable(false);
        this.add(jTextArea);
        this.noMoveButton = new JButton("Next Phase");
        this.add(noMoveButton);
        this.noMoveButton.addActionListener(new NoMoveButtonListener(gui,this));
        this.setVisible(true);
        this.pack();
    }
}
