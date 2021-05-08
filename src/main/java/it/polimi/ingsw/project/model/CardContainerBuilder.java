package it.polimi.ingsw.project.model;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;

import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

public class CardContainerBuilder implements Serializable {
    public List<DevelopmentCard> allCards;

    public CardContainerBuilder(String fileSrc) {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(fileSrc)) {
            // Convert JSON File to Java Object
            this.allCards = gson.fromJson(reader, CardContainerBuilder.class).allCards;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
