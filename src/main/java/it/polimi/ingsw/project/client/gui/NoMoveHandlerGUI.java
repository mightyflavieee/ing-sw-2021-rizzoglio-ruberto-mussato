package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.informations.NoMoveButtonListener;

import javax.swing.*;
import java.awt.*;

public class NoMoveHandlerGUI extends JInternalFrame {
    private final JButton noMoveButton;

    public NoMoveHandlerGUI(String string, GUI gui) {
        this.setTitle("Leader Card Action Phase");
        this.setVisible(true);
       // this.setLayout(new GridLayout(2,1));
        this.setLayout(new BorderLayout());
        JTextArea jTextArea = new JTextArea("You can Activate or Discard a Leader Card by clicking on it.\n" +
                " If you want " + string + " and you don't want to perform a Leader Card Action click on Next Phase");
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        this.add(jTextArea,BorderLayout.NORTH);
        this.noMoveButton = new JButton("Next Phase");
        this.add(noMoveButton,BorderLayout.SOUTH);
        this.noMoveButton.addActionListener(new NoMoveButtonListener(gui,this));
        this.setVisible(true);
        this.pack();
    }
}
