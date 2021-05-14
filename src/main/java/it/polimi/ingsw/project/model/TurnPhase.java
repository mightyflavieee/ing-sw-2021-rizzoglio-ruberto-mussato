package it.polimi.ingsw.project.model;

public enum TurnPhase {
    WaitPhase, InitialPhase, MainPhase, EndPhase;

    public TurnPhase next(){
        switch (this) {
            case WaitPhase:
                return InitialPhase;
            case InitialPhase:
                return MainPhase;
            case MainPhase:
                return EndPhase;
            case EndPhase:
                return WaitPhase;
        }
        return this;
    }
}
