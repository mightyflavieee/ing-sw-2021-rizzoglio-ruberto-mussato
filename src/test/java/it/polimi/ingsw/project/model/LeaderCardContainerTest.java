package it.polimi.ingsw.project.model;

import com.google.gson.Gson;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardContainerTest {

    @Test
    void getLeaderCards() {
        LeaderCardContainerBuilder leaderCardContainerBuilder = new LeaderCardContainerBuilder(
                "src/main/resources/leadercards.json");
        List<LeaderCard> leaderCards = leaderCardContainerBuilder.getLeaderCards();
        assertEquals(16, leaderCards.size());
        assertEquals("id1", leaderCards.get(0).getId());
        assertEquals("id16", leaderCards.get(15).getId());
    }

    @Test
    void getFourCardsForPlayer() {

    }

    @Test
    void setJsonLeaderCard() {
        Gson gson = new Gson();

    }

    @Test
    void insertLeaderCardsAfterChoice() {

    }
}