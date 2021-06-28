package it.polimi.ingsw.project.messages;

import java.util.List;

import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class ChooseResourcesMove extends GameRequestMove {
    final String nickname;
    final String gameId;
    final List<ResourceType> selectedResources;

    public ChooseResourcesMove(String nickname, String gameId, List<ResourceType> selectedResources) {
        this.nickname = nickname;
        this.gameId = gameId;
        this.selectedResources = selectedResources;
    }

    @Override
    public void action(SocketClientConnection connection) {
        connection.getServer().addChosenResourcesToPlayer(gameId, nickname, selectedResources);
    }

    @Override
    public String toString() {
        return "Choose Resources Game Request Move, Nickname: " + this.nickname;
    }
}
