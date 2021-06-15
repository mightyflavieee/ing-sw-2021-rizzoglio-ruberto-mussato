package it.polimi.ingsw.project.model.actionTokens;

import it.polimi.ingsw.project.model.Match;

public interface ActionToken  {
    void Action();

    void addObserverBasedOnType(Match match, ActionTokenContainer actionTokenContainer);
}
