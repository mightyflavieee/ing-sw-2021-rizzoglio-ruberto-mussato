import java.util.List;

// è un Singleton
public class ActionTokenContainer {
    
    private List<ActionToken> actionTokens;
    
    public List<ActionToken> getActionTokens(){
        return  actionTokens; //da cambiare
    }
    public void attach(Observer observer){}
    public void detach(Observer observer){}
    public void notifyUpdate(){}
    public void shuffle(){
        //da definire
    }
}
