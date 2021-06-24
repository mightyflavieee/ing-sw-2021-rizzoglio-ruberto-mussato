package it.polimi.ingsw.project.model.playermove;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.Map;

public class BuyDevCardMove extends Move{
    private final String devCardID;
    private final DevCardPosition position;
    private final Map<ResourceType, Integer> resourcesToEliminateWarehouse;
    private final Map<ResourceType, Integer> resourcesToEliminateExtraDeposit;
    private final Map<ResourceType, Integer> resourcesToEliminateChest;

    public BuyDevCardMove(String devCardID,
                          DevCardPosition position,
                          Map<ResourceType, Integer> resourcesToEliminateWarehouse,
                          Map<ResourceType, Integer> resourcesToEliminateExtraDeposit,
                          Map<ResourceType, Integer> resourcesToEliminateChest) {
        this.devCardID = devCardID;
        this.position = position;
        this.resourcesToEliminateWarehouse = resourcesToEliminateWarehouse;
        this.resourcesToEliminateExtraDeposit = resourcesToEliminateExtraDeposit;
        this.resourcesToEliminateChest = resourcesToEliminateChest;
    }

    @Override
    public boolean isFeasibleMove(Match match){
        return match.isFeasibleBuyDevCardMove(this.devCardID,
                this.resourcesToEliminateWarehouse,
                this.resourcesToEliminateExtraDeposit,
                this.resourcesToEliminateChest,
                this.position);
    }

    @Override
    public void performMove(Match match) {
        // performs move
        match.performBuyDevCardMove(this.devCardID,
                this.resourcesToEliminateWarehouse,
                this.resourcesToEliminateExtraDeposit,
                this.resourcesToEliminateChest,
                this.position);
        // adds DevelopmentCard victory points to player
        int victoryPointsToAdd = match.getCurrentPlayer().getBoard().fetchDevCardById(this.devCardID).getPoints();
        match.getCurrentPlayer().addVictoryPoints(victoryPointsToAdd);
        // checks if the player has 7 DevelopmentCards, if yes the last turn is player or the game ends
        if (match.getCurrentPlayer().hasSevenCards()) {
            match.setIsLastTurn(true);
        }
    }

    @Override
    public String toString(){
        return "Buy Dev Card Move, Dev Card ID:" + this.devCardID;
    }
}
