package it.polimi.ingsw.project.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.project.model.LeaderCardContainer;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

public class Lobby {
    private String id;
    private Integer maxNumberOfPlayers;
    private List<PlayerConnection> listOfPlayerConnections;
    private LeaderCardContainer leaderCardContainer;
    private Map<String, List<LeaderCard>> chosenLeaderCardsByPlayer;

    public Lobby(String id, Integer maxNumberOfPlayers, List<PlayerConnection> listOfPlayerConnections) {
        this.id = id;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.listOfPlayerConnections = listOfPlayerConnections;
        this.leaderCardContainer = new LeaderCardContainer();
        this.chosenLeaderCardsByPlayer = new HashMap<String, List<LeaderCard>>();
    }

    public String getId() {
        return this.id;
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

    public List<PlayerConnection> getListOfPlayerConnections() {
        return listOfPlayerConnections;
    }

    public Integer lenght() {
        return listOfPlayerConnections.size();
    }

    public LeaderCardContainer getLeaderCardContainer() {
        return leaderCardContainer;
    }

    public void insertPlayer(String name, ClientConnection connection) {
        PlayerConnection newPlayerConnection = new PlayerConnection(name, connection);
        this.listOfPlayerConnections.add(newPlayerConnection);
    }
}
