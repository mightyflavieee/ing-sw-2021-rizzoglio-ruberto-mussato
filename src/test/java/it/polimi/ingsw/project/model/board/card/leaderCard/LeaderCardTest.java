package it.polimi.ingsw.project.model.board.card.leaderCard;

import it.polimi.ingsw.project.model.LeaderCardContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardTest {
    @Test
    void testToString() {
        LeaderCardContainer leaderCardContainer = new LeaderCardContainer();

        assertFalse(leaderCardContainer.getLeaderCards().get(0).toString().isEmpty());
    //    assertFalse(leaderCardContainer.getLeaderCards().get(0).toString().isBlank());

    }

}