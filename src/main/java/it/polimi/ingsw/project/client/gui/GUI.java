package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.TakeMarketResourceBuilder;
import it.polimi.ingsw.project.client.gui.board.*;
import it.polimi.ingsw.project.client.gui.leadercardcontainer.LeaderCardPlaceGUI;
import it.polimi.ingsw.project.client.gui.listeners.ResizeListener;
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
    private LeaderCardPlaceGUI leaderCardPlaceGUI;
    private CardContainerGUI cardContainerGUI;
    private Player mePlayer;
    private List<Player> opponentsPlayer;
    private InformationsGUI informationsGUI;
    private HistoryGUI historyGUI;
    private PlayersBarGUI playersBarGUI;
    private TakeMarketResourceBuilder takeMarketResourceBuilder;
    private BuyDevCardMoveHandler buyDevCardMoveHandler;
    private ProductionMoveHandler productionMoveHandler;


    public GUI(Match match, String myNickname) {
        this.takeMarketResourceBuilder = new TakeMarketResourceBuilder();
        this.jFrame = new JFrame();
        this.jFrame.setTitle("Master of Renaissance");
        jFrame.setLayout(new GridLayout(2,1));

        //  boardGUI = new BoardGUI("Board");
        Pair<Player, List<Player>> pair = Utils.splitPlayers(match,myNickname);
        this.mePlayer = pair._1;
        this.opponentsPlayer = pair._2;
        this.informationsGUI = new InformationsGUI(this,this.mePlayer.getTurnPhase());
        this.boardGUI = new BoardGUI(myNickname, this.informationsGUI, this.mePlayer.getBoard());
        this.marketGUI = new MarketGUI(this,match.getMarket(),informationsGUI);
        this.historyGUI = new HistoryGUI(this.mePlayer.getHistoryToString());
        this.cardContainerGUI = new CardContainerGUI(this.informationsGUI, match.getCardContainer());
        this.leaderCardPlaceGUI = new LeaderCardPlaceGUI(this.mePlayer.getLeaderCards(),this);
        this.playersBarGUI = new PlayersBarGUI(this.opponentsPlayer.stream().map(Player::getNickname).collect(Collectors.toList()), myNickname,this);
        //todo inizializzatori di altre cose


//        this.jFrame.add(boardGUI, BorderLayout.NORTH);
//        this.jFrame.add(informationsGUI, BorderLayout.CENTER);
//        //this.jFrame.add(historyGUI, BorderLayout.NORTH);
//        this.jFrame.add(marketGUI, BorderLayout.SOUTH);
//        this.jFrame.add(cardContainerGUI, BorderLayout.WEST);
//        this.jFrame.add(leaderCardPlaceGui, BorderLayout.EAST);
        //this.jFrame.add(playersBarGUI, BorderLayout.SOUTH);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        JPanel top2Panel = new JPanel();
        top2Panel.setLayout(new GridLayout(1,4));
        top2Panel.add(marketGUI);
        top2Panel.add(cardContainerGUI);
        top2Panel.add(informationsGUI);
        top2Panel.add(historyGUI);
        bottomPanel.add(top2Panel,BorderLayout.CENTER);

        bottomPanel.add(playersBarGUI,BorderLayout.SOUTH);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(boardGUI,BorderLayout.EAST);
        topPanel.add(leaderCardPlaceGUI,BorderLayout.CENTER);

        this.jFrame.add(topPanel);
        this.jFrame.add(bottomPanel);
        this.jFrame.setVisible(true);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.pack();
        this.disableButtonsHandler(this.mePlayer.getTurnPhase());


        this.jFrame.addComponentListener(new ResizeListener(this));
    }

    public void send(Move move){
        super.notify(move);
        this.disableAllButtons(); //devo attendere una risposta dal server
        //todo lo chiamo da un listener e mando la move al server
    }

    public void disableButtonsHandler(TurnPhase turnPhase){
        switch (turnPhase) {
            case WaitPhase:
            case MainPhase:
                disableAllButtons();
                break;
            case InitialPhase:
            case EndPhase:
                disableAllButtons();
                enableForLeaderCardPhase();
                break;
        }
    }

    private void disableAllButtons() {
        this.leaderCardPlaceGUI.disableButtons();
        this.boardGUI.disableAllButtons();
        this.cardContainerGUI.disableAllButtons();
        this.marketGUI.disableButtons();
    }

    private void enableForLeaderCardPhase() {
        this.leaderCardPlaceGUI.enableButtons();
    }

    public void enableForTakeFromMarket() {
        this.marketGUI.enableButtons();
    }

    public void setMatch(Match match){//todo chiama i metodi set di tutti i jinternalframe e aggiorna tutto
        Pair<Player, List<Player>> pair = Utils.splitPlayers(match,this.mePlayer.getNickname());
        this.mePlayer = pair._1;
        this.opponentsPlayer = pair._2;
        this.marketGUI.setMarket(match.getMarket(),this.mePlayer);
        this.historyGUI.setMyHistory(this.mePlayer.getHistoryToString());
        this.informationsGUI.setTurnPhase(this.mePlayer.getTurnPhase());
        this.cardContainerGUI.setCardContainer(match.getCardContainer());
        this.leaderCardPlaceGUI.setMyLeaderCards(this.mePlayer);
        this.boardGUI.refresh(this.mePlayer.getBoard());
        this.disableButtonsHandler(this.mePlayer.getTurnPhase());
        //todo setter di altre cose
    }

    public void showMarketInformations(boolean hasFaith) {
        this.informationsGUI.showMarketInformations(hasFaith);
        if(hasFaith){
            this.boardGUI.moveForward();
        }
    }

    // shows your view
    public void showMyView() {
        this.historyGUI.setMyHistory(this.mePlayer.getHistoryToString());
        this.informationsGUI.setTurnPhase(this.mePlayer.getTurnPhase());
        this.leaderCardPlaceGUI.setMyLeaderCards(this.mePlayer);
        this.boardGUI.setBoardByPlayer(this.mePlayer);
    }

    // show the view of the opponent opponentPLayers(index)
    public void showOpponentView(int index) {
        this.historyGUI.setHistory(this.opponentsPlayer.get(index).getHistoryToString(),this.opponentsPlayer.get(index).getNickname());
        this.informationsGUI.showOpponentView(this.opponentsPlayer.get(index).getNickname());
        this.leaderCardPlaceGUI.setOpponentLeaderCards(this.opponentsPlayer.get(index).getLeaderCards(),this.opponentsPlayer.get(index).getNickname());
        this.boardGUI.setBoardByPlayer(this.mePlayer);
        this.disableAllButtons();
    }

    public InformationsGUI getInformationsGUI() { return informationsGUI; }

    public BoardGUI getBoardGUI() {
        return this.boardGUI;
    }

    public CardContainerGUI getCardContainerGUI() { return this.cardContainerGUI; }

    public MarketGUI getMarketGUI() { return marketGUI; }

    public ResourceInHandGUI getResourceInHandGUI() {
        return this.marketGUI.getResourceInHandGUI();
    }

    public TakeMarketResourceBuilder getTakeMarketResourceBuilder() {
        return takeMarketResourceBuilder;
    }

    public BuyDevCardMoveHandler getBuyDevCardMoveHandler() { return buyDevCardMoveHandler; }

    public void sendMarketMove() {
        disableAllButtons();
        this.takeMarketResourceBuilder.setWarehouse(this.boardGUI.getWarehouseModel());
        this.takeMarketResourceBuilder.setMarket(this.marketGUI.getMarket());
        this.send(takeMarketResourceBuilder.getMove());
        this.takeMarketResourceBuilder.reset();
    }

    public void sendBuyDevCardMove(BuyDevCardMoveHandler buyDevCardMoveHandler) {
        disableAllButtons();
        this.informationsGUI.refresh();
        this.buyDevCardMoveHandler = buyDevCardMoveHandler;
        this.send(this.buyDevCardMoveHandler.getMove());
        this.buyDevCardMoveHandler.reset();
    }

    public void sendProductionMove(ProductionMoveHandler productionMoveHandler) {
        disableAllButtons();
        this.productionMoveHandler = productionMoveHandler;
        this.send(this.productionMoveHandler.getMove());
        this.productionMoveHandler.reset();
    }

    public void refreshSize(){
        Dimension d = this.jFrame.getSize();
        this.leaderCardPlaceGUI.refreshSize((int) (d.width*0.25), (int) (d.height*0.48));
        this.boardGUI.refreshSize((int) (d.width*0.75), (int) (d.height*0.48));
        this.marketGUI.refreshSize((int) (d.width*0.2), (int) (d.height*0.25));
        this.cardContainerGUI.refreshSize(d.width/4, (int) (d.height*0.45));
        this.leaderCardPlaceGUI.refreshSize(d.width-boardGUI.getWidth(), (int) (d.height*0.48));
    }
}

