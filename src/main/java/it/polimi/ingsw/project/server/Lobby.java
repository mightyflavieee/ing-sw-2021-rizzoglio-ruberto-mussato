package it.polimi.ingsw.project.server;

import java.util.Map;

public class Lobby {
    private String id;
    private Integer maxNumberOfPlayers;
    private Map<String, ClientConnection> mapOfConnections;

    public Lobby(String id, Integer maxNumberOfPlayers, Map<String, ClientConnection> mapOfConnections) {
        this.id = id;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        this.mapOfConnections = mapOfConnections;
    }

    public String getId() {
        return this.id;
    }

    public Integer getMaxNumberOfPlayers() {
        return this.maxNumberOfPlayers;
    }

    public Map<String, ClientConnection> getMapOfConnections() {
        return mapOfConnections;
    }

    public Integer lenght() {
        return mapOfConnections.size();
    }

    public void insertPlayer(String name, ClientConnection connection) {
        this.mapOfConnections.put(name, connection);
    }
}
