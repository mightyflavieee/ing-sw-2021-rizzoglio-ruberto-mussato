package it.polimi.ingsw.project.client.gui.listeners;

import it.polimi.ingsw.project.client.gui.BoardGUI;
import it.polimi.ingsw.project.client.gui.MatchGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardGUIButtonListener implements ActionListener {
    private final MatchGUI matchGUI;
    private final BoardGUI boardGUI;

    public BoardGUIButtonListener(BoardGUI boardGUI, MatchGUI matchGUI) {
        this.matchGUI = matchGUI;
        this.boardGUI = boardGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.boardGUI.isShowing()) {
            this.boardGUI.hide();
            //this.matchGUI.setComponentZOrder(this.boardGUI, 0);
        } else {
            this.boardGUI.show();
            //this.matchGUI.setComponentZOrder(this.matchGUI.getPanel(), 0);
            //this.matchGUI.setComponentZOrder(this.boardGUI, 4);
        }
    }
}
