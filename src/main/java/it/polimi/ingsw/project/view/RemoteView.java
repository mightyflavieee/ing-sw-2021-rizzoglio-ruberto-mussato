package it.polimi.ingsw.project.view;

import it.polimi.ingsw.project.messages.MoveMessage;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.observer.Observer;
import it.polimi.ingsw.project.server.ClientConnection;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<Move> {

        @Override
        public void update(Move message) {
            System.out.println("Received: " + message.toString());
            try {
                handleMove(message);
            } catch (IllegalArgumentException e) {
                clientConnection.asyncSend("Error!");
            }
        }
    }

    private ClientConnection clientConnection;

    public RemoteView(Player player, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        this.clientConnection.addObserver(new MessageReceiver());

    }

    public void setClientConnection(SocketClientConnection socketClientConnection) {
        this.clientConnection = socketClientConnection;
    }

    @Override
    protected void showMessage(Object message) {
        this.clientConnection.asyncSend(message);
    }

    @Override
    public void update(MoveMessage message) {
        // messaggio che mando al player
        showMessage(message);
    }

}
