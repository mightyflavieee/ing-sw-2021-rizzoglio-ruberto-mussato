package it.polimi.ingsw.project.messages;

import it.polimi.ingsw.project.server.SocketClientConnection;

public class CreateRequestMove extends GameRequestMove {
    private final Integer numberOfPlayers;
    private final String nickName;

    public CreateRequestMove(Integer numberOfPlayers, String nickname) {
        this.numberOfPlayers = numberOfPlayers;
        this.nickName = nickname;
    }

    @Override
    public void action(SocketClientConnection connection) {
        String gameId = connection.getServer().createGame(this.numberOfPlayers);
        try {
            connection.getServer().addToLobby(gameId, connection, this.nickName);
            connection.send(new ConfirmJoinMessage(gameId));
            if (connection.getServer().tryToStartGame(gameId)) {
                connection.getServer().sendChooseLeaderCards(gameId);
            }
        } catch (Exception e) {
            connection.send(new ErrorJoinMessage(e.getMessage()));
        }
    }
    @Override
    public String toString(){
        return "Create Request Move, Nickname: " + this.nickName;
    }
}
