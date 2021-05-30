package it.polimi.ingsw.project.messages;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

public class LeaderCardsToChooseMessage implements ResponseMessage, Serializable {

    private List<LeaderCard> leaderCardsToChoose;

    public LeaderCardsToChooseMessage(List<LeaderCard> leaderCardsToChoose) {
        this.leaderCardsToChoose = leaderCardsToChoose;
    }

    @Override
    public void action(Client client) {
        client.chooseLeaderCards(this.leaderCardsToChoose);
    }
}
