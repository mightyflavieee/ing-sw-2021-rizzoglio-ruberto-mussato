package it.polimi.ingsw.project.server;

public class PlayerConnection {
  private String name;
  private ClientConnection connection;

  public PlayerConnection(String name, ClientConnection connection) {
    this.name = name;
    this.connection = connection;
  }

  public String getName() {
    return name;
  }

  public ClientConnection getConnection() {
    return connection;
  }
}
