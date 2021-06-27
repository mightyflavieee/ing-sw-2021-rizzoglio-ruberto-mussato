package it.polimi.ingsw.project.utils.TypeAdapters;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import it.polimi.ingsw.project.model.board.Warehouse;

public class AdapterWarehouse extends TypeAdapter<Warehouse> {

  @Override
  public Warehouse read(JsonReader reader) {
    return null;
  }

  @Override
  public void write(JsonWriter writer, Warehouse observableObj) throws IOException {
    writer.beginObject();
    writer.name("shelves");
    writer.jsonValue(new Gson().toJson(observableObj.getShelves()));
    writer.name("extraDeposit");
    writer.jsonValue(new Gson().toJson(observableObj.getExtraDeposit()));
    writer.name("numResourcesToDiscard");
    writer.value(observableObj.getNumResourcesToDiscard());
    writer.name("observers");
    writer.beginArray();
    writer.endArray();
    writer.endObject();
  }
}
