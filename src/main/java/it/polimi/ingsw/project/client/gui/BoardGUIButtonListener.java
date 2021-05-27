package it.polimi.ingsw.project.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardGUIButtonListener implements ActionListener {
    private final BoardGUI boardGUI;

    public BoardGUIButtonListener(BoardGUI boardGUI) {
        this.boardGUI = boardGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.boardGUI.isShowing()) {
            this.boardGUI.hide();
        } else {
            this.boardGUI.show();
        }
    }
}
