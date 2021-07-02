package it.polimi.ingsw.project.utils.TypeAdapters;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import it.polimi.ingsw.project.model.market.Market;

/**
 * it is used to serialize to json the Market object
 */
public class AdapterMarket extends TypeAdapter<Market> {

  @Override
  public Market read(JsonReader reader) {
    return null;
  }

  @Override
  public void write(JsonWriter writer, Market observableObj) throws IOException {
    writer.beginObject();
    writer.name("tray");
    writer.jsonValue(new Gson().toJson(observableObj.getTray()));
    writer.name("outsideMarble");
    writer.jsonValue(new Gson().toJson(observableObj.getOutSideMarble()));
    writer.name("observers");
    writer.beginArray();
    writer.endArray();
    writer.endObject();
  }
}