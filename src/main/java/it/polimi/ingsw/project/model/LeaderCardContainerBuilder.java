package it.polimi.ingsw.project.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.model.board.card.CardLevel;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.Perk;
import it.polimi.ingsw.project.model.board.card.leaderCard.perk.PerkType;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

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
