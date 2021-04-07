public abstract class Perk {
  Resource resource;
  Board board;

  public Perk(Resource resource, Board board) {
    this.resource = resource;
    this.board = board;
  }

  public Resource getResource() {
    return resource;
  }

  public void usePerk(Resource resource){}
}
