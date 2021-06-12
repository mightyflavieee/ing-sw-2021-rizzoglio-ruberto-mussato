package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.gui.listeners.players.MyButtonListener;
import it.polimi.ingsw.project.client.gui.listeners.players.OpponentsButtonListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayersBarGUI extends JInternalFrame {
    private List<String> opponentsIDs;
    private String myNickname;
    private JButton myButton;
    private List<JButton> opponentsButtons;
    public PlayersBarGUI(List<String> opponentsIDs, String myNickname,GUI gui) {
        if(opponentsIDs.size() == 0){
            this.dispose();
            return;
        }
        this.setVisible(true);
        this.opponentsIDs = opponentsIDs;
        this.myNickname = myNickname;
        this.setTitle("Players Bar");
        this.opponentsIDs = opponentsIDs;
        this.myNickname = myNickname;
        this.setLayout(new GridLayout(1,1+this.opponentsIDs.size()));
        this.myButton = new JButton(this.myNickname);
        this.myButton.addActionListener(new MyButtonListener(gui,this));
        this.myButton.setEnabled(false);
        this.add(myButton);
        this.opponentsButtons = new ArrayList<>();
        for(int i = 0; i < this.opponentsIDs.size(); i++){
            this.opponentsButtons.add(new JButton(this.opponentsIDs.get(i)));
            this.add(this.opponentsButtons.get(i));
            this.opponentsButtons.get(i).addActionListener(new OpponentsButtonListener(gui,this,i));
        }

    }

    public void clickMyButton() {
        this.myButton.setEnabled(false);
        this.opponentsButtons.forEach(x -> x.setEnabled(true));
    }

    public void clickOpponentButton(int index) {
        this.myButton.setEnabled(true);
        for(int i = 0; i < opponentsButtons.size(); i++){
            if(i == index){
                opponentsButtons.get(i).setEnabled(false);
            }else{
                opponentsButtons.get(i).setEnabled(true);
            }
        }
    }
}
