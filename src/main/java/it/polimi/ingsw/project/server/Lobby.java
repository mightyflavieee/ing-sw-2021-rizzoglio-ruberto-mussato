package it.polimi.ingsw.project.server;

import java.util.List;

public class Lobby {
    private String id;
    private Integer maxNumberOfPlayers;
    private List<PlayerConnection> listOfPlayerConnections;

    public Lobby(String id, Integer maxNumberOfPlayers, List<PlayerConnection> listOfPlayerConnections) {
        this.id = id;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.listOfPlayerConnections = listOfPlayerConnections;
    }

    public String getId() {
        return this.id;
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

    public void insertPlayer(String name, ClientConnection connection) {
        PlayerConnection newPlayerConnection = new PlayerConnection(name, connection);
        this.listOfPlayerConnections.add(newPlayerConnection);
    }
}
