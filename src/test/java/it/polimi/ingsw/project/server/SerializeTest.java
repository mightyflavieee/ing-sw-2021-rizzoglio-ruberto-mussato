package it.polimi.ingsw.project.server;

import java.io.FileReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.actionTokens.ActionToken;
import it.polimi.ingsw.project.model.board.faithMap.tile.ActivableTile;
import it.polimi.ingsw.project.utils.TypeAdapters.AdapterActionToken;
import it.polimi.ingsw.project.utils.TypeAdapters.AdapterActivableTile;

class SerializeTest {

  @Test
  void deserializedTest() {
    try {
      Server server = new Server();
      //server.recreateLobby("e9ffa");
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

}
