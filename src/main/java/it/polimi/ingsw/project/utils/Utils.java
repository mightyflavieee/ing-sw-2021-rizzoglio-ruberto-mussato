package it.polimi.ingsw.project.utils;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.project.model.Player;

public class Utils {
    public static List<String> extractOpponentsName(Player currentPlayer, List<Player> listOfAllPlayers) {
        List<String> listOfOpponentsName = new ArrayList<String>();
        listOfAllPlayers.remove(currentPlayer);
        listOfAllPlayers.forEach((Player opponentPlayer) -> {
            listOfOpponentsName.add(opponentPlayer.getNickname());
        });
        return listOfOpponentsName;
    }
}
