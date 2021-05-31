package it.polimi.ingsw.project.model.playermove;

import java.util.List;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.server.SocketClientConnection;

public class ChooseLeaderCardMove extends GameRequestMove {
  final String nickname;
  final String gameId;
  final List<LeaderCard> listOfSelectedCards;

  public ChooseLeaderCardMove(String nickname, String gameId, List<LeaderCard> listOfSelectedCards) {
    this.nickname = nickname;
    this.gameId = gameId;
    this.listOfSelectedCards = listOfSelectedCards;
  }

  @Override
  public void action(SocketClientConnection connection) {
    connection.getServer().addChosenCardsToPlayer(gameId, nickname, listOfSelectedCards);
  }

  @Override
  public String toString() {
    return "Choose Leader Card Game Request Move, Nickname: " + this.nickname;
  }
}
