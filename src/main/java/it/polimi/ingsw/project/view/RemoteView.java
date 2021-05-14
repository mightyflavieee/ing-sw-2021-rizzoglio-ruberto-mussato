package it.polimi.ingsw.project.view;

import java.util.List;

import it.polimi.ingsw.project.model.MoveMessage;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.observer.Observer;
import it.polimi.ingsw.project.server.ClientConnection;

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

    public RemoteView(Player player, List<String> opponents, ClientConnection c) {
        super(player);
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
        c.asyncSend("Your opponents are:");
        for (String nameOpponent : opponents) {
            c.asyncSend(nameOpponent);
        }

    }

    @Override
    protected void showMessage(Object message) {
        clientConnection.asyncSend(message);
    }

    @Override
    public void update(MoveMessage message) {
        //messaggio che mando al player
        showMessage(message.getMatch());
    }



}
