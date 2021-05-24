package it.polimi.ingsw.project.model;

import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;

import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;

public class LeaderCardContainerBuilder implements Serializable {
    private List<LeaderCard> leaderCards;

    public LeaderCardContainerBuilder(String src) {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader(src);
            this.leaderCards = gson.fromJson(reader, LeaderCardContainerBuilder.class).leaderCards;
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }
}
