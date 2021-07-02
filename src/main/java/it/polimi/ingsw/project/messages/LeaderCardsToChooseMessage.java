package it.polimi.ingsw.project.messages;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

public class LeaderCardsToChooseMessage implements ResponseMessage, Serializable {
    private static final long serialVersionUID = 3111280592475092704L;
    
    private final List<LeaderCard> leaderCardsToChoose;

    /**
     * it is the constructor of the LeaderCardsToChoose message sent by the server to the player with all the cards
     * that can be chosen by the player
     * @param leaderCardsToChoose list of the leaderCards that can choose the player
     */
    public LeaderCardsToChooseMessage(List<LeaderCard> leaderCardsToChoose) {
        this.leaderCardsToChoose = leaderCardsToChoose;
    }

    /**
     * it calls the function on the client that handles the selection of the initial leaderCards
     * @param client it is needed to call the method to choose the leaderCards
     */
    @Override
    public void action(Client client) {
        client.chooseLeaderCards(this.leaderCardsToChoose);
    }
}
