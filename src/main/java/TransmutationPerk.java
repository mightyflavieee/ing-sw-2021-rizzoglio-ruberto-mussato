public class TransmutationPerk extends Perk{
  public TransmutationPerk(Resource resource, Board board) {
    super(resource, board);
  }

  @Override
  public void usePerk(Resource resource) {
    super.usePerk(this.resource);
  }

  public Resource getResource() {
    return super.getResource();
  }

  
}
