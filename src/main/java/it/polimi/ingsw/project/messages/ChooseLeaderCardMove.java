package it.polimi.ingsw.project.messages;

import java.util.List;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class ChooseLeaderCardMove extends GameRequestMove {
  final String nickname;
  final String gameId;
  final List<LeaderCard> listOfSelectedCards;

  /**
   * it construct the GameRequestMove for the choosing of the leaderCards
   * @param gameId it is the id of the game
   * @param nickname it is the name of the player that is choosing the cards
   * @param listOfSelectedCards the list of the cards chosen by the player
   */
  public ChooseLeaderCardMove(String nickname, String gameId, List<LeaderCard> listOfSelectedCards) {
    this.nickname = nickname;
    this.gameId = gameId;
    this.listOfSelectedCards = listOfSelectedCards;
  }

  /**
   * it adds the leaderCards chosen by the player to the player with that specific nickname
   * and in case on wrong ids it resends the cards to the player
   * @param connection it is the connection of the player
   */
  @Override
  public void action(SocketClientConnection connection) {
    connection.getServer().addChosenCardsToPlayer(gameId, nickname, listOfSelectedCards);
  }

  @Override
  public String toString() {
    return "Choose Leader Card Game Request Move, Nickname: " + this.nickname;
  }
}
