package it.polimi.ingsw.project.server;

public class PlayerConnection {
  private final String name;
  private final SocketClientConnection connection;

  public PlayerConnection(String name, SocketClientConnection connection) {
    this.name = name;
    this.connection = connection;
  }

  public String getName() {
    return name;
  }

  public SocketClientConnection getConnection() {
    return connection;
  }
}
