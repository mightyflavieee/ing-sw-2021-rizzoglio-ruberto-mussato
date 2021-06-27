package it.polimi.ingsw.project.server;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.project.controller.Controller;
import it.polimi.ingsw.project.model.LeaderCardContainer;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.view.RemoteView;

public class Lobby {
    private Model model;
    private final Integer maxNumberOfPlayers;
    private Map<String, SocketClientConnection> mapOfSocketClientConnections;
    private final LeaderCardContainer leaderCardContainer;
    private final Map<String, List<LeaderCard>> chosenLeaderCardsByPlayer;
    private final Map<String, List<ResourceType>> chosenResourcesByPlayer;
    private Map<String, RemoteView> mapOfViews;
    private List<Player> playerList;
    private Controller controller;
    private boolean isGameStarted = false;

    public Lobby(Integer maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.mapOfSocketClientConnections = new LinkedHashMap<>();
        this.leaderCardContainer = new LeaderCardContainer();
        this.chosenLeaderCardsByPlayer = new HashMap<>();
        this.chosenResourcesByPlayer = new HashMap<>();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted() {
        this.isGameStarted = true;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return this.controller;
    }

    public void setMapOfViews(Map<String, RemoteView> mapOfViews) {
        this.mapOfViews = mapOfViews;
    }

    public Map<String, RemoteView> getMapOfViews() {
        return this.mapOfViews;
    }

    public void setModel(Model model) {
        if (this.model == null) {
            this.model = model;
        }
    }

    public Model getModel() {
        return model;
    }

    public boolean isPlayerPresentAndDisconnected(String nickName) {
        if (this.mapOfSocketClientConnections.containsKey(nickName)) {
            return this.mapOfSocketClientConnections.get(nickName).getSocket().isClosed();
        }
        return false;
    }

    public boolean leaderCardsChosenWereRight(String nickname, List<LeaderCard> chosenLeaderCards) {
        Map<String, List<LeaderCard>> mapToCheck = this.leaderCardContainer.getMapOfExtractedCards();
        if (!mapToCheck.containsKey(nickname)) {
            return false;
        } else {
            int counter = 0;
            for (LeaderCard selectedLeaderCard : mapToCheck.get(nickname)) { // verifica che le due carte del player
                                                                             // siano veramente state scelte da lui
                if (selectedLeaderCard.getId().equals(chosenLeaderCards.get(0).getId())
                        || selectedLeaderCard.getId().equals(chosenLeaderCards.get(1).getId())) {
                    counter++;
                }
            }
            return counter == 2;
        }
    }

    public void addChosenLeaderCardsToPlayer(String nickname, List<LeaderCard> leaderCards) {
        this.chosenLeaderCardsByPlayer.put(nickname, leaderCards);
    }

    public Map<String, List<LeaderCard>> getchosenLeaderCardsByPlayer() {
        return this.chosenLeaderCardsByPlayer;
    }

    public void addChosenResourcesToPlayer(String nickname, List<ResourceType> resources) {
        this.chosenResourcesByPlayer.put(nickname, resources);
    }

    public Map<String, List<ResourceType>> getChosenResourcesByPlayer() {
        return this.chosenResourcesByPlayer;
    }

    public Integer getMaxNumberOfPlayers() {
        return this.maxNumberOfPlayers;
    }

    public Map<String, SocketClientConnection> getMapOfSocketClientConnections() {
        return this.mapOfSocketClientConnections;
    }

    public void setMapOfSocketClientConnections(Map<String, SocketClientConnection> mapOfSocketClientConnections) {
        this.mapOfSocketClientConnections = mapOfSocketClientConnections;
    }

    public Integer lenght() {
        return this.mapOfSocketClientConnections.size();
    }

    public LeaderCardContainer getLeaderCardContainer() {
        return leaderCardContainer;
    }

    public void insertPlayer(String name, SocketClientConnection connection) {
        this.mapOfSocketClientConnections.put(name, connection);
    }

}
