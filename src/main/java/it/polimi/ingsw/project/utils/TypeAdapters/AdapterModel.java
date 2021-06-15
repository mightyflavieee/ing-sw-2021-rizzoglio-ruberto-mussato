package it.polimi.ingsw.project.utils.TypeAdapters;

import java.io.IOException;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import it.polimi.ingsw.project.model.Model;
import it.polimi.ingsw.project.model.actionTokens.DiscardActionToken;
import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.actionTokens.MoveAndShuffleActionToken;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.board.faithMap.tile.PapalCouncilTile;
import it.polimi.ingsw.project.model.board.faithMap.tile.VictoryPointsTile;
import it.polimi.ingsw.project.model.market.Market;

public class AdapterModel extends TypeAdapter<Model> {

  @Override 
  public Model read(JsonReader reader) throws IOException {
    return null;
  }

  @Override
  public void write(JsonWriter writer, Model observableObj) throws IOException {
    writer.beginObject();
    writer.name("match");
    writer.jsonValue(new GsonBuilder().registerTypeAdapter(Warehouse.class, new AdapterWarehouse())
        .registerTypeAdapter(Market.class, new AdapterMarket())
        .registerTypeAdapter(VictoryPointsTile.class, new AdapterVictoryPointsTile())
        .registerTypeAdapter(PapalCouncilTile.class, new AdapterPapalCouncilTile())
        .registerTypeAdapter(MoveAndShuffleActionToken.class, new AdapterMoveAndShuffleActionToken())
        .registerTypeAdapter(MoveActionToken.class, new AdapterMoveActionToken())
        .registerTypeAdapter(DiscardActionToken.class, new AdapterDiscardActionToken()).setPrettyPrinting().create()
        .toJson(observableObj.getMatch()));
    writer.name("matchUID");
    writer.value(observableObj.getMatchUID());
    writer.name("observers");
    writer.beginArray();
    writer.endArray();
    writer.endObject();
  }
}
