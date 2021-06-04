package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.playermove.BuyDevCardMove;
import it.polimi.ingsw.project.model.playermove.Move;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.HashMap;
import java.util.Map;

public class BuyDevCardMoveHandler {
    private DevelopmentCard developmentCard;
    private SelectResourcesHandler selectResourcesHandler;
    private DevCardPosition position;

    public DevelopmentCard getDevelopmentCard() {
        return this.developmentCard;
    }

    public SelectResourcesHandler getSelectResourcesHandler() { return this.selectResourcesHandler; }

    public DevCardPosition getPosition() { return this.position; }

    public void setDevelopmentCard(DevelopmentCard developmentCard) { this.developmentCard = developmentCard; }

    public void setSelectResourcesHandler(SelectResourcesHandler selectResourcesHandler) { this.selectResourcesHandler = selectResourcesHandler; }

    public void setPosition(DevCardPosition position) { this.position = position; }

    public Move getMove() {
        return new BuyDevCardMove(this.developmentCard.getId(), this.position,
                this.selectResourcesHandler.getResourcesFromWarehouse(), this.selectResourcesHandler.getResourcesFromChest());
    }

    public void reset() {
        this.developmentCard = null;
        this.selectResourcesHandler = null;
        this.position = null;
    }
}
