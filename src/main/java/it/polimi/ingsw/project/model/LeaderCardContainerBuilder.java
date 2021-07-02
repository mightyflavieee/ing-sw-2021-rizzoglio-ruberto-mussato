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

    /**
     * it is the builder used to read the json of the cardContainer
     * @param src file source for the json
     */
    public LeaderCardContainerBuilder(String src) {
        Gson gson = new Gson();
        try {
             InputStream inputStream = LeaderCardContainerBuilder.class.getClassLoader().getResourceAsStream("leadercards.json");
            assert inputStream != null;
            Scanner s = new Scanner(inputStream);
            StringBuilder string = new StringBuilder();
            while (s.hasNext()){
                string.append(s.nextLine());
            }
            s.close();
            this.leaderCards = gson.fromJson(string.toString(), LeaderCardContainerBuilder.class).leaderCards;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }
}
