package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.informations.NoMoveButtonListener;

import javax.swing.*;
import java.awt.*;

/**
 * it is used in the informations gui during a leader card phase and it informs you about that
 */
public class NoMoveHandlerGUI extends JInternalFrame {

    public NoMoveHandlerGUI(String string, GUI gui) {
        this.setTitle("Leader Card Action Phase");
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        JTextArea jTextArea = new JTextArea("You can Activate or Discard a Leader Card by clicking on it.\n" +
                " If you want " + string + " and you don't want to perform a Leader Card Action click on Next Phase");
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        this.add(jTextArea,BorderLayout.NORTH);
        JButton noMoveButton = new JButton("Next Phase");
        this.add(noMoveButton,BorderLayout.SOUTH);
        noMoveButton.addActionListener(new NoMoveButtonListener(gui,this));
        this.setVisible(true);
        this.pack();
    }
}
