package it.polimi.ingsw.project;
import it.polimi.ingsw.project.client.ClientCLI;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args){
        ClientCLI client = new ClientCLI("127.0.0.1", 12345);
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
