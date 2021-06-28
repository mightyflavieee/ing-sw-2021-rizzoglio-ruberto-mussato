package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.client.ClientGUI;
import it.polimi.ingsw.project.client.gui.board.*;
import it.polimi.ingsw.project.client.gui.leadercardcontainer.LeaderCardPlaceGUI;
import it.polimi.ingsw.project.client.gui.listeners.ResizeListener;
import it.polimi.ingsw.project.client.gui.market.MarketGUI;
import it.polimi.ingsw.project.client.gui.market.ResourceInHandGUI;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.TurnPhase;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.observer.Observable;
import it.polimi.ingsw.project.utils.Pair;
import it.polimi.ingsw.project.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUI extends Observable<Move>  {
    private static final long serialVersionUID = 3840332592475092704L;
    private final JFrame jFrame;
    private final ClientGUI clientGUI;
    private final BoardGUI boardGUI;
    private final MarketGUI marketGUI;
    private final LeaderCardPlaceGUI leaderCardPlaceGUI;
    private final CardContainerGUI cardContainerGUI;
    private Player mePlayer;
    private List<Player> opponentsPlayer;
    private final InformationsGUI informationsGUI;
    private final HistoryGUI historyGUI;
    private final PlayersBarGUI playersBarGUI;
    private final TakeMarketResourceBuilder takeMarketResourceBuilder;

    public GUI(ClientGUI clientGUI, Match match, String myNickname) {
        this.clientGUI = clientGUI;
        this.takeMarketResourceBuilder = new TakeMarketResourceBuilder();
        this.jFrame = new JFrame();
        this.jFrame.setTitle("Master of Renaissance");
        this.jFrame.setLayout(new GridLayout(2,1));
        Pair<Player, List<Player>> pair = Utils.splitPlayers(match,myNickname);
        this.mePlayer = pair._1;
        this.opponentsPlayer = pair._2;
        this.informationsGUI = new InformationsGUI(this,this.mePlayer.getTurnPhase());
        if (this.opponentsPlayer.size() == 0) {
            this.boardGUI = new BoardGUI(myNickname, this.informationsGUI, this.mePlayer.getBoard(), true);
        } else {
            this.boardGUI = new BoardGUI(myNickname, this.informationsGUI, this.mePlayer.getBoard(), false);
        }
        this.marketGUI = new MarketGUI(this,match.getMarket(),informationsGUI);
        this.historyGUI = new HistoryGUI(this.mePlayer.getHistoryToString());
        this.cardContainerGUI = new CardContainerGUI(this.informationsGUI, match.getCardContainer());
        this.leaderCardPlaceGUI = new LeaderCardPlaceGUI(this.mePlayer.getLeaderCards(),this);
        this.playersBarGUI = new PlayersBarGUI(this.opponentsPlayer.stream().map(Player::getNickname).collect(Collectors.toList()), myNickname,this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        JPanel top2Panel = new JPanel();
        top2Panel.setLayout(new GridLayout(1,4));
        top2Panel.add(marketGUI);
        top2Panel.add(cardContainerGUI);
        top2Panel.add(informationsGUI);
        top2Panel.add(leaderCardPlaceGUI);
        bottomPanel.add(top2Panel,BorderLayout.CENTER);

        bottomPanel.add(playersBarGUI,BorderLayout.SOUTH);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(boardGUI,BorderLayout.EAST);
        topPanel.add(historyGUI,BorderLayout.CENTER);
        this.jFrame.add(topPanel);
        this.jFrame.add(bottomPanel);
        this.jFrame.setVisible(true);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.pack();
        this.disableLeaderButtonsHandler(this.mePlayer.getTurnPhase());

        this.jFrame.addComponentListener(new ResizeListener(this));
        this.jFrame.setMinimumSize(new Dimension(1460,760));
    }

    public void send(Move move){
        super.notify(move);
        this.disableAllButtons(); //devo attendere una risposta dal server
    }

    /**
     * disables the buttons of LeaderCardPlaceGUI based on the turn Phase
     */
    public void disableLeaderButtonsHandler(TurnPhase turnPhase){
        switch (turnPhase) {
            case WaitPhase:
            case MainPhase:
                disableAllButtons();
                break;
            case InitialPhase:
            case EndPhase:
                disableAllButtons();
                this.leaderCardPlaceGUI.enableButtonsForLeaderPhase();
                break;
        }

    }

    private void disableAllButtons() {
        this.leaderCardPlaceGUI.disableButtons();
        this.boardGUI.disableAllButtons();
        this.cardContainerGUI.disableAllButtons();
        this.marketGUI.disableButtons();
    }

    public void setMatch(Match match){
        if (match.getisOver()) {
                this.clientGUI.endGame();
            }
         else {
            Pair<Player, List<Player>> pair = Utils.splitPlayers(match,this.mePlayer.getNickname());
            this.mePlayer = pair._1;
            this.opponentsPlayer = pair._2;
            this.marketGUI.setMarket(match.getMarket(),this.mePlayer);
            this.historyGUI.setMyHistory(this.mePlayer.getHistoryToString());
            this.informationsGUI.setTurnPhase(this.mePlayer.getTurnPhase());
            this.cardContainerGUI.setCardContainer(match.getCardContainer());
            this.leaderCardPlaceGUI.setMyLeaderCards(this.mePlayer);
            this.boardGUI.refresh(this.mePlayer.getBoard());
            this.boardGUI.setBoardTitle(this.mePlayer.getNickname(), this.mePlayer.getVictoryPoints());
            this.disableLeaderButtonsHandler(this.mePlayer.getTurnPhase());
            if (!this.playersBarGUI.isClosed()) {
                this.playersBarGUI.clickMyButton();
            }
            if(TurnPhase.MainPhase == this.mePlayer.getTurnPhase()){
                this.informationsGUI.getMainPhaseHandler().goToMainButtons();
            }
        }
    }

    /**
     * updates the informations gui and the boardGUI after collecting resources from the market
     */
    public void showMarketInformations(boolean hasFaith) {
        this.informationsGUI.showMarketInformations(hasFaith);
        if(hasFaith){
            this.boardGUI.moveForward();
        }
        this.boardGUI.setCanChangeShelves(true);
    }

    /**
     * shows your view
     */
    public void showMyView() {
        if (playersBarGUI.isClosed()) {
            return;
        }
        this.historyGUI.setMyHistory(this.mePlayer.getHistoryToString());
        this.informationsGUI.setTurnPhase(this.mePlayer.getTurnPhase());
        this.leaderCardPlaceGUI.setMyLeaderCards(this.mePlayer);
        this.boardGUI.setBoardByPlayer(this.mePlayer);
        this.playersBarGUI.clickMyButton();
        TurnPhase turnPhase = mePlayer.getTurnPhase();
        if(turnPhase == TurnPhase.EndPhase || turnPhase == TurnPhase.InitialPhase){
            this.leaderCardPlaceGUI.enableButtonsForLeaderPhase();
        }
    }

    /**
     * show the view of the opponent opponentPLayers(index)
     */
    public void showOpponentView(int index) {
        this.historyGUI.setHistory(this.opponentsPlayer.get(index).getHistoryToString(),this.opponentsPlayer.get(index).getNickname());
        this.informationsGUI.showOpponentView(this.opponentsPlayer.get(index).getNickname());
        this.leaderCardPlaceGUI.setOpponentLeaderCards(this.opponentsPlayer.get(index).getLeaderCards(),this.opponentsPlayer.get(index).getNickname());
        this.boardGUI.setBoardByPlayer(this.opponentsPlayer.get(index));
        this.disableAllButtons();
        playersBarGUI.clickOpponentButton(index);
    }

    public JFrame getJFrame() { return this.jFrame; }

    public InformationsGUI getInformationsGUI() { return informationsGUI; }

    public BoardGUI getBoardGUI() { return this.boardGUI; }

    public CardContainerGUI getCardContainerGUI() { return this.cardContainerGUI; }

    public LeaderCardPlaceGUI getLeaderCardPlaceGUI() { return this.leaderCardPlaceGUI; }

    public ResourceInHandGUI getResourceInHandGUI() {
        return this.marketGUI.getResourceInHandGUI();
    }

    public TakeMarketResourceBuilder getTakeMarketResourceBuilder() {
        return this.takeMarketResourceBuilder;
    }

    /**
     * sends the market move to the server creating it from the TakeMarketResourceBuilder
     */
    public void sendMarketMove() {
        disableAllButtons();
        this.takeMarketResourceBuilder.setWarehouse(this.boardGUI.getWarehouseModel());
        this.takeMarketResourceBuilder.setMarket(this.marketGUI.getMarket());
        this.send(takeMarketResourceBuilder.getMove());
        this.takeMarketResourceBuilder.reset();
    }

    /**
     * sends the Buy development card move to the server creating it from the BuyDevCardMoveHandler and removes the listener used for the selection of the resources in the warehouse
     */
    public void sendBuyDevCardMove(BuyDevCardMoveHandler buyDevCardMoveHandler) {
        this.boardGUI.getWarehouseGUI().removeSelectResourceListeners();
        disableAllButtons();
        this.informationsGUI.refresh();
        this.send(buyDevCardMoveHandler.getMove());
        buyDevCardMoveHandler.reset();
    }

    /**
     * sends the Production move to the server creating it from the ProductionMoveHandler and removes the listener used for the selection of the resources in the warehouse
     */
    public void sendProductionMove(ProductionMoveHandler productionMoveHandler) {
        this.boardGUI.getWarehouseGUI().removeSelectResourceListeners();
        disableAllButtons();
        this.send(productionMoveHandler.getMove());
        productionMoveHandler.reset();
    }

    /**
     * updates the size of all the pictures
     */
    public void refreshSize(){
        Dimension d = this.jFrame.getSize();
        this.leaderCardPlaceGUI.refreshSize((int) (d.width*0.25), (int) (d.height*0.4));
        this.boardGUI.refreshSize((int) (d.width*0.75), (int) (d.height*0.48));
        this.marketGUI.refreshSize((int) (d.width*0.2), (int) (d.height*0.2));
        this.cardContainerGUI.refreshSize(d.width/4, (int) (d.height*0.4));

    }

    /**
     * it is used at the beginning of the market move and it shows she transmutation panel if you have two transmutation perk enabled (corner case),
     * it enables the market's buttons and the warehouse's buttons if you don't need to choose
     */
    public void startMarketMove() {
        if(this.marketGUI.isTransmutationChosable()){
            this.informationsGUI.goToTransmutationPanel();
        }else {
            this.marketGUI.enableButtons();
            this.boardGUI.getWarehouseGUI().enableAllButtons();
            this.informationsGUI.getMainPhaseHandler().goToAbortMovePanel();
        }
    }

    public List<ResourceType> getTransmutationPerks() {
        return this.marketGUI.getTransmutationPerks();
    }

    /**
     * set the chosen transmutation perk and enables the market's buttons and the warehouse's buttons
     */
    public void setChosenTransmutationPerk(ResourceType resourceType) {
        this.marketGUI.setChosenTransmutationPerk(resourceType);
        this.marketGUI.enableButtons();
        this.boardGUI.getWarehouseGUI().enableAllButtons();
        this.informationsGUI.getMainPhaseHandler().goToAbortMovePanel();
    }

    public PlayersBarGUI getPlayersBarGUI() {
        return playersBarGUI;
    }
}

