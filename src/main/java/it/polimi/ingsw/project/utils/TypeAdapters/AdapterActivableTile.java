package it.polimi.ingsw.project.utils.TypeAdapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import it.polimi.ingsw.project.model.board.faithMap.tile.ActivableTile;
import it.polimi.ingsw.project.model.board.faithMap.tile.NormalTile;
import it.polimi.ingsw.project.model.board.faithMap.tile.PapalCouncilTile;
import it.polimi.ingsw.project.model.board.faithMap.tile.VictoryPointsTile;
import it.polimi.ingsw.project.utils.TypeAdapters.Deserializers.ActivableTileDeserializer;

/**
 * it is used to deserialize from json to the ActivableTile object
 */
public class AdapterActivableTile extends TypeAdapter<ActivableTile> {

  @Override
  public ActivableTile read(JsonReader reader) throws IOException {
    ActivableTileDeserializer activableTileSerializer = new ActivableTileDeserializer();
    String fieldName = "";

    reader.beginObject();

    while (reader.hasNext()) {
      JsonToken token = reader.peek();

      if (token.equals(JsonToken.NAME)) {
        // get the current token
        fieldName = reader.nextName();
      }

      if ("type".equals(fieldName)) {
        // move to next token
        token = reader.peek();
        activableTileSerializer.setType(reader.nextString());
      }

      if ("victoryPoints".equals(fieldName)) {
        // move to next token
        token = reader.peek();
        activableTileSerializer.setVictoryPoints(reader.nextInt());
      }

      if ("numTile".equals(fieldName)) {
        // move to next token
        token = reader.peek();
        activableTileSerializer.setNumTile(reader.nextInt());
      }

      if ("observers".equals(fieldName)) {
        token = reader.peek();
        reader.beginArray();
        reader.endArray();
      }
    }
    reader.endObject();

    switch (activableTileSerializer.getType()) {
      case "victoryPointsTile":
        return new VictoryPointsTile(activableTileSerializer.getVictoryPoints());
      case "papalCouncilTile":
        return new PapalCouncilTile(activableTileSerializer.getNumTile());
      default:
        return new NormalTile();
    }

  }

  @Override
  public void write(JsonWriter writer, ActivableTile observableObj) {
    // not needed for this object
  }
}