package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.playermove.*;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ClientCLI {

    private String ip;
    private int port;
    private boolean active = true;
    private Match match;
    private Scanner stdin;
    private String myNickname; //da inizializzare

    public ClientCLI(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.match = null;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Optional<Match> getMatch() {
        return Optional.ofNullable(match);
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if (inputObject instanceof String) {
                            System.out.println((String) inputObject);
                        } else if (inputObject instanceof Match) {
                            setMatch((Match) inputObject);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncCli( final ObjectOutputStream socketOut) {//sends to server and shows the match
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        if(getMatch().isEmpty()) {
                            String inputLine = stdin.nextLine();
                            socketOut.writeObject(inputLine);
                        }
                        else {
                            Move move = handleTurn();
                            if(move!=null) {
                                socketOut.writeObject(move);
                            }
                        }
                        socketOut.flush();
                    }
                } catch (Exception e) {
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Move handleTurn(){
        //quando do come comando 0 entro SEMPRE in una funzione che mi permette di visualizzare le varie informazioni

        switch (this.match.getTurnPhase(myNickname)) {
            case WaitPhase:
                viewer();
                break;
            case InitialPhase:
            case EndPhase:
                return handleLeaderAction();
            case MainPhase:
                return handleMainPhase();

        }

        return  null;
    }

    private Move handleLeaderAction(){
        //quando do come comando 0 entro SEMPRE in una funzione che mi permette di visualizzare le varie informazioni

        while(true) {
            System.out.println("Do you want to perform a Leader Card Action?\n" +
                    "0 - see informations" +
                    "1 - Discard Leader Card\n" +
                    "2 - Play Leader Card\n" +
                    "3 - no\n");
            String answer = stdin.nextLine();
            switch (answer) {
                case "0":
                    viewer();
                case "1":
                    this.match.getLeaderCards(this.myNickname);
                    System.out.println("Give the LeaderCard id that you want to discard");
                    return new DiscardLeaderCardMove(stdin.nextLine());
                case "2":
                case "3":
                    System.out.println("Are you sure? [y/n]");
                    if(stdin.nextLine().equals("y")) {
                        return new NoMove();
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private Move handleMainPhase() {
        //quando do come comando 0 entro SEMPRE in una funzione che mi permette di visualizzare le varie informazioni
        Move playerMove = null;
        do {
            System.out.println("What do you want to do?\n" +
                    "0 - See informations" +
                    "1 - Take Resources from Market\n" +
                    "2 - Buy one Development Card\n" +
                    "3 - Activate the Production");
            String answer = stdin.nextLine();
            switch (answer) {
                case "0":
                    viewer();
                case "1":
                    playerMove = handleTakeMarketResourcesMove();
                    break;
                case "2":
                    playerMove = constructBuyDevCardMove();
                    break;
                case "3":
                default:
                    break;
            }
        } while (playerMove == null);
        return playerMove;
    }

    // constructs the BuyDevCardMove according to the player choices
    private Move constructBuyDevCardMove() {
        Move playerMove = null;
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        boolean skipElimination = false;
        List<DevelopmentCard> availableDevCards = this.match.getCardContainer().getAvailableDevCards();
        String devCardToBuyID = showAndSelectDevCardToBuy(availableDevCards);
        if (devCardToBuyID != null) {
            while (true) {
                System.out.println("Do you want to eliminate resources from the Warehouse? (y/n): ");
                String answer = this.stdin.nextLine();
                if (answer.equals("y")) {
                    resourcesToEliminateWarehouse = selectResourcesToEliminate(devCardToBuyID, "Warehouse");
                    break;
                } else {
                    if (answer.equals("n")) {
                        skipElimination = true;
                        break;
                    } else {
                        System.out.println("Choose a correct option.\n");
                    }
                }
            }
            if (!resourcesToEliminateWarehouse.isEmpty() || skipElimination) {
                skipElimination = false;
                while (true) {
                    System.out.println("Do you want to eliminate resources from the Chest? (y/n): ");
                    String answer = this.stdin.nextLine();
                    if (answer.equals("y")) {
                        resourcesToEliminateChest = selectResourcesToEliminate(devCardToBuyID, "Chest");
                        break;
                    } else {
                        if (answer.equals("n")) {
                            skipElimination = true;
                            break;
                        } else {
                            System.out.println("Choose a correct option.\n");
                        }
                    }
                }
                if (!resourcesToEliminateChest.isEmpty() || skipElimination) {
                    if (resourcesToEliminateWarehouse.isEmpty() && resourcesToEliminateChest.isEmpty()) {
                        System.out.println("You did not select any resource to be eliminated. Move aborted.\n");
                    } else {
                        DevCardPosition position = selectPositionForDevCard(devCardToBuyID);
                        if (position != null) {
                            playerMove = new BuyDevCardMove(devCardToBuyID, position, resourcesToEliminateWarehouse, resourcesToEliminateChest);
                        }
                    }
                }
            }
        }
        return playerMove;
    }

    // provides the id of the DevelopmentCard the player wants to buy. Returns null if the player
    // wants to go back
    private String showAndSelectDevCardToBuy(List<DevelopmentCard> availableDevCards) {
        boolean isCardPresent = false;
        String answer = null;
        System.out.println("Development Cards available for purchase:\n");
        for (DevelopmentCard devCard : availableDevCards) {
            System.out.println(devCard.toString());
        }
        while (!isCardPresent) {
            System.out.println("Which Development Card do you want to buy? (Provide the correct ID or type " +
                    "'back' to go back): ");
            answer = stdin.nextLine();
            if (!answer.equals("back")) {
                for (DevelopmentCard devCard : availableDevCards) {
                    if (answer.equals(devCard.getId())) {
                        isCardPresent = true;
                        break;
                    }
                }
                if (!isCardPresent) {
                    System.out.println("A Development Card with that ID is not available. Please provide a correct ID " +
                            "or go back.");
                }
            } else {
                answer = null;
                break;
            }
        }
        return answer;
    }

    // helper for the selectResourcesToEliminate() function, asks the player to select how many
    // of each resource to eliminate
    private void selectResourcesToEliminateHelper(
            Map<ResourceType, Integer> resourcesToEliminate,
            ResourceType type) {
        String stringType = null;
        if (type.equals(ResourceType.Coin)) {
            stringType = "Coin";
        } else {
            if (type.equals(ResourceType.Servant)) {
                stringType = "Servant";
            } else {
                if (type.equals(ResourceType.Shield)) {
                    stringType = "Shield";
                } else {
                    stringType = "Stone";
                }
            }
        }
        while (true) {
            System.out.println("How many " + stringType + "s do you want to eliminate? (insert number): ");
            try {
                int numResourcesToEliminate = Integer.parseInt(this.stdin.nextLine());
                resourcesToEliminate.put(type, numResourcesToEliminate);
                break;
            } catch (Exception e) {
                System.out.println("Insert an answer with the correct format (number).");
            }
        }
    }

    // provides the resources to eliminate from the Warehouse or the Chest for the purchase of the
    // DevelopmentCard the player wants to buy. Returns null if the player wants to go back.
    private Map<ResourceType, Integer> selectResourcesToEliminate(String devCardToBuyID, String location) {
        Map<ResourceType, Integer> resourcesToEliminate = new HashMap<>();
        String answer = null;
        boolean goBack = false;
        boolean isDone = false;
        do {
            System.out.println("Select the resource type to eliminate from the " + location + " :" +
                    "0 - Go Back;\n" +
                    "1 - Coin;\n" +
                    "2 - Servant;\n" +
                    "3 - Shield;\n" +
                    "4 - Stone;\n" +
                    "Enter here your answer: ");
            answer = stdin.nextLine();
            switch (answer) {
                case "0":
                    goBack = true;
                    break;
                case "1":
                    selectResourcesToEliminateHelper(resourcesToEliminate, ResourceType.Coin);
                    break;
                case "2":
                    selectResourcesToEliminateHelper(resourcesToEliminate, ResourceType.Servant);
                    break;
                case "3":
                    selectResourcesToEliminateHelper(resourcesToEliminate, ResourceType.Shield);
                    break;
                case "4":
                    selectResourcesToEliminateHelper(resourcesToEliminate, ResourceType.Stone);
                    break;
                default:
                    System.out.println("Choose a correct option.");
            }
            while (true) {
                System.out.println("Do you want to keep choosing or do you want to go to the" +
                        " next step? (Press 1 to keep choosing and 2 to go forward): ");
                answer = this.stdin.nextLine();
                if (answer.equals("1")) {
                    break;
                } else {
                    if (answer.equals("2")) {
                        isDone = true;
                        break;
                    } else {
                        System.out.println("Choose a correct option.");
                    }
                }
            }
        } while (!isDone && !goBack);
        if (goBack) {
            resourcesToEliminate = null;
        }
        return resourcesToEliminate;
    }

    // provides the position on the MapTray of the DevelopmentCard once it is bought. Returns null
    // if the player wants to go back.
    private DevCardPosition selectPositionForDevCard(String devCardToBuyID) {
        boolean goBack = false;
        DevelopmentCard devCardToBuy = this.match.getCardContainer().fetchCard(devCardToBuyID);
        DevCardPosition chosenPosition = null;
        String answer = null;
        do {
            System.out.println("Choose an option:\n" +
                    "0 - Go Back\n" +
                    "1 - Left;\n" +
                    "2 - Center;\n" +
                    "3 - Right.\n" +
                    "Enter here your answer: ");
            answer = this.stdin.nextLine();
            switch (answer) {
                case "0":
                    goBack = true;
                    break;
                case "1":
                    chosenPosition = DevCardPosition.Left;
                    break;
                case "2":
                    chosenPosition = DevCardPosition.Center;
                    break;
                case "3":
                    chosenPosition = DevCardPosition.Right;
                    break;
                default:
                    System.out.println("Choose a correct option.");
            }
            // verifies that the player can put the DevelopmentCard in the position he/she indicated
            if (!goBack) {
                int lastPosition = this.match.getBoardByPlayerNickname(this.myNickname).getMapTray().get(chosenPosition).size();
                DevelopmentCard devCardInLastPosition = this.match.getBoardByPlayerNickname(this.myNickname).getMapTray().get(chosenPosition).get(lastPosition);
                if (devCardInLastPosition.getLevel().compareTo(devCardToBuy.getLevel()) > 0) {
                    chosenPosition = null;
                    System.out.println("The level of the upper Development Card present in the section you selected of the " +
                            "Map Tray is higher than the one of the Card you want to buy. Please select another position " +
                            "or go back.");
                }
            }
        } while (chosenPosition == null || !goBack);
        return chosenPosition;
    }

    public void viewer() {
        //todo
        System.out.println("0 - Go Back\n" +
                "1 - show informations about the others players\n" +
                "2 - show your Points\n" +
                "3 - show your Marker Position\n" +
                "4 - show your Leader Cards\n" +
                "5 - show your Development Cards\n" +
                "6 - show the Market");
        String answer = stdin.nextLine();
        switch (answer) {
            case "0":
                break;
            case "1":
                //todo
            case "2":
                System.out.println("Your points are: " + this.match.getVictoryPoints(myNickname));
                break;
            case "3":
                System.out.println("Your marker position is: " + this.match.getMarkerPosition(myNickname) +"/24");
                break;
            case "4":
                System.out.println("Your Leader Cards are: " + this.match.getLeaderCards(myNickname) );
                break;
            case "5":
            case "6":
                System.out.println(this.match.getMarket());
                break;
            default:
                return;
        }
        return;

    }

    private TakeMarketResourcesMove handleTakeMarketResourcesMove(){
        Market market = this.match.getMarket();
        System.out.println(market);
        //todo richieste di axis e position
        int axis = 0, position = 0;
        //todo transmutation perk
        ResourceType transmutationPerk = null;
        List<Resource> resourceList = market.insertMarble(axis,position,transmutationPerk);
        Boolean hasRedMarble = false;
        for(int i = 0; i < resourceList.size(); i++){
            if(resourceList.get(i).getType() == ResourceType.Faith){
                hasRedMarble = true;
                resourceList.remove(i);
                break;
            }
        }
        Warehouse warehouse = match.getWarehouse(myNickname);
        Map<ResourceType, Integer> resourcesInHand = warehouse.listToMapResources(resourceList);
        while(resourcesInHand.size()>0){
            //todo mostrare shelves
            //todo chiedere quali risorse vuole inserire
            warehouse.insertInShelves(null, null);
            warehouse.insertInExtraDeposit(null);
        }
        return new TakeMarketResourcesMove(warehouse,null,market,hasRedMarble);
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        stdin = new Scanner(System.in);
        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncCli(socketOut);
            t0.join();
            t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }
}
