package it.polimi.ingsw.project.client.gui;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PlayersBarGUI extends JInternalFrame {
    private List<String> opponentsIDs;
    private String myNickname;
    private JButton myButton;
    private List<JButton> jButtons;
    public PlayersBarGUI(List<String> opponentsIDs, String myNickname) {
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
        this.add(myButton);
        this.jButtons = new ArrayList<>();
        for(int i = 0; i < this.opponentsIDs.size(); i++){
            this.jButtons.add(new JButton(this.opponentsIDs.get(i)));
            this.add(this.jButtons.get(i));
        }

    }
}
