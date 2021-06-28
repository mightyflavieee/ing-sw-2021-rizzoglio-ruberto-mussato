package it.polimi.ingsw.project.client.gui.leadercardcontainer;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.NewGameHandler;
import it.polimi.ingsw.project.client.gui.listeners.leadercards.LeaderCardChoserListener;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.messages.ChooseLeaderCardMove;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderCardChoserGUI extends JInternalFrame {
    private final List<String> chosedIDs;
    private final List <LeaderCard> leadercards;
    private final ClientGUI clientGUI;
    private final NewGameHandler newGameHandler;
    public LeaderCardChoserGUI(List<LeaderCard> leadercards, ClientGUI clientGUI, NewGameHandler newGameHandler) {
        this.setTitle("Select Two Leader Cards");
        this.clientGUI = clientGUI;
        this.leadercards = leadercards;
        this.chosedIDs = new ArrayList<>();
        List<LeaderCardButtonGUI> leaderCardButtonGUIList = new ArrayList<>();
        this.setLayout(new GridLayout(2,2));
        for(String id : this.leadercards.stream().map(LeaderCard::getId).collect(Collectors.toList())){
            LeaderCardButtonGUI leaderCardButtonGUI = new LeaderCardButtonGUI(id);
            leaderCardButtonGUI.addActionListener(new LeaderCardChoserListener(leaderCardButtonGUI,this));
            leaderCardButtonGUIList.add(leaderCardButtonGUI);
            this.add(leaderCardButtonGUI);
        }
        this.newGameHandler = newGameHandler;
        this.setVisible(true);
        this.setPreferredSize(new Dimension(400,600));
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    /**
     * @param id is added to the leadercards' chosed at the beginning of the game, if you have selected two leadercards it sends the selection to the server
     */
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
            this.newGameHandler.goToWaitingRoom(clientGUI.getGameId());
        }

    }
}