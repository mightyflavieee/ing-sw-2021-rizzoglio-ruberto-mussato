package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;

public class WaitForLeaderCardsMessage implements Serializable, ResponseMessage {

  @Override
  public void action(Client client) {
    client.showWaitMessageForOtherPlayers();
  }

}
