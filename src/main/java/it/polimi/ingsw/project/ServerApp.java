package it.polimi.ingsw.project;

import it.polimi.ingsw.project.server.Server;

import java.io.IOException;

public class ServerApp {
    /**
     * it creates the server
     * @param args the args are not needed in this case
     */
    public static void main( String[] args ) {
        Server server;
        System.out.println("ServerAPP started");
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
