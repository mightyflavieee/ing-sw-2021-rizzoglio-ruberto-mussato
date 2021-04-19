package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.view.View;

import java.util.Collections;
import java.util.List;

public class TakeMarketResourcesMove extends Move{
    private int axis;
    private List<Resource> discardedResources;
    public TakeMarketResourcesMove(int axis, List<Resource> discardedResources) {
        this.axis = axis;
        Collections.copy(this.discardedResources, discardedResources);
    }
    @Override
    public boolean isFeasibleMove(Match match){
        //TODO
        return false;
    }
    @Override
    public void performMove(Match match){
        //TODO
    }
    @Override
    public String toString(){
        //TODO
        return new String("Generic Move");
    }
}
