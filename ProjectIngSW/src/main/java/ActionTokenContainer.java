import java.util.Collections;
import java.util.List;

// è un Singleton
public class ActionTokenContainer {
    
    private List<ActionToken> actionTokens;
    
    public List<ActionToken> getActionTokens(){
        return  actionTokens; //da cambiare
    }

    public void shuffle(){
        Collections.shuffle(actionTokens);
    }
}
