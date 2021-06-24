package it.polimi.ingsw.project;
import it.polimi.ingsw.project.client.ClientCLI;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        // if (args.equals("cli")) {
        // client = new ClientCLI("127.0.0.1", 12345);
        // }
        // } else if (args.equals("gui")) {
        // client = new ClientGUI("127.0.0.1", 12345);
        // }
        ClientCLI client = new ClientCLI("0.tcp.ngrok.io", 14341);
        try {
            client.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
