package it.polimi.ingsw.project.model;

import java.util.List;

import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.view.View;

public class PlayerMove {
    private final MoveType moveType;
    private final Player player;
    private final List<Resource> resources; //è generico, non sto specificando da dove prendo le risorse
    private final View view;

    public PlayerMove(Player player, MoveType moveType, List<Resource> resources, View view) {
        this.player = player;
        this.moveType = moveType;
        this.resources = resources;
        this.view = view;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public Player getPlayer() {
        return player;
    }

    public View getView() {
        return view;
    }
}
