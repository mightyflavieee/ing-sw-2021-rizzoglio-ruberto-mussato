package it.polimi.ingsw.project.messages;

import java.util.List;

import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class ChooseResourcesMove extends GameRequestMove {
    final String nickname;
    final String gameId;
    final List<ResourceType> selectedResources;

    /**
     * it construct the GameRequestMove for the choosing of the resources
     * @param gameId it is the id of the game
     * @param nickname it is the name of the player that is choosing the cards
     * @param selectedResources the list of the resources chosen by the player
     */
    public ChooseResourcesMove(String nickname, String gameId, List<ResourceType> selectedResources) {
        this.nickname = nickname;
        this.gameId = gameId;
        this.selectedResources = selectedResources;
    }

    /**
     * it adds the resources chosen by the player to the player with that specific nickname
     * @param connection it is the connection of the player
     */
    @Override
    public void action(SocketClientConnection connection) {
        connection.getServer().addChosenResourcesToPlayer(gameId, nickname, selectedResources);
    }

    @Override
    public String toString() {
        return "Choose Resources Game Request Move, Nickname: " + this.nickname;
    }
}
