

public class Player {
  private Board board;
  private boolean isConnected;
  private String nickname;
  private int victoryPoints;

  public Board getBoard(){
    return board;
  }

  public boolean getStatus(){
    return isConnected;
  }

  public String getNickname(){
    return nickname;
  }

  public int getVictoryPoints(){
    return victoryPoints;
  }
}
