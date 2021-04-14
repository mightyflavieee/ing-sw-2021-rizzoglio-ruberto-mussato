package it.polimi.ingsw.project;
import it.polimi.ingsw.project.client.Client;
import java.io.IOException;

public class ClientApp {
    public static void main(String[] args){
        Client client = new Client("127.0.0.1", 12345); //to change
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
