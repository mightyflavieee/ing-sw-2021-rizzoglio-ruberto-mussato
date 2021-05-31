package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.utils.Pair;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame{
    private BoardGUI boardGUI;
    private MatchGUI matchGUI;
    private MarketGUI marketGUI;
//    private MarketButton marketButton;
//    private MarketButtonListener marketButtonListener;
    private LeaderCardPlaceGUI leaderCardPlaceGui;
    private CardContainerGUI cardContainerGUI;
    private FaithMapGUI faithMapGUI;
    private WarehouseGUI warehouseGUI;
    private ChestGUI chestGUI;
    private MapTrayGUI mapTrayGUI;
    private Player mePlayer;
    private List<Player> opponentsPlayer;
    private InformationsGUI informationsGUI;
    private HistoryGUI historyGUI;


    public GUI(Match match, String myNickname) {
        this.setTitle("Master of Renaissance");
        this.setLayout(new GridLayout(4,3));
       // jFrame.setLayout(new BorderLayout());

      //  boardGUI = new BoardGUI("Board");
        Pair<Player, List<Player>> pair = Utils.splitPlayers(match,myNickname);
        this.mePlayer = pair._1;
        this.opponentsPlayer = pair._2;
        marketGUI = new MarketGUI(this,match.getMarket());
        historyGUI = new HistoryGUI(this.mePlayer.getHistoryToString());
        informationsGUI = new InformationsGUI(this,this.mePlayer.getTurnPhase());
        cardContainerGUI = new CardContainerGUI(match.getCardContainer());
        leaderCardPlaceGui = new LeaderCardPlaceGUI(this.mePlayer.getLeaderCards());
        //todo inizializzatori di altre cose
        //faithMapGUI = new FaithMapGUI();
        //warehouseGUI = new WarehouseGUI();
        //chestGUI = new ChestGUI();
        //mapTrayGUI = new MapTrayGUI();




//        marketButton = new MarketButton("market button");
//        marketButtonListener = new MarketButtonListener(marketGUI);
//        marketButton.addActionListener(marketButtonListener);
//        jFrame.add(marketButton);
//        jFrame.add(new MarketGUI("altro market ma potrebbe essere board"));
//        jFrame.add(new MarketGUI("altro market ma potrebbe essere warehouse"));
//        marketGUI.doubleSize();

//        jFrame.add(marketGUI, BorderLayout.EAST);
//        jFrame.add(boardGUI, BorderLayout.NORTH);
//        jFrame.add(cardContainerGUI, BorderLayout.CENTER);
//        jFrame.add(leaderCardPlaceGui, BorderLayout.WEST);
//        jFrame.add(faithMapGUI);
//        jFrame.add(warehouseGUI);
//        jFrame.add(chestGUI);
//        jFrame.add(mapTrayGUI);
        this.add(informationsGUI);
        this.add(marketGUI);
        this.add(cardContainerGUI);
        this.add(leaderCardPlaceGui);
        this.add(historyGUI);
        //jFrame.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public void send(Move move){
        this.lockAll(); //devo attendere una risposta dal server
        //todo lo chiamo da un listener e mando la move al server

    }
    public void lockAll(){
        //todo blocca tutte le cose che si possono fare es. quando devo aspettare una risposta dal server
    }
    public void setMatch(Match match){//todo chiama i metodi set di tutti i jinternalframe e aggiorna tutto
        Pair<Player, List<Player>> pair = Utils.splitPlayers(match,this.mePlayer.getNickname());
        this.mePlayer = pair._1;
        this.opponentsPlayer = pair._2;
        this.marketGUI.setMarket(match.getMarket());
        this.historyGUI.setHistory(this.mePlayer.getHistoryToString());
        this.informationsGUI.setTurnPhase(this.mePlayer.getTurnPhase());
        this.cardContainerGUI.setCardContainer(match.getCardContainer());
        this.leaderCardPlaceGui.setLeaderCards(this.mePlayer.getLeaderCards());
        //todo setter di altre cose
    }

    public void showMarketInformations() {
        this.informationsGUI.showMarketInformations();
    }
}
