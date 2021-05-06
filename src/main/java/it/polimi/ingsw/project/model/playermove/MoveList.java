package it.polimi.ingsw.project.model.playermove;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MoveList implements Serializable {
   private List<Move> moveList;

    public MoveList() {
        this.moveList = new ArrayList<>();
    }

    public MoveList(List<Move> moveList) {
        this.moveList = moveList;
    }

    public int getSize(){
       return moveList.size();
   }
    public Move get(int i){
       return moveList.get(i);
   }
    public void add(Move move){
        this.moveList.add(move);
    }
}
