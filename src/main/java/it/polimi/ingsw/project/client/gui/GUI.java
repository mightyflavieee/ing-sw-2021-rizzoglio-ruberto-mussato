package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.utils.Pair;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends Observable<Move> {
    private JFrame jFrame;
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
    private PlayersBarGUI playersBarGUI;


    public GUI(Match match, String myNickname) {
        this.jFrame = new JFrame();
        this.jFrame.setTitle("Master of Renaissance");
        this.jFrame.setLayout(new GridLayout(3,3));
        // jFrame.setLayout(new BorderLayout());

        //  boardGUI = new BoardGUI("Board");
        Pair<Player, List<Player>> pair = Utils.splitPlayers(match,myNickname);
        this.mePlayer = pair._1;
        this.opponentsPlayer = pair._2;
        this.marketGUI = new MarketGUI(this,match.getMarket());
        this.historyGUI = new HistoryGUI(this.mePlayer.getHistoryToString());
        this.informationsGUI = new InformationsGUI(this,this.mePlayer.getTurnPhase());
        this.cardContainerGUI = new CardContainerGUI(match.getCardContainer());
        this.leaderCardPlaceGui = new LeaderCardPlaceGUI(this.mePlayer.getLeaderCards(),this);
        this.playersBarGUI = new PlayersBarGUI(this.opponentsPlayer.stream().map(Player::getNickname).collect(Collectors.toList()), myNickname,this);
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
        this.jFrame.add(informationsGUI);
        this.jFrame.add(marketGUI);
        this.jFrame.add(cardContainerGUI);
        this.jFrame.add(leaderCardPlaceGui);
        this.jFrame.add(historyGUI);
        this.jFrame.add(playersBarGUI);
        //jFrame.pack();
        this.jFrame.setVisible(true);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.pack();
    }

    public void send(Move move){
        super.notify(move);
        this.disableAllButtons(); //devo attendere una risposta dal server
        //todo lo chiamo da un listener e mando la move al server

    }
    public void disableAllButtons(){
        //todo blocca tutte le cose che si possono fare es. quando devo aspettare una risposta dal server
    }
    public void setMatch(Match match){//todo chiama i metodi set di tutti i jinternalframe e aggiorna tutto
        Pair<Player, List<Player>> pair = Utils.splitPlayers(match,this.mePlayer.getNickname());
        this.mePlayer = pair._1;
        this.opponentsPlayer = pair._2;
        this.marketGUI.setMarket(match.getMarket());
        this.historyGUI.setMyHistory(this.mePlayer.getHistoryToString());
        this.informationsGUI.setTurnPhase(this.mePlayer.getTurnPhase());
        this.cardContainerGUI.setCardContainer(match.getCardContainer());
        this.leaderCardPlaceGui.setMyLeaderCards(this.mePlayer.getLeaderCards());
        //todo setter di altre cose
    }

    public void showMarketInformations() {
        this.informationsGUI.showMarketInformations();
    }

    public void showMyView() {
        //shows your view
        this.historyGUI.setMyHistory(this.mePlayer.getHistoryToString());
        this.informationsGUI.setTurnPhase(this.mePlayer.getTurnPhase());
        this.leaderCardPlaceGui.setMyLeaderCards(this.mePlayer.getLeaderCards());
        //todo setter di altre cose
    }

    public void showOpponentView(int index) {
        //show the view of the opponent opponentPLayers(index)
        this.historyGUI.setHistory(this.opponentsPlayer.get(index).getHistoryToString(),this.opponentsPlayer.get(index).getNickname());
        this.informationsGUI.showOpponentView(this.opponentsPlayer.get(index).getNickname());
        this.leaderCardPlaceGui.setLeaderCards(this.opponentsPlayer.get(index).getLeaderCards(),this.opponentsPlayer.get(index).getNickname());
        //todo setter di altre cose


        this.disableAllButtons();
    }
}

