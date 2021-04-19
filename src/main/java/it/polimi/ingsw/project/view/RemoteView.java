package it.polimi.ingsw.project.view;

import java.util.List;

import it.polimi.ingsw.project.model.MoveMessage;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.observer.Observer;
import it.polimi.ingsw.project.server.ClientConnection;
import it.polimi.ingsw.project.utils.gameMessage;

public class RemoteView extends View {

    private class MessageReceiver implements Observer<String> {

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
            try {
                // la stringa (oppure oggetto) che ricevo dal client deve essere in qualche modo
                // convertita nel tipo Move
                //handleMove(message);
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
        String resultMsg = "";
        //TODO definire messaggi di fine partita
        /*
        boolean gameOver = message.getBoard().isGameOver(message.getPlayer().getMarker());
        boolean draw = message.getBoard().isFull();
        if (gameOver) {
            if (message.getPlayer() == getPlayer()) {
                resultMsg = gameMessage.winMessage + "\n";
            } else {
                resultMsg = gameMessage.loseMessage + "\n";
            }
        } else {
            if (draw) {
                resultMsg = gameMessage.drawMessage + "\n";
            }
        }
        if (message.getPlayer() == getPlayer()) {
            resultMsg += gameMessage.waitMessage;
        } else {
            resultMsg += gameMessage.moveMessage;
        }
        showMessage(resultMsg);
        */
    }



}
