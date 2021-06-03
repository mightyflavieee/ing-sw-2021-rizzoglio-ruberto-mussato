package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.TakeMarketResourceBuilder;
import it.polimi.ingsw.project.client.gui.board.*;
import it.polimi.ingsw.project.client.gui.market.LeaderCardPlaceGUI;
import it.polimi.ingsw.project.client.gui.market.MarketGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.TurnPhase;
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
    private TakeMarketResourceBuilder takeMarketResourceBuilder;


    public GUI(Match match, String myNickname) {
        this.jFrame = new JFrame();
        this.jFrame.setTitle("Master of Renaissance");
        //this.jFrame.setLayout(new GridLayout(3,3));
        jFrame.setLayout(new BorderLayout());

        //  boardGUI = new BoardGUI("Board");
        Pair<Player, List<Player>> pair = Utils.splitPlayers(match,myNickname);
        this.mePlayer = pair._1;
        this.opponentsPlayer = pair._2;
        this.informationsGUI = new InformationsGUI(this,this.mePlayer.getTurnPhase());
        this.boardGUI = new BoardGUI(myNickname, this.informationsGUI, this.mePlayer.getBoard());
        this.marketGUI = new MarketGUI(this,match.getMarket(),informationsGUI);
        this.historyGUI = new HistoryGUI(this.mePlayer.getHistoryToString());
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

        this.jFrame.add(boardGUI, BorderLayout.NORTH);
        //this.jFrame.add(informationsGUI, BorderLayout.NORTH);
        //this.jFrame.add(historyGUI, BorderLayout.NORTH);
        this.jFrame.add(marketGUI, BorderLayout.CENTER);
        this.jFrame.add(cardContainerGUI, BorderLayout.WEST);
        this.jFrame.add(leaderCardPlaceGui, BorderLayout.EAST);
        //this.jFrame.add(playersBarGUI, BorderLayout.SOUTH);
        this.jFrame.setVisible(true);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.pack();
    }

    public void send(Move move){
        super.notify(move);
        this.disableAllButtons(); //devo attendere una risposta dal server
        //todo lo chiamo da un listener e mando la move al server

    }
    public void disableButtonsHandler(TurnPhase turnPhase){
        switch (turnPhase) {
            case WaitPhase:
                this.disableAllButtons();
                break;
            case MainPhase:
                this.disableForMainPhase();
                this.enableForMainPhase();
                break;
            case InitialPhase:
            case EndPhase:
                this.disableForLeaderCardPhase();
                this.enableForLeaderCardPhase();
                break;
        }
    }

    private void enableForLeaderCardPhase() {
        this.leaderCardPlaceGui.enableButtons();
    }

    private void enableForMainPhase() {
        //todo attiva i bottoni delle azioni principali
        this.marketGUI.enableButtons();
    }

    private void disableForMainPhase() {
        this.leaderCardPlaceGui.disableButtons();
    }

    private void disableForLeaderCardPhase() {
        //todo blocca tutto apparte le azioni leader
        this.marketGUI.disableButtons();
    }

    public void disableAllButtons(){
        //disables all the buttons
        this.disableForMainPhase();
        this.disableForLeaderCardPhase();
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
        this.disableButtonsHandler(this.mePlayer.getTurnPhase());
        //todo setter di altre cose
    }

    public void showMarketInformations(boolean hasFaith) {
        this.informationsGUI.showMarketInformations(hasFaith);
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

    public WarehouseGUI getWarehouseGUI() {
        return this.boardGUI.getWarehouseGUI();
    }

    public ResourceInHandGUI getResourceInHandGUI() {
        return this.marketGUI.getResourceInHandGUI();
    }

    public TakeMarketResourceBuilder getTakeMarketResourceBuilder() {
        return takeMarketResourceBuilder;
    }

    public void sendMarketMove() {
        this.takeMarketResourceBuilder.setWarehouse(this.boardGUI.getWarehouseModel());
        this.takeMarketResourceBuilder.setMarket(this.marketGUI.getMarket());
        this.send(takeMarketResourceBuilder.getMove());
        this.takeMarketResourceBuilder.reset();
    }
}

