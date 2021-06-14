package it.polimi.ingsw.project.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.project.controller.Controller;
import it.polimi.ingsw.project.model.LeaderCardContainer;
import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.view.RemoteView;

public class Lobby {
    private String id;
    private Model model;
    private Integer maxNumberOfPlayers;
    private Map<String, SocketClientConnection> mapOfSocketClientConnections;
    private LeaderCardContainer leaderCardContainer;
    private Map<String, List<LeaderCard>> chosenLeaderCardsByPlayer;
    private Map<String, RemoteView> mapOfViews;
    private Controller controller;

    public Lobby(String id, Integer maxNumberOfPlayers) {
        this.id = id;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.mapOfSocketClientConnections = new HashMap<>();
        this.leaderCardContainer = new LeaderCardContainer();
        this.chosenLeaderCardsByPlayer = new HashMap<String, List<LeaderCard>>();
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

    public String getId() {
        return this.id;
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
        if (this.mapOfSocketClientConnections.containsKey(nickName)
                && this.mapOfSocketClientConnections.get(nickName).getSocket().isClosed()) {
            return true;
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
            if (counter != 2) {
                return false;
            }
            return true;
        }
    }

    public void addChosenLeaderCardsToPlayer(String nickname, List<LeaderCard> leaderCards) {
        this.chosenLeaderCardsByPlayer.put(nickname, leaderCards);
    }

    public Map<String, List<LeaderCard>> getchosenLeaderCardsByPlayer() {
        return this.chosenLeaderCardsByPlayer;
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
