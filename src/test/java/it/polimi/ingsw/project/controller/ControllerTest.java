package it.polimi.ingsw.project.controller;

import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.playermove.ExtractActionTokenMove;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.playermove.MoveList;
import it.polimi.ingsw.project.model.playermove.PlayerMove;
import it.polimi.ingsw.project.server.ClientConnection;
import it.polimi.ingsw.project.server.Server;
import it.polimi.ingsw.project.server.SocketClientConnection;
import it.polimi.ingsw.project.view.RemoteView;
import it.polimi.ingsw.project.view.View;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void update() {
    }

    @Test
    void lorenzoTest() {
        Player player = new Player("pinco pallino");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        Model model = new Model(playerList);
        MoveList moveList = new MoveList();
        Move extractActionTokenMove = new ExtractActionTokenMove();
        moveList.add(extractActionTokenMove);
        PlayerMove playerMove = new PlayerMove(player,
                null,moveList);
        Controller controller = new Controller(model);
        while(!(model.getMatch().getActionTokenContainer().getActionTokens().get(0) instanceof MoveActionToken)){
            model.getMatch().getActionTokenContainer().shuffle();
        }
        controller.update(playerMove);
        assertTrue(2==player.getBoard().getFaithMap().getBlackMarkerPosition());

    }

}