import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProductionPerk extends Perk{

  @Override
  public void usePerk(Resource resource) {
    System.out.println("Which Resource do you want?\n1. Coin;\n2. Shield;\n3. Servant;\n4. Stone.");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String chosenResource;
    try {
      chosenResource = reader.readLine();
      switch (Integer.parseInt(chosenResource)) {
        case 1:
          // tolgo una resource (argomento) al magazzino
          // aggiungo un Coin al magazzino e faccio moveForward() sulla FaithMap
          break;
        case 2:
          // tolgo una resource (argomento) al magazzino
          // aggiungo un Shield al magazzino e faccio moveForward() sulla FaithMap
          break;
        case 3:
          // tolgo una resource (argomento) al magazzino
          // aggiungo un Servant al magazzino e faccio moveForward() sulla FaithMap
          break;
        case 4:
          // tolgo una resource (argomento) al magazzino
          // aggiungo un Stone al magazzino e faccio moveForward() sulla FaithMap
          break;
        default:
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Cannot read commad!");
    }
  }

  @Override
  public Resource getResource() {
    // TODO Auto-generated method stub
    return super.getResource();
  }
}
