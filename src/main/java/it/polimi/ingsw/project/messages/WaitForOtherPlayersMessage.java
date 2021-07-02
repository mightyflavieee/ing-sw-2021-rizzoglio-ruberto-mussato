package it.polimi.ingsw.project.messages;

import java.io.Serializable;

import it.polimi.ingsw.project.client.Client;

public class WaitForOtherPlayersMessage implements Serializable, ResponseMessage {
  private static final long serialVersionUID = 384028059247511111L;

  /**
   * it calls the method on the client to wait for the other Players
   * @param client it is needed to call the method to wait for the other Players
   */
  @Override
  public void action(Client client) {
    client.showWaitMessageForOtherPlayers();
  }

}
