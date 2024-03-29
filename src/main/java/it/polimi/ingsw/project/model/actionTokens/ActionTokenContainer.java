package it.polimi.ingsw.project.model.actionTokens;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.card.CardColor;
import it.polimi.ingsw.project.observer.custom.DiscardActionTokenObserver;
import it.polimi.ingsw.project.observer.custom.MoveActionTokenObserver;
import it.polimi.ingsw.project.observer.custom.MoveAndShuffleActionTokenObserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// è un Singleton
public class ActionTokenContainer implements Serializable {
    private static final long serialVersionUID = 3840280592475092999L;

    private final List<ActionToken> actionTokens;

    /**
     * constructor of it.polimi.ingsw.project.model.ActionTokens.ActionTokenContainer, it adds all the different types of actionToken and then shuffles all actionToken
     * @param match the match passed by the controller
     */

    public ActionTokenContainer(Match match) {
        this.actionTokens = new ArrayList<>();
        MoveActionToken moveActionToken = new MoveActionToken();
        moveActionToken.addObserver(new MoveActionTokenObserver(match));
        this.actionTokens.add(moveActionToken);
        MoveAndShuffleActionToken moveAndShuffleActionToken = new MoveAndShuffleActionToken();
        moveAndShuffleActionToken.addObserver(new MoveAndShuffleActionTokenObserver(match, this));
        this.actionTokens.add(moveAndShuffleActionToken);
        DiscardActionToken amethist = new DiscardActionToken(CardColor.Amethyst);
        DiscardActionToken emerald = new DiscardActionToken(CardColor.Emerald);
        DiscardActionToken sapphire = new DiscardActionToken(CardColor.Sapphire);
        DiscardActionToken gold = new DiscardActionToken(CardColor.Gold);
        amethist.addObserver(new DiscardActionTokenObserver(match));
        emerald.addObserver(new DiscardActionTokenObserver(match));
        sapphire.addObserver(new DiscardActionTokenObserver(match));
        gold.addObserver(new DiscardActionTokenObserver(match));
        this.actionTokens.add(amethist);
        this.actionTokens.add(emerald);
        this.actionTokens.add(sapphire);
        this.actionTokens.add(gold);
        this.shuffle();
    }

    public List<ActionToken> getActionTokens() {
        return new ArrayList<>(this.actionTokens);
    }

    public void shuffle() {
        Collections.shuffle(actionTokens);
    }

    /**
     * it extracted the token for single player
     * @return it returns the token extracted for the single player
     */
    public String drawToken() {
        ActionToken firstActionToken = this.actionTokens.get(0);
        firstActionToken.Action();
        this.actionTokens.remove(0);
        this.actionTokens.add(firstActionToken);
        return firstActionToken.toString();
    }

    /**
     * it readds all the observers after recreation of the game
     * @param match it is passed by controller
     */
    public void readdObservers(Match match) {
        for (ActionToken actionToken : actionTokens) {
            actionToken.addObserverBasedOnType(match, this);
        }
    }

}
