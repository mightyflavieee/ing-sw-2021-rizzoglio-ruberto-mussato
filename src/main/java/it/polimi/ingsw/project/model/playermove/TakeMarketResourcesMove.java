package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.resource.Resource;

import java.util.Collections;
import java.util.List;

public class TakeMarketResourcesMove extends Move {

    private final Warehouse warehouse;
    private final List<Resource> discardedResources;
    private final Market market;
    private final Boolean hasRedMarble;

    public TakeMarketResourcesMove(
            Warehouse warehouse,
            List<Resource> discardedResources,
            Market market,
            Boolean hasRedMarble
    ) {
        this.warehouse = warehouse;
        this.discardedResources = discardedResources;
        this.market = market;
        this.hasRedMarble = hasRedMarble;
    }

    @Override
    public boolean isFeasibleMove(Match match) {
        return match.isFeasibleTakeMarketResourcesMove(
                warehouse,
                discardedResources,
                market
        );
    }

    @Override
    public void performMove(Match match) {
        match.performTakeMarketResourceMove(warehouse, discardedResources, market, hasRedMarble);
    }

    @Override
    public String toString() {
        //TODO
        return new String("Take Market Resources Move");
    }
}
