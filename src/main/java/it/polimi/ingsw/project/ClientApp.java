package it.polimi.ingsw.project;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.client.ClientCLI;
import it.polimi.ingsw.project.client.ClientGUI;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        Client client;
        String ip = "127.0.0.1";
         int port = 12345;
        String input = "";
        for (int i = 0; i < args.length; i++) {
            input = input + args[i];
        }

        if (input.equals("cli")) {
            client = new ClientCLI(ip, port);
        } else {
            client = new ClientGUI(ip, port);
        }
        try {
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
