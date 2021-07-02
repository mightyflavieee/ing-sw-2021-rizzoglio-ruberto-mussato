package it.polimi.ingsw.project.utils.TypeAdapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import it.polimi.ingsw.project.model.actionTokens.MoveAndShuffleActionToken;

/**
 * it is used to serialize to json the MoveAndShuffleActionToken object
 */
public class AdapterMoveAndShuffleActionToken extends TypeAdapter<MoveAndShuffleActionToken> {

  @Override
  public MoveAndShuffleActionToken read(JsonReader reader) {
    return null;
  }

  @Override
  public void write(JsonWriter writer, MoveAndShuffleActionToken observableObj) throws IOException {
    writer.beginObject();
    writer.name("type");
    writer.value(observableObj.getType());
    writer.name("observers");
    writer.beginArray();
    writer.endArray();
    writer.endObject();
  }
}
