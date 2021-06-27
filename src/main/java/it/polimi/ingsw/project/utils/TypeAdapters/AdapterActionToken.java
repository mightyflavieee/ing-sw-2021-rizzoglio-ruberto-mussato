package it.polimi.ingsw.project.utils.TypeAdapters;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import it.polimi.ingsw.project.model.actionTokens.ActionToken;
import it.polimi.ingsw.project.model.actionTokens.DiscardActionToken;
import it.polimi.ingsw.project.model.actionTokens.MoveActionToken;
import it.polimi.ingsw.project.model.actionTokens.MoveAndShuffleActionToken;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.utils.TypeAdapters.Deserializers.ActionTokenDeserializer;

public class AdapterActionToken extends TypeAdapter<ActionToken> {

  @Override
  public ActionToken read(JsonReader reader) throws IOException {
    ActionTokenDeserializer actionTokenSerializer = new ActionTokenDeserializer();
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
        actionTokenSerializer.setType(reader.nextString());
      }

      if ("cardColor".equals(fieldName)) {
        // move to next token
        token = reader.peek();
        actionTokenSerializer.setCardColor(new Gson().fromJson(reader.nextString(), CardColor.class));
      }

      if ("observers".equals(fieldName)) {
        token = reader.peek();
        reader.beginArray();
        reader.endArray();
      }

    }
    reader.endObject();
    switch (actionTokenSerializer.getType()) {
      case "discardActionToken":
        return new DiscardActionToken(actionTokenSerializer.getCardColor());
      case "moveAndShuffleActionToken":
        return new MoveAndShuffleActionToken();
      default:
        return new MoveActionToken();
    }
  }

  @Override
  public void write(JsonWriter writer, ActionToken observableObj) {
    // not needed for this object
  }
}
