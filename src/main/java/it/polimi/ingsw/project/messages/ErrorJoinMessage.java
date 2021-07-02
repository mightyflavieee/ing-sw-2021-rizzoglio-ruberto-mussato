package it.polimi.ingsw.project.messages;


import it.polimi.ingsw.project.client.Client;

public class ErrorJoinMessage extends ErrorMessage implements ResponseMessage {

    /**
     * @param errorMessage it is shown to the player when you try to join an already started game
     */
    public ErrorJoinMessage(String errorMessage) {
        setErrorMessage(errorMessage);
    }

    /**
     * it calls the function to ask to rebuild the game in the client
     * @param client it is passed to call the method to recreate the game
     */
    @Override
    public void action(Client client) {
        client.reBuildGame(super.messageError);
    }

}
