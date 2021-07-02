package it.polimi.ingsw.project.utils;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import it.polimi.ingsw.project.client.gui.market.MarbleTrayGUI;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.resource.ResourceType;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Utils {
    private static Map<String, Image> imageMap;

    public static List<String> extractOpponentsName(Player currentPlayer, List<Player> listOfAllPlayers) {
        List<String> listOfOpponentsName = new ArrayList<>();
        listOfAllPlayers.forEach((Player opponentPlayer) -> {
            if (!opponentPlayer.getNickname().equals(currentPlayer.getNickname()))
                listOfOpponentsName.add(opponentPlayer.getNickname());
        });
        return listOfOpponentsName;
    }

    public static Pair<Player, List<Player>> splitPlayers(Match match, String nickame) {
        List<Player> playerList = new ArrayList<>(match.getPlayerList());

        Player player = null;
        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).getNickname().equals(nickame)) {
                player = playerList.remove(i);
                break;
            }
        }
        return new Pair<>(player, playerList);
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

    public static Icon readIcon(String src, int width, int height) {
        //sei già nella cartella risorse, quindi src è tipo leadercards.json
        if (imageMap == null) {
            imageMap = new HashMap<>();
        }
        if (imageMap.containsKey(src)) {
            return new ImageIcon(imageMap.get(src).getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } else {
            try {
                Image image = ImageIO.read(Objects.requireNonNull(MarbleTrayGUI.class.getClassLoader().getResourceAsStream(src)));
                imageMap.put(src, image);
                return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(src);
            }
            return null;
        }
    }

    public static boolean isOneLevelUpper(CardLevel big, CardLevel small) {
        switch (big) {
            case Two:
                return small == CardLevel.One;
            case Three:
                return small == CardLevel.Two;
            default:
                return false;
        }
    }

    public static Map<ResourceType, Integer> sumResourcesMaps(Map<ResourceType, Integer> resourcesToSum1, Map<ResourceType, Integer> resourcesToSum2) {
        Map<ResourceType, Integer> sumRequiredResources = new HashMap<>();
        List<ResourceType> resourceTypes = new ArrayList<>();
        resourceTypes.add(ResourceType.Coin);
        resourceTypes.add(ResourceType.Servant);
        resourceTypes.add(ResourceType.Shield);
        resourceTypes.add(ResourceType.Stone);
        resourceTypes.add(ResourceType.Faith);

        if (resourcesToSum1 != null) {
            if (resourcesToSum2 != null) {
                for (ResourceType resourceType : resourceTypes) {
                    if (resourcesToSum1.containsKey(resourceType)) {
                        if (resourcesToSum2.containsKey(resourceType)) {
                            sumRequiredResources.put(resourceType,
                                    resourcesToSum1.get(resourceType) + resourcesToSum2.get(resourceType));
                        } else {
                            sumRequiredResources.put(resourceType, resourcesToSum1.get(resourceType));
                        }
                    } else {
                        if (resourcesToSum2.containsKey(resourceType)) {
                            sumRequiredResources.put(resourceType, resourcesToSum2.get(resourceType));
                        }
                    }
                }
            } else {
                sumRequiredResources = resourcesToSum1;
            }
        } else {
            if (resourcesToSum2 != null) {
                sumRequiredResources = resourcesToSum2;
            }
        }
        return sumRequiredResources;
    }


    /**
     * it checks if the two maps are equal one another
     * @param resourcesMap1 first map
     * @param resourcesMap2 second map
     * @return true if the two maps have the same values, false if not
     */
    // checks if two maps have the same values
    public static boolean compareResourcesMaps(Map<ResourceType, Integer> resourcesMap1, Map<ResourceType, Integer> resourcesMap2) {
        List<ResourceType> resourceTypes = new ArrayList<>();
        resourceTypes.add(ResourceType.Coin);
        resourceTypes.add(ResourceType.Servant);
        resourceTypes.add(ResourceType.Shield);
        resourceTypes.add(ResourceType.Stone);
        for (ResourceType type : resourceTypes) {
            if (resourcesMap1.containsKey(type)) {
                if (resourcesMap2.containsKey(type)) {
                    if (!resourcesMap1.get(type).equals(resourcesMap2.get(type))) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                if (resourcesMap2.containsKey(type)) {
                    return false;
                }
            }
        }
        return true;
    }
}
