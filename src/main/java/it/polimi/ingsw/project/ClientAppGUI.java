package it.polimi.ingsw.project;

import it.polimi.ingsw.project.client.ClientGUI;

import java.io.IOException;

public class ClientAppGUI {
    public static void main(String[] args) {
        ClientGUI client = new ClientGUI("127.0.0.1", 12345);
        try {
            client.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
