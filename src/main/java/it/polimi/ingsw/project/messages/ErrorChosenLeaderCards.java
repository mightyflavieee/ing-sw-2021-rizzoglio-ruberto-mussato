package it.polimi.ingsw.project.messages;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

public class ErrorChosenLeaderCards extends ErrorMessage implements ResponseMessage, Serializable {
    final List<LeaderCard> possibleLeaderCards;

    public ErrorChosenLeaderCards(String errorMessage, List<LeaderCard> possibleLeaderCards) {
        setErrorMessage(errorMessage);
        this.possibleLeaderCards = possibleLeaderCards;
    }

    @Override
    public void action(Client client) {
        client.reChooseLeaderCards(super.errorMessage, this.possibleLeaderCards);
    }

}
