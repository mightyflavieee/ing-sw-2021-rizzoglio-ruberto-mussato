package it.polimi.ingsw.project.client.gui.market;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.listeners.LeaderCardChoserListener;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.playermove.ChooseLeaderCardMove;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderCardChoserGUI extends JFrame {
    private List<LeaderCardButtonGUI> leaderCardButtonGUIList;
    private List<String> chosedIDs;
    private List <LeaderCard> leadercards;
    private ClientGUI clientGUI;
    public LeaderCardChoserGUI(List<LeaderCard> leadercards, ClientGUI clientGUI) {
        this.setTitle("Leader Card Choser");
        this.clientGUI = clientGUI;
        this.leadercards = leadercards;
        this.chosedIDs = new ArrayList<>();
        this.leaderCardButtonGUIList = new ArrayList<>();
        this.setLayout(new GridLayout(2,2));
        for(String id : this.leadercards.stream().map(LeaderCard::getId).collect(Collectors.toList())){
            LeaderCardButtonGUI leaderCardButtonGUI = new LeaderCardButtonGUI(id, this);
            leaderCardButtonGUI.addActionListener(new LeaderCardChoserListener(leaderCardButtonGUI,this));
            this.leaderCardButtonGUIList.add(leaderCardButtonGUI);
            this.add(leaderCardButtonGUI);
        }
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public void selectID(String id) {
        this.chosedIDs.add(id);
        if(chosedIDs.size()==2){
            List<LeaderCard> leaderCardsToSend = new ArrayList<>();
            for(int j = 0; j < 2 ; j++) {
                for (int i = 0; i < this.leadercards.size(); i++) {
                    if (leadercards.get(i).getId().equals(this.chosedIDs.get(j))){
                        leaderCardsToSend.add(this.leadercards.remove(i));
                    }
                }
            }
            this.clientGUI.send(new ChooseLeaderCardMove(this.clientGUI.getNickname(), this.clientGUI.getGameId(), leaderCardsToSend));
            this.dispose();
        }

    }
}