package it.polimi.ingsw.project.model;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

@SuppressWarnings("unused")
public class LeaderCardContainerBuilder implements Serializable {
    private static final long serialVersionUID = 3840280592475092666L;
    private List<LeaderCard> leaderCards;

    public LeaderCardContainerBuilder(String src) {
        Gson gson = new Gson();
        try {
             InputStream inputStream = LeaderCardContainerBuilder.class.getClassLoader().getResourceAsStream("leadercards.json");
             Scanner s = new Scanner(inputStream);
            String string = "";
            while (s.hasNext()){
                string = string + s.nextLine();
            }
            s.close();
            this.leaderCards = gson.fromJson(string, LeaderCardContainerBuilder.class).leaderCards;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }
}
