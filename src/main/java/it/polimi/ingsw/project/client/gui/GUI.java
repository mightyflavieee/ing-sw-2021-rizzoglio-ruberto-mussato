package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.TakeMarketResourceBuilder;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.TurnPhase;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.TakeMarketResourcesMove;
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
    private LeaderCardPlaceGUI leaderCardPlaceGui;
    private CardContainerGUI cardContainerGUI;
    private Player mePlayer;
    private List<Player> opponentsPlayer;
    private InformationsGUI informationsGUI;
    private HistoryGUI historyGUI;
    private PlayersBarGUI playersBarGUI;
    private TakeMarketResourceBuilder takeMarketResourceBuilder;


    public GUI(Match match, String myNickname) {
        this.takeMarketResourceBuilder = new TakeMarketResourceBuilder();
        this.jFrame = new JFrame();
        this.jFrame.setTitle("Master of Renaissance");


        //  boardGUI = new BoardGUI("Board");
        Pair<Player, List<Player>> pair = Utils.splitPlayers(match,myNickname);
        this.mePlayer = pair._1;
        this.opponentsPlayer = pair._2;
        this.informationsGUI = new InformationsGUI(this,this.mePlayer.getTurnPhase());
        this.marketGUI = new MarketGUI(this,match.getMarket(),informationsGUI);
        this.historyGUI = new HistoryGUI(this.mePlayer.getHistoryToString());
        this.cardContainerGUI = new CardContainerGUI(match.getCardContainer());
        this.leaderCardPlaceGui = new LeaderCardPlaceGUI(this.mePlayer.getLeaderCards(),this);
        this.playersBarGUI = new PlayersBarGUI(this.opponentsPlayer.stream().map(Player::getNickname).collect(Collectors.toList()), myNickname,this);
        this.boardGUI = new BoardGUI(myNickname,this.informationsGUI,this.mePlayer.getBoard());
        //todo inizializzatori di altre cose
        //faithMapGUI = new FaithMapGUI();
        //warehouseGUI = new WarehouseGUI();
        //chestGUI = new ChestGUI();
        //mapTrayGUI = new MapTrayGUI();
        this.disableButtonsHandler(this.mePlayer.getTurnPhase());



//        this.jFrame.setLayout(new BorderLayout());
//        JPanel southPanel = new JPanel();
//        southPanel.setLayout(new GridLayout(1,2));
//        southPanel.add(marketGUI);
//        southPanel.add(cardContainerGUI);
//        southPanel.setVisible(true);
//        JPanel eastPanel = new JPanel();
//        eastPanel.setLayout(new GridLayout(2,1));
//        eastPanel.add(historyGUI);
//        eastPanel.add(playersBarGUI);
//        eastPanel.setVisible(true);
//        jFrame.add(eastPanel,BorderLayout.EAST);
//        jFrame.add(southPanel, BorderLayout.SOUTH);
//        jFrame.add(boardGUI, BorderLayout.NORTH);
//        jFrame.add(leaderCardPlaceGui, BorderLayout.WEST);
//        jFrame.add(informationsGUI,BorderLayout.CENTER);



        this.jFrame.setLayout(new GridLayout(3,1));
        JPanel upperPanel, centerPanel, bottomPanel;
        upperPanel = new JPanel();
        upperPanel.add(boardGUI);
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,4));
        centerPanel.add(informationsGUI);
        centerPanel.add(leaderCardPlaceGui);
        centerPanel.add(historyGUI);
        centerPanel.add(playersBarGUI);
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1,2));
        bottomPanel.add(marketGUI);
        bottomPanel.add(cardContainerGUI);
        this.jFrame.add(upperPanel);
        this.jFrame.add(centerPanel);
        this.jFrame.add(bottomPanel);





        // this.jFrame.setLayout(new GridLayout(3,3));
//        this.jFrame.add(informationsGUI);
//        this.jFrame.add(marketGUI);
//        this.jFrame.add(cardContainerGUI);
//        this.jFrame.add(leaderCardPlaceGui);
//        this.jFrame.add(historyGUI);
//        this.jFrame.add(playersBarGUI);
//        this.jFrame.add(boardGUI);
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
    }

    private void disableForMainPhase() {
        this.leaderCardPlaceGui.disableButtons();
    }

    private void disableForLeaderCardPhase() {
        //todo blocca tutto apparte le azioni leader
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

