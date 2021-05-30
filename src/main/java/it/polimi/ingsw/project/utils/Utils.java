package it.polimi.ingsw.project.utils;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;

public class Utils {
    public static List<String> extractOpponentsName(Player currentPlayer, List<Player> listOfAllPlayers) {
        List<String> listOfOpponentsName = new ArrayList<>();
        listOfAllPlayers.forEach((Player opponentPlayer) -> {
            if(!opponentPlayer.getNickname().equals(currentPlayer.getNickname()))
            listOfOpponentsName.add(opponentPlayer.getNickname());
        });
        return listOfOpponentsName;
    }
    public static Pair<Player, List<Player>> splitPlayers(Match match, String nickame){
        List<Player> playerList = new ArrayList<>();
        playerList = match.getPlayerList();
        Player player = null;
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getNickname().equals(nickame)){
                player = playerList.remove(i);
                break;
            }
        }
        return new Pair<>(player,playerList);
    }

}
