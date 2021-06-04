package it.polimi.ingsw.project.client.gui;

import it.polimi.ingsw.project.model.playermove.ProductionType;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class ProductionMoveHandler {
    private String devCardID;
    private String leaderCardID;
    private SelectResourcesHandler selectResourcesHandler;
    private ProductionType productionType;
    private List<ResourceType> boardOrPerkManufacturedResource;

    public String getDevCardID() {
        return devCardID;
    }

    public String getLeaderCardID() {
        return leaderCardID;
    }

    public SelectResourcesHandler getSelectResourcesHandler() {
        return selectResourcesHandler;
    }

    public ProductionType getProductionType() {
        return productionType;
    }

    public List<ResourceType> getBoardOrPerkManufacturedResource() {
        return boardOrPerkManufacturedResource;
    }

    public void setDevCardID(String devCardID) {
        this.devCardID = devCardID;
    }

    public void setLeaderCardID(String leaderCardID) {
        this.leaderCardID = leaderCardID;
    }

    public void setSelectResourcesHandler(SelectResourcesHandler selectResourcesHandler) {
        this.selectResourcesHandler = selectResourcesHandler;
    }

    public void setProductionType(ProductionType productionType) {
        this.productionType = productionType;
    }

    public void setBoardOrPerkManufacturedResource(ResourceType resourceType) {
        if (this.boardOrPerkManufacturedResource == null) {
            this.boardOrPerkManufacturedResource = new ArrayList<>();
            this.boardOrPerkManufacturedResource.add(resourceType);
        } else {
            this.boardOrPerkManufacturedResource.add(resourceType);
        }
    }
}
