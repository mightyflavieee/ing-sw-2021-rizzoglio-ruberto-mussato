package it.polimi.ingsw.project.model;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardContainerTest {

    @Test
    void getLeaderCards() {
        LeaderCardContainerBuilder leaderCardContainerBuilder = new LeaderCardContainerBuilder(""
        );
        List<LeaderCard> leaderCards = leaderCardContainerBuilder.getLeaderCards();
        assertEquals(16, leaderCards.size());
        assertEquals("id1", leaderCards.get(0).getId());
        assertEquals("id16", leaderCards.get(15).getId());

    }



    @Test
    void testGetLeaderCards() {
        LeaderCardContainer leaderCardContainer = new LeaderCardContainer();
        assertEquals(16,leaderCardContainer.getLeaderCards().size());
        List<LeaderCard> leaderCardList = leaderCardContainer.getFourCardsForPlayer("ciao");
        leaderCardContainer.insertLeaderCardsAfterChoice(leaderCardList);
        assertEquals(16,leaderCardContainer.getLeaderCards().size());
    }

}