package it.polimi.ingsw.project.utils.TypeAdapters;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import it.polimi.ingsw.project.model.actionTokens.DiscardActionToken;

public class AdapterDiscardActionToken extends TypeAdapter<DiscardActionToken> {

  @Override
  public DiscardActionToken read(JsonReader reader) {
    return null;
  }

  @Override
  public void write(JsonWriter writer, DiscardActionToken observableObj) throws IOException {
    writer.beginObject();
    writer.name("cardColor");
    writer.jsonValue(new Gson().toJson(observableObj.getCardColor()));
    writer.name("type");
    writer.value(observableObj.getType());
    writer.name("observers");
    writer.beginArray();
    writer.endArray();
    writer.endObject();
  }
}
