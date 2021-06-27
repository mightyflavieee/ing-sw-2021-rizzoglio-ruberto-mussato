package it.polimi.ingsw.project;

import it.polimi.ingsw.project.client.Client;
import it.polimi.ingsw.project.client.ClientCLI;
import it.polimi.ingsw.project.client.ClientGUI;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        Client client;
        String ip = "4.tcp.ngrok.io";
         int port = 10665;
        StringBuilder input = new StringBuilder();
        for (String arg : args) {
            input.append(arg);
        }

        if (input.toString().equals("cli")) {
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
