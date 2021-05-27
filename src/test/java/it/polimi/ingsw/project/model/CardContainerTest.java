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
                assertEquals(4, cardContainer.getCardContainer().get(cardLevel).get(cardColor).size());
            }
        }
    }
    @Test
    void getAvailableDevCards(){
        CardContainer cardContainer = new CardContainer();
        assertEquals(12,cardContainer.getAvailableDevCards().size());
    }

}