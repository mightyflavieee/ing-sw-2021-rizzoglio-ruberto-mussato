package it.polimi.ingsw.project.utils.TypeAdapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import it.polimi.ingsw.project.model.board.faithMap.tile.PapalCouncilTile;

/**
 * it is used to serialize to json the PapalCouncilTile object
 */
public class AdapterPapalCouncilTile extends TypeAdapter<PapalCouncilTile> {

  @Override
  public PapalCouncilTile read(JsonReader reader) {
    return null;
  }

  @Override
  public void write(JsonWriter writer, PapalCouncilTile observableObj) throws IOException {
    writer.beginObject();
    writer.name("numTile");
    writer.value(observableObj.getNumTile());
    writer.name("type");
    writer.value(observableObj.getType());
    writer.name("observers");
    writer.beginArray();
    writer.endArray();
    writer.endObject();
  }
}
