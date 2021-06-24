package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;

public class WaitForOtherPlayersMessage implements Serializable, ResponseMessage {
  private static final long serialVersionUID = 384028059247511111L;

  @Override
  public void action(Client client) {
    client.showWaitMessageForOtherPlayers();
  }

}
