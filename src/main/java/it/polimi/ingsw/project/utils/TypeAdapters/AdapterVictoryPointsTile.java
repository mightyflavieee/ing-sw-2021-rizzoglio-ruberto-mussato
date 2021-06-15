package it.polimi.ingsw.project.utils.TypeAdapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import it.polimi.ingsw.project.model.board.faithMap.tile.VictoryPointsTile;

public class AdapterVictoryPointsTile extends TypeAdapter<VictoryPointsTile> {

  @Override
  public VictoryPointsTile read(JsonReader reader) throws IOException {
    return null;
  }

  @Override
  public void write(JsonWriter writer, VictoryPointsTile observableObj) throws IOException {
    writer.beginObject();
    writer.name("type");
    writer.value(observableObj.getType());
    writer.name("victoryPoints");
    writer.value(observableObj.getVictorypoints());
    writer.name("observers");
    writer.beginArray();
    writer.endArray();
    writer.endObject();
  }
}
