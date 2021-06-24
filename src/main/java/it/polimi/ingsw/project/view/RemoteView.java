package it.polimi.ingsw.project.view;


import it.polimi.ingsw.project.messages.MoveMessage;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.interfaces.Controllable;
import it.polimi.ingsw.project.observer.Observer;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class RemoteView extends View  {

    private class MessageReceiver implements Observer<Controllable> {

        @Override
        public void update(Controllable message) {
          //  System.out.println("Received: " + message.toString());
            try {
                handleMove(message);
            } catch (IllegalArgumentException e) {
                clientConnection.asyncSend("Error!");
            }
        }
    }

    private SocketClientConnection clientConnection;

    public RemoteView(Player player, SocketClientConnection c) {
        super(player);
        this.clientConnection = c;
        this.clientConnection.addObserver(new MessageReceiver());

    }

    public void setClientConnection(SocketClientConnection socketClientConnection) {
        this.clientConnection = socketClientConnection;
        this.clientConnection.removeObserver(new MessageReceiver());
        this.clientConnection.addObserver(new MessageReceiver());
    }

    @Override
    protected void showMessage(MoveMessage message) {
        if (!this.clientConnection.getSocket().isClosed()) {
            this.clientConnection.asyncSend(message);
        }
    }

    @Override
    public void update(MoveMessage message) {
        // messaggio che mando al player
        showMessage(message);
    }

}
