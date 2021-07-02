package it.polimi.ingsw.project.messages;

import java.util.List;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

public class ErrorChosenLeaderCards extends ErrorMessage implements ResponseMessage {
    final List<LeaderCard> possibleLeaderCards;

    /**
     * @param errorMessage it is the message to be notified to the client
     * @param possibleLeaderCards are the leaderCards that needs to be selected by the player
     */
    public ErrorChosenLeaderCards(String errorMessage, List<LeaderCard> possibleLeaderCards) {
        setErrorMessage(errorMessage);
        this.possibleLeaderCards = possibleLeaderCards;
    }

    /**
     * it calls on the client the function to handle the error of choosing leaderCards
     */
    @Override
    public void action(Client client) {
        client.reChooseLeaderCards(super.messageError, this.possibleLeaderCards);
    }

}
