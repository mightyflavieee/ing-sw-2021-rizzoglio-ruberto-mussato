package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.playermove.TakeMarketResourcesMove;
import it.polimi.ingsw.project.model.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class TakeMarketResourceBuilder {
    private boolean hasRedMarble, isHandClear, isMarketClear;
    private final List<Resource> discardedResources;
    private Warehouse warehouse;
    private Market market;
    public TakeMarketResourceBuilder() {
        this.hasRedMarble = false;
        this.discardedResources = new ArrayList<>();
        this.isHandClear = false;
        this.isMarketClear = false;
    }
    public void addResourcesToDiscard(List<Resource> resourcesToAdd){
        this.discardedResources.addAll(resourcesToAdd);
    }

    public void setHasRedMarble(boolean hasRedMarble) {
        this.hasRedMarble = hasRedMarble;
    }

    public boolean setHandClear(){
        if(this.isMarketClear){
            this.isHandClear = true;
        }
        return this.isHandClear;
    }
    public void setMarketClear(Boolean value){
        this.isMarketClear = value;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public TakeMarketResourcesMove getMove(){
        return new TakeMarketResourcesMove(warehouse,discardedResources,market,hasRedMarble);
    }
    public void reset(){
        this.discardedResources.clear();
        this.isMarketClear = false;
        this.isHandClear = false;
    }
}
