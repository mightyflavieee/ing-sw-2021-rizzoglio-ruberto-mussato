package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.actionTokens.ActionTokenContainer;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Match implements Serializable, Cloneable {
    private static final long serialVersionUID = 3840280592475092777L;

    private List<Player> playerList;
    private Market market;
    private CardContainer cardContainer;
    private ActionTokenContainer actionTokenContainer;
    private Player currentPlayer;
    private boolean isLastTurn;
    private boolean isOver;
    private boolean lorenzoWon;

    /**
     * constructor of the match used when you start a game
     * @param playerList list of the Players connected in the game
     */
    public Match(List<Player> playerList) {
        this.playerList = new ArrayList<>();
        this.playerList.addAll(playerList); // la lista mi arriva già shuffled
        // this.playerList.forEach(x -> x.createFaithMapAndWarehouse(this)); // serve
        // per l'inizio partita
        for (Player player : playerList) {
            player.createFaithMapAndWarehouse(this);
        }
        this.market = new Market();
        this.cardContainer = new CardContainer();
        // if (playerList.size() == 1) {
        actionTokenContainer = new ActionTokenContainer(this);

        this.currentPlayer = playerList.get(0);
        this.currentPlayer.updateTurnPhase();
        this.isLastTurn = false;
        this.isOver = false;
        this.lorenzoWon = false;
    }

    public Match() { // da usare nella clone
    }

    /**
     * it moves forward the players in 3 and 4 position
     */
    public void moveForwardForStartingGame() {
        for (int i = 0; i < this.playerList.size(); i++) {
            Player player = this.playerList.get(i);
            switch (i) {
                case 0:
                case 1:
                    break;
                case 2:
                case 3:
                    player.moveForward();
                    break;
            }
        }
    }

    /**
     * it change the current player to the next player connected, it can be also played in a single player
     */
    private void nextPlayer() {
        int playerIndex;
        int loopCounter = 0;
        while (loopCounter <= this.playerList.size()) {
            playerIndex = this.playerList.indexOf(currentPlayer);
            playerIndex = playerIndex + 1;
            if (playerIndex > this.playerList.size() - 1) {
                playerIndex = 0;
            }
            this.currentPlayer = playerList.get(playerIndex);
            if (this.currentPlayer.getIsConnected()) {
                break;
            } else {
                loopCounter++;
            }
        }
    }

    /**
     * called when a player disconnects and it skips his phase and revert it to waitPhase
     */
    public void playerSkipTurn() {
        // sets old player to wait phase
        this.currentPlayer.setToWaitPhase();
        this.nextPlayer();
        // sets new player to initialPhase
        this.currentPlayer.setToInitialPhase();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Market getMarket() {
        return market;
    }

    public CardContainer getCardContainer() {
        return cardContainer;
    }

    public ActionTokenContainer getActionTokenContainer() {
        return actionTokenContainer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getIsLastTurn() {
        return this.isLastTurn;
    }

    public boolean getisOver() {
        return isOver;
    }

    public boolean getLorenzoWon() {
        return this.lorenzoWon;
    }

    /**
     * it calls on each player the method to add points to the player
     * @param numTile it is the number of points we get when we step into this papal councilTile
     */
    public void notifyFaithMapsForCouncil(int numTile) {
        playerList.forEach(x -> x.papalCouncil(numTile));
        if (numTile == 3) {
            this.isLastTurn = true; // qualcuno è arrivato all'ultima casella
        }
    }

    /**
     * it calls on each player the method to move forward the player (if the name is different from the currentPlayer)
     * @param numDiscardedResources number of resources discarded
     */
    public void notifyFaithMapsForDiscard(int numDiscardedResources) {
        for (int i = 0; i < numDiscardedResources; i++) {
            playerList.stream().filter(x -> !x.getNickname().equals(currentPlayer.getNickname()))
                    .forEach(Player::moveForward);
        }
    }

    /**
     * it discard the cards with that specific color and calls the you lost function if the game has finished
     * @param cardColor color of the card to discard
     */
    public void discardForActionToken(CardColor cardColor) {
        if (cardContainer.discard(cardColor))
            this.youLost();
    }

    /**
     * it handles the end of single game by loosing
     */
    private void youLost() {
        this.lorenzoWon = true;
        this.isOver = true;
    }

    /**
     * @param isLastTurn boolean that explains it's the last turn
     */
    public void setIsLastTurn(boolean isLastTurn) {
        this.isLastTurn = isLastTurn;
    }

    /**
     * method called at the end of the game
     */
    public void end() {
        List<Player> connectedPlayer = new ArrayList<>();
        for (Player player : playerList) {
            if (player.getIsConnected()) {
                connectedPlayer.add(player);
            }
        }
        if(this.isLastTurn && connectedPlayer.size()==0){
            this.isOver = true;
        }else {
            if (this.isLastTurn && this.currentPlayer.getNickname()
                    .equals(connectedPlayer.get(connectedPlayer.size() - 1).getNickname())) {
                if (this.currentPlayer.getTurnPhase() == TurnPhase.EndPhase) {
                    addResourceVictoryPoints();
                    this.isOver = true;
                }
            }
        }

    }

    /**
     * it adds to each player the victoryPoints gained by his resources / 5
     */
    public void addResourceVictoryPoints() {
        for (Player player : this.playerList) {
            player.addResourceVictoryPoints();
        }
    }

    /**
     * it is called by the model after every move and it updates the turnPhase or updates also the player
     */
    public void updatePlayer() {
        if (this.currentPlayer.getTurnPhase() == TurnPhase.EndPhase) {
            this.currentPlayer.updateTurnPhase();
            this.nextPlayer();
        }
        this.currentPlayer.updateTurnPhase();
    }

    /**
     * method used to clone the match and send it to the players
     * @return it returns a clone of the match
     */
    @Override
    public final Match clone() {
        final Match result = new Match();
        result.actionTokenContainer = actionTokenContainer;
        result.cardContainer = cardContainer;
        result.currentPlayer = currentPlayer.clone();
        result.isLastTurn = isLastTurn;
        result.isOver = isOver;
        result.market = market;
        result.playerList = playerList.stream().map(Player::clone).collect(Collectors.toList());
        result.lorenzoWon = lorenzoWon;
        return result;
    }

    /**
     * it checks if the discardAction is feasible
     * @param leaderCardID id to check if present
     * @return true if the id is present, false if not
     */
    public boolean isFeasibleDiscardLeaderCardMove(String leaderCardID) {
        return currentPlayer.isFeasibleDiscardLeaderCardMove(leaderCardID);
    }

    /**
     * it removes the LeaderCard with that id
     * @param leaderCardID id of the card to remove
     */
    public void performDiscardLeaderCardMove(String leaderCardID) {
        this.currentPlayer.performDiscardLeaderCardMove(leaderCardID);
    }

    /**
     * it checks if the move Buy Development Card it makes sense
     * @param devCardID id of the card to buy
     * @param resourcesToEliminateWarehouse resources selected from the warehouse
     * @param resourcesToEliminateExtraDeposit resources selected from the extradeposit
     * @param resourcesToEliminateChest resources selected from the chest
     * @param position position of the card where I want to put it
     * @return it returns true if all checks are passed, false if not
     */
    public boolean isFeasibleBuyDevCardMove(String devCardID, Map<ResourceType, Integer> resourcesToEliminateWarehouse,
            Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
            Map<ResourceType, Integer> resourcesToEliminateChest, DevCardPosition position) {
        if (!this.cardContainer.isCardPresent(devCardID)) {
            return false;
        } else {
            DevelopmentCard devCard = this.cardContainer.fetchCard(devCardID);
            return this.currentPlayer.isFeasibleBuyDevCardMove(devCard, resourcesToEliminateWarehouse,
                    resourcesToEliminateExtraDeposit, resourcesToEliminateChest, position);
        }
    }

    /**
     * it buys the requested development card
     * @param devCardID card selected to be bought
     * @param resourcesToEliminateWarehouse resources selected from warehouse
     * @param resourcesToEliminateExtraDeposit resources selected from extra deposit
     * @param resourcesToEliminateChest resources selected from chest
     * @param position position selected for the card
     */
    public void performBuyDevCardMove(String devCardID, Map<ResourceType, Integer> resourcesToEliminateWarehouse,
            Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
            Map<ResourceType, Integer> resourcesToEliminateChest, DevCardPosition position) {
        DevelopmentCard devCard = this.cardContainer.removeBoughtCard(devCardID);
        this.currentPlayer.performBuyDevCardMove(devCard, resourcesToEliminateWarehouse,
                resourcesToEliminateExtraDeposit, resourcesToEliminateChest, position);
    }

    /**
     * it checks if the production Move is feasible
     * @param devCardIDs ids of the developmentCards selected fro the production
     * @param leaderCardIDs ids of the leaderCards selected for the production
     * @param requiredResources resources required for the production
     * @param resourcesToEliminateWarehouse resources selected from the warehouse
     * @param resourcesToEliminateExtraDeposit resources selected from the extraDeposit
     * @param resourcesToEliminateChest resources selected from the chest
     * @param productionType enum of the productionType
     * @return true is the action is feasible, false if not
     */
    public boolean isFeasibleProductionMove(List<String> devCardIDs, List<String> leaderCardIDs,
            Map<ResourceType, Integer> requiredResources, Map<ResourceType, Integer> resourcesToEliminateWarehouse,
            Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
            Map<ResourceType, Integer> resourcesToEliminateChest, ProductionType productionType) {
        if (devCardIDs != null) {
            for (String devCardID : devCardIDs) {
                if (this.cardContainer.isCardPresent(devCardID)) {
                    return false;
                }
            }
        }
        return this.currentPlayer.isFeasibleProductionMove(devCardIDs, leaderCardIDs, requiredResources,
                resourcesToEliminateWarehouse, resourcesToEliminateExtraDeposit, resourcesToEliminateChest,
                productionType);
    }

    /**
     * performs the production putting the resources manufactured in the strongbox
     * and eliminating the resources required
     * @param devCardIDs ids of developmentCards selected by the user
     * @param resourcesToEliminateWarehouse resources selected from the warehouse
     * @param resourcesToEliminateExtraDeposit resources selected from the extraDeposit
     * @param resourcesToEliminateChest resources selected from the chest
     * @param productionType enum of the productionType
     * @param boardManufacturedResource resources manufactured by the board
     * @param perkManufacturedResource resources manufactured from the perk
     */
    public void performProductionMove(List<String> devCardIDs, Map<ResourceType, Integer> resourcesToEliminateWarehouse,
            Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
            Map<ResourceType, Integer> resourcesToEliminateChest, ProductionType productionType,
            ResourceType boardManufacturedResource, List<ResourceType> perkManufacturedResource) {
        this.currentPlayer.performProductionMove(devCardIDs, resourcesToEliminateWarehouse,
                resourcesToEliminateExtraDeposit, resourcesToEliminateChest, productionType, boardManufacturedResource,
                perkManufacturedResource);
    }

    /**
     * checks if the warehouse is feasible for the takeMarketResourceMove
     * @param warehouse sent by the player to check if it has the right format
     * @return true if it pass all the checks, false if not
     */
    public boolean isFeasibleTakeMarketResourcesMove(Warehouse warehouse) {
        return this.currentPlayer.isFeasibleTakeMarketResourcesMove(warehouse);

    }

    /**
     * @param warehouse  sent by the player
     * @param discardedResources list of resources discarded
     * @param hasRedMarble true if he got the red marble from the market
     */
    public void performTakeMarketResourceMove(Warehouse warehouse, List<Resource> discardedResources, Market market,
            Boolean hasRedMarble) {
        this.currentPlayer.performTakeMarketResourceMove(warehouse, discardedResources, hasRedMarble);
        this.market = market;
    }

    /**
     * it extracted the token for single player, and updates the history
     */
    public void performExtractActionTokenMove() {
        this.currentPlayer.updateHistory(this.actionTokenContainer.drawToken());
    }

    /**
     * checks if the current player can activate a LeaderCard
     * @param leaderCardID id of the leaderCard
     * @return it returns true if all checks are passed, false if not
     */
    public boolean isFeasibleActivateLeaderCardMove(String leaderCardID) {
        return this.currentPlayer.isFeasibleActivateLeaderCardMove(leaderCardID);
    }

    /**
     * activates a LeaderCard and its respective Perk
     * @param leaderCardID id of the selected leaderCard
     */
    public void performActivateLeaderCardMove(String leaderCardID) {
        this.currentPlayer.performActivateLeaderCardMove(leaderCardID);
    }

    /**
     * it moves forward Lorenzo for single player game and sets you lost if Lorenzo arrives to the end of the game
     */
    public void moveForwardBlack() {
        if (24 == this.currentPlayer.moveForwardBlack())
            this.youLost(); // cioè se lorenzo è arrivato alla fine
    }

    /**
     * 
     * @param playerMove
     * @return
     */
    public boolean isRightTurnPhase(PlayerMove playerMove) {
        if (currentPlayer.getTurnPhase() == TurnPhase.WaitPhase) {
            return false;
        }
        if (playerMove.isMainMove()) {
            return (currentPlayer.getTurnPhase() == TurnPhase.MainPhase);
        } else {
            return (currentPlayer.getTurnPhase() != TurnPhase.MainPhase);
        }

    }

    public TurnPhase getTurnPhase(String nickname) {

        for (Player player : playerList) {
            if (player.getNickname().equals(nickname)) {
                return player.getTurnPhase();
            }
        }
        return TurnPhase.WaitPhase;
    }

    public int getVictoryPoints(String nickname) {
        for (Player player : playerList) {
            if (player.getNickname().equals(nickname)) {
                return player.getVictoryPoints();
            }
        }
        return 0;
    }

    public int getMarkerPosition(String nickname) {
        for (Player player : playerList) {
            if (player.getNickname().equals(nickname)) {
                return player.getMarkerPosition();
            }
        }
        return 0;
    }

    public String getLeaderCardsToString(String nickname) {
        for (Player player : playerList) {
            if (player.getNickname().equals(nickname)) {
                return player.getLeaderCardsToString();
            }
        }
        return "";
    }

    public Warehouse getWarehouse(String nickname) {
        for (Player player : playerList) {
            if (player.getNickname().equals(nickname)) {
                return player.getWarehouse();
            }
        }
        return null;
    }

    public Board getBoardByPlayerNickname(String nickname) {
        int playerIndex = 0;
        for (Player player : this.playerList) {
            if (player.getNickname().equals(nickname)) {
                break;
            } else {
                playerIndex++;
            }
        }
        return this.playerList.get(playerIndex).getBoard();
    }

    public List<ResourceType> getTransmutationPerk(String nickname) {
        for (Player player : playerList) {
            if (player.getNickname().equals(nickname)) {
                return player.getTransmutationPerk();
            }
        }
        return new ArrayList<>();
    }

    public String getOpponentsToString(String nickname) {
        StringBuilder string = new StringBuilder();
        for (Player player : playerList) {
            if (!player.getNickname().equals(nickname)) {
                string.append(player.getNickname()).append(" ");
            }
        }
        return string.toString();
    }

    public String getWarehouseToString(String nickname) {
        return this.getWarehouse(nickname).getShelvesToString();
    }

    public String getHistoryToString(String nickname) {
        for (Player player : playerList) {
            if (player.getNickname().equals(nickname)) {
                return player.getHistoryToString();
            }
        }
        return "";
    }

    public int getBlackMarkerPosition() {
        return this.currentPlayer.getBlackMarkerPosition();
    }

    public void setPlayerConnectionToFalse(Player disconnectedPlayer) {
        for (Player player : this.playerList) {
            if (player.getNickname().equals(disconnectedPlayer.getNickname())) {
                player.setIsConnected(false);
                player.setToWaitPhase();
                if (!currentPlayer.getIsConnected()) {
                    this.currentPlayer.setToWaitPhase();
                    this.nextPlayer();
                    this.currentPlayer.setToInitialPhase();
                }
                break;
            }
        }
    }

    public void setPlayerConnectionToTrue(String disconnectedPlayerNickname) {
        for (Player player : this.playerList) {
            if (player.getNickname().equals(disconnectedPlayerNickname)) {
                player.setIsConnected(true);
                if (player.getNickname().equals(currentPlayer.getNickname())) {
                    currentPlayer = player;
                }
                break;
            }
        }

    }

    public void readdObservers() {
        this.actionTokenContainer.readdObservers(this);
        for (Player player : this.playerList) {
            player.readdObservers(this);
        }
        this.currentPlayer.readdObservers(this);
    }

    public LinkedHashMap<Integer, Player> getLeaderboard() {
        LinkedHashMap<Integer, Player> leaderboard = new LinkedHashMap<>();
        List<Player> orderedListOfPlayers = new ArrayList<>();
        orderedListOfPlayers.addAll(playerList);
        Collections.sort(orderedListOfPlayers);
        Collections.reverse(orderedListOfPlayers);
        for (int i = 0; i < orderedListOfPlayers.size(); i++) {
            leaderboard.put(i, orderedListOfPlayers.get(i));
        }
        return leaderboard;
    }

    public void setSelectedResourcesForEachPlayer(Map<String, List<ResourceType>> chosenResourcesByPlayer) {
        for (Player player : this.playerList) {
            List<ResourceType> selectedResources = chosenResourcesByPlayer.get(player.getNickname());
            if (selectedResources != null) {
                player.setResources(selectedResources);
            }
        }
    }

    public void setAllPlayersToDisconnected() {
        for (Player player : this.playerList) {
            player.setIsConnected(false);
        }
        currentPlayer.setIsConnected(false);
    }

    public String getActivatedLeaderCardsToString(String nickname) {
        for (Player player : playerList) {
            if (player.getNickname().equals(nickname)) {
                return player.getActivatedLeaderCardsToString();
            }
        }
        return "";
    }
}
