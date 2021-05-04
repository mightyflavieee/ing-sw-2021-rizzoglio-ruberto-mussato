package it.polimi.ingsw.project.model.board.card.leaderCard;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.PerkType;
import it.polimi.ingsw.project.model.resource.ResourceType;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LeaderCardTest {

    @Test
    void getPerk() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void getId() {
    }

    @Test
    void activateCard() {
    }

    @Test
    void testToString() {
    }
    @Test
    void provaGson() throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/Leadercards_Test_0.json"));
        LeaderCard leaderCard = gson.fromJson(reader,LeaderCard.class);
        assertEquals("1",leaderCard.getId());
        assertEquals(ResourceType.Coin,leaderCard.getPerk().getResource().getType());
        assertEquals(PerkType.Transmutation, leaderCard.getPerk().getType());
//        assertEquals(1,leaderCard.getRequiredResources().get(ResourceType.Coin));
//        assertEquals(1,leaderCard.getRequiredResources().get(ResourceType.Servant));
//        assertEquals(1,leaderCard.getRequiredResources().get(ResourceType.Stone));
        assertEquals(Status.Active,leaderCard.getStatus());


    }
}