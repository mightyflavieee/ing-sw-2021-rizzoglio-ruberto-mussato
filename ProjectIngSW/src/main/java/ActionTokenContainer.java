import java.util.List;

// è un Singleton
public class ActionTokenContainer {
    private static ActionTokenContainer instance;
    private List<ActionToken> actionTokens;
    public static ActionTokenContainer instance(){
        if (instance == null){
            instance = new ActionTokenContainer();
        }
        return instance;
    }
    public List<ActionToken> getActionTokens(){
        return  actionTokens; //da cambiare
    }
    public void attach(Observer observer){}
    public void detach(Observer observer){}
    public void notifyUpdate(){};
}
