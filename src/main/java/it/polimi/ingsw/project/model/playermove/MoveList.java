package it.polimi.ingsw.project.model.playermove;

import java.util.List;

public class MoveList {
   private List<Move> moveList;
   public int getSize(){
       return moveList.size();
   }
   public Move get(int i){
       return moveList.get(i);
   }
}
