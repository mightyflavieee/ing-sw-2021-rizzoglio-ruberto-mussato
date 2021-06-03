package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
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
}
