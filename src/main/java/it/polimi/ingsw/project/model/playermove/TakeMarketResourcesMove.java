package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.resource.Resource;

import java.util.List;

public class TakeMarketResourcesMove extends Move {

    private final Warehouse warehouse;
    private final List<Resource> discardedResources;
    private final Market market;
    private final Boolean hasRedMarble;

    /**
     * constructs the TakeMarketResourcesMove
     * @param warehouse current warehouse sent by the player
     * @param discardedResources list of the discarded resources chosen by the player
     * @param market current market sent by the player
     * @param hasRedMarble true if the player got the red Marble from the market
     */
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

    /**
     * it checks the correctness of the move
     * @param match it is passed by the controller to check on it
     * @return true if all checks are passed, false if not
     */
    @Override
    public boolean isFeasibleMove(Match match) {
        return match.isFeasibleTakeMarketResourcesMove(
                warehouse
        );
    }

    /**
     * it exec the TakeMarketResourcesMove on the match passed by the controller
     * @param match it is passed by the controller to execute the move on it
     */
    @Override
    public void performMove(Match match) {
        match.performTakeMarketResourceMove(warehouse, discardedResources, market, hasRedMarble);
    }

    @Override
    public String toString() {
        return "Take Market Resources Move";
    }
}
