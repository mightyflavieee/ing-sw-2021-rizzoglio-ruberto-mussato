package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderCardContainer {
    private List<LeaderCard> leaderCards;

    public LeaderCardContainer() {
        LeaderCardContainerBuilder leaderCardContainerBuilder = new LeaderCardContainerBuilder("src/main/resources/leadercards.json");
        List<LeaderCard> leaderCards = leaderCardContainerBuilder.getLeaderCards();
        Collections.shuffle(leaderCards);
        this.leaderCards = leaderCards;
    }

    public List<LeaderCard> getLeaderCards() { return leaderCards; }

    public List<LeaderCard> getFourCardsForPlayer() {
        List<LeaderCard> cardsToAssign = new ArrayList<>();
        cardsToAssign.add(this.leaderCards.get(0));
        cardsToAssign.add(this.leaderCards.get(1));
        cardsToAssign.add(this.leaderCards.get(2));
        cardsToAssign.add(this.leaderCards.get(3));
        for (int i = 0; i < 4; i++) {
            this.leaderCards.remove(0);
        }
        return cardsToAssign;
    }

    public void insertLeaderCardsAfterChoice(List<LeaderCard> leaderCardsToReinsert) {
        this.leaderCards.addAll(leaderCardsToReinsert);
    }
}