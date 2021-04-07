import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// è un Singleton
public class ActionTokenContainer {
    
    private List<ActionToken> actionTokens;

    public ActionTokenContainer() {
        this.actionTokens = new ArrayList<>();
        this.actionTokens.add(new MoveActionToken());
        this.actionTokens.add(new MoveAndShuffleActionToken());
        this.actionTokens.add(new DiscardActionToken(CardColor.Amethyst));
        this.actionTokens.add(new DiscardActionToken(CardColor.Emerald));
        this.actionTokens.add(new DiscardActionToken(CardColor.Sapphire));
        this.actionTokens.add(new DiscardActionToken(CardColor.Gold));
        this.shuffle();
    }

    public List<ActionToken> getActionTokens(){
        return  actionTokens; //da cambiare
    }

    public void shuffle(){
        Collections.shuffle(actionTokens);
    }
}
