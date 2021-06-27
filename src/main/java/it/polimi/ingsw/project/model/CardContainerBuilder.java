package it.polimi.ingsw.project.model;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;

@SuppressWarnings("unused")
public class CardContainerBuilder implements Serializable {
    private static final long serialVersionUID = 3840280592473292704L;
    public List<DevelopmentCard> allCards;

    public CardContainerBuilder(String fileSrc) {
        Gson gson = new Gson();

        try {
            InputStream inputStream = CardContainerBuilder.class.getClassLoader()
                    .getResourceAsStream("developmentCards.json");
            Scanner s = new Scanner(inputStream);
            String string = "";
            while (s.hasNext()) {
                string = string + s.nextLine();
            }
            s.close();
            this.allCards = gson.fromJson(string, CardContainerBuilder.class).allCards;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
