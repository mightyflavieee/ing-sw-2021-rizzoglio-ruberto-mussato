package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.view.View;

import java.util.Collections;
import java.util.List;

public class DiscardResourcesMove extends Move{
    private List<Resource> discardedResources;
    public DiscardResourcesMove(List<Resource> discardedResources) {
        Collections.copy(this.discardedResources,discardedResources);
    }
    @Override
    public boolean isFeasibleMove(Match match){
        return true;
    }
    @Override
    public void performMove(Match match){
        match.performDiscardResourcesMove();
    }
    @Override
    public String toString(){
        //TODO
        return new String("Generic Move");
    }
}
