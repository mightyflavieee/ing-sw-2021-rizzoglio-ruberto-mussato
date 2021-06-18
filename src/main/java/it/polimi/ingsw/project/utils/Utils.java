package it.polimi.ingsw.project.utils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.project.client.gui.market.TrayGUI;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import javax.imageio.ImageIO;
import javax.swing.*;

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

    
    public static boolean isIdPresent(List<LeaderCard> possibleCards, String chosenId) {
        for (LeaderCard leaderCard : possibleCards) {
            if (leaderCard.getId().equals(chosenId)) {
                return true;
            }
        }
        return false;
    }

    public static List<LeaderCard> extractSelectedLeaderCards(List<LeaderCard> possibleCards, List<String> listOfSelectedIds) {
        List<LeaderCard> selectedLeaderCards = new ArrayList<>();
        for (LeaderCard possibleCard : possibleCards) {
            if (possibleCard.getId().equals(listOfSelectedIds.get(0))
                    || possibleCard.getId().equals(listOfSelectedIds.get(1))) {
                selectedLeaderCards.add(possibleCard);
            }
        }
        return selectedLeaderCards;
    }

    public static Icon readIcon(String src, int width, int height){
        //sei già nella cartella risorse, quindi src è tipo leadercards.json
        try {
            return new ImageIcon(ImageIO.read(TrayGUI.class.getClassLoader().getResourceAsStream(src)).getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
