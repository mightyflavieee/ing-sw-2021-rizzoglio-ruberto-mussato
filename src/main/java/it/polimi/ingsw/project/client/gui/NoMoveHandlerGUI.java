package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;

public class NoMoveHandlerGUI extends JInternalFrame {
    private JButton noMoveButton;

    public NoMoveHandlerGUI(String string) {
        this.setTitle("Leader Card Action Phase");
        this.setVisible(true);
        this.setLayout(new GridLayout(2,1));
        this.add(new JLabel("You can Activate or Discard a Leader Card by clicking on it.\n If you want " + string + " and you don't want to perform a Leader Card Action click on Next Phase"));
        this.noMoveButton = new JButton("Next Phase");
        this.add(noMoveButton);
        this.setVisible(true);
        this.pack();
    }
}
