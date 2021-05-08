package it.polimi.ingsw.project.model;

import org.junit.jupiter.api.Test;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;

import static org.junit.jupiter.api.Assertions.*;

class CardContainerTest {

    @Test
    void constructor() {
        CardContainer cardContainer = new CardContainer();
        for (CardLevel cardLevel : CardLevel.values()) {
            for (CardColor cardColor : CardColor.values()) {
                assertTrue(cardContainer.getCardContainer().get(cardLevel).get(cardColor).size() == 4);
            }
        }
    }

    @Test
    void getCardContainer() {
    }

    @Test
    void discard() {
    }

    @Test
    void isCardPresent() {
    }

    @Test
    void fetchCard() {
    }

    @Test
    void removeBoughtCard() {
    }
}