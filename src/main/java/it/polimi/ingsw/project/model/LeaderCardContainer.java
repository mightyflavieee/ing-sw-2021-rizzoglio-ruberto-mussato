package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderCardContainer implements Serializable {
    private static final long serialVersionUID = 384028059247505555L;
    private final List<LeaderCard> leaderCards;
    private final Map<String, List<LeaderCard>> mapOfExtractedCards;

    /**
     * it construct the container of the leaderCards, all read from a Json File
     */
    public LeaderCardContainer() {
        LeaderCardContainerBuilder leaderCardContainerBuilder = new LeaderCardContainerBuilder(""
        );
        List<LeaderCard> leaderCards = leaderCardContainerBuilder.getLeaderCards();
        Collections.shuffle(leaderCards);
        this.mapOfExtractedCards = new HashMap<>();
        this.leaderCards = leaderCards;
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public Map<String, List<LeaderCard>> getMapOfExtractedCards() {
        return mapOfExtractedCards;
    }

    /**
     * it extracts and remove from the leaderCardsContainer the 4 cards chosen for the player and saves the choices for
     * the player in a map nickname,list of leaderCard to check if the chosen leaderCards are right
     * @param nicknameForCards nickname of the player to where we sent the card
     * @return it returns the list of cards selected for the player
     */
    public List<LeaderCard> getFourCardsForPlayer(String nicknameForCards) {
        List<LeaderCard> cardsToAssign = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cardsToAssign.add(this.leaderCards.remove(0));
        }
        mapOfExtractedCards.put(nicknameForCards, cardsToAssign);
        return cardsToAssign;
    }

    public void insertLeaderCardsAfterChoice(List<LeaderCard> leaderCardsToReinsert) {
        this.leaderCards.addAll(leaderCardsToReinsert);
    }
}
