package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.players.MyButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.players.OpponentsButtonListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayersBarGUI extends JInternalFrame {
    private JButton myButton;
    private List<JButton> opponentsButtons;
    public PlayersBarGUI(List<String> opponentsIDs, String myNickname,GUI gui) {
        if(opponentsIDs.size() == 0){
            this.dispose();
            return;
        }
        this.setVisible(true);
        String myNickname1;
        this.setTitle("Players Bar");
        myNickname1 = myNickname;
        this.setLayout(new GridLayout(1,1+ opponentsIDs.size()));
        this.myButton = new JButton(myNickname1);
        this.myButton.addActionListener(new MyButtonListener(gui,this));
        this.myButton.setEnabled(false);
        this.add(myButton);
        this.opponentsButtons = new ArrayList<>();
        for(int i = 0; i < opponentsIDs.size(); i++){
            this.opponentsButtons.add(new JButton(opponentsIDs.get(i)));
            this.add(this.opponentsButtons.get(i));
            this.opponentsButtons.get(i).addActionListener(new OpponentsButtonListener(gui,this,i));
        }

    }

    /**
     * updates the buttons according to the fact that you clicked on your button
     */
    public void clickMyButton() {
        this.myButton.setEnabled(false);
        this.opponentsButtons.forEach(x -> x.setEnabled(true));
    }

    /**
     * updates the buttons according to the fact that you clicked on an opponent
     */
    public void clickOpponentButton(int index) {
        this.myButton.setEnabled(true);
        for(int i = 0; i < opponentsButtons.size(); i++){
            opponentsButtons.get(i).setEnabled(i != index);
        }
    }

    public void disableAllButtons(){
        clickMyButton();
        this.opponentsButtons.forEach(x -> x.setEnabled(false));
    }

}
