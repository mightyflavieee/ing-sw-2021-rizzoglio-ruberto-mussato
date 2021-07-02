package it.polimi.ingsw.project.messages;

import it.polimi.ingsw.project.server.SocketClientConnection;

public class CreateRequestMove extends GameRequestMove {
    private final Integer numberOfPlayers;
    private final String nickName;


    /**
     * it is sent bby the player when he wants to create a game
     * @param numberOfPlayers number of the size of the lobby
     * @param nickname the nickname of the player who sent the request
     */
    public CreateRequestMove(Integer numberOfPlayers, String nickname) {
        this.numberOfPlayers = numberOfPlayers;
        this.nickName = nickname;
    }

    /**
     * it creates the game and sent it back to the player or it sends an errorJoinMessage to reInitiate the client
     * @param connection the socketClient connection of the player to send message back to him
     */
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
