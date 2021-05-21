package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.playermove.*;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Pair;

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

    private Move handleLeaderAction() {
        //quando do come comando 0 entro SEMPRE in una funzione che mi permette di visualizzare le varie informazioni

        while(true) {
            System.out.println("Do you want to perform a Leader Card Action?\n" +
                    "0 - See information;" +
                    "1 - Discard Leader Card;\n" +
                    "2 - Activate Leader Card;\n" +
                    "3 - No.\n" +
                    "> ");
            String answer = stdin.nextLine();
            switch (answer) {
                case "0":
                    viewer();
                case "1":
                    this.match.getLeaderCards(this.myNickname);
                    System.out.println("Give the LeaderCard id that you want to discard");
                    return new DiscardLeaderCardMove(stdin.nextLine());
                case "2":
                    return constructActivateLeaderCardMove();
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
        boolean isInputError = false;
        do {
            System.out.println("What do you want to do?\n" +
                    "0 - See informations" +
                    "1 - Take Resources from Market\n" +
                    "2 - Buy one Development Card\n" +
                    "3 - Activate Production.\n" +
                    "> ");
            do {
                String answer = stdin.nextLine();
                switch (answer) {
                    case "0":
                        viewer();
                        break;
                    case "1":
                        isInputError = false;
                        playerMove = handleTakeMarketResourcesMove();
                        break;
                    case "2":
                        isInputError = false;
                        playerMove = constructBuyDevCardMove();
                        break;
                    case "3":
                        isInputError = false;
                        playerMove = constructProductionMove();
                        break;
                    default:
                        System.out.println("Please provide a correct number.\n" + "> ");
                        isInputError = true;
                        break;
                }
            } while (isInputError);
        } while (playerMove == null);
        return playerMove;
    }

    // constructs the ProductionMove according to the player choices
    private Move constructActivateLeaderCardMove() {
        Move playerMove = null;
        boolean isCorrectID = false;
        boolean isMovePossible = false;
        String leaderCardID = null;
        if (!this.match.getCurrentPlayer().getBoard().getLeaderCards().isEmpty()) {
            this.match.getLeaderCards(this.myNickname);
            do {
                System.out.println("Provide the ID of the LeaderCard you want to activate: (Type 'quit' to go back)\n" + "> ");
                String answer = this.stdin.nextLine();
                for (LeaderCard leaderCard : this.match.getCurrentPlayer().getBoard().getLeaderCards()) {
                    if (answer.equals(leaderCard.getId())) {
                        isCorrectID = true;
                        leaderCardID = answer;
                        break;
                    }
                }
                if (!isCorrectID) {
                    System.out.println("Provide a correct ID");
                } else {
                    if (isActivateLeaderCardMovePossible(leaderCardID)) {
                        playerMove = new ActivateLeaderCardMove(leaderCardID);
                        isMovePossible = true;
                    } else {
                        System.out.println("You cannot activate the Leader Card with ID=" + leaderCardID + " because you" +
                                " don't have the requirements for the activation! Please choose another Leader Card or go back!");
                    }
                }
            } while (!isMovePossible);
        } else {
            System.out.println("No Leader Cards present. Cannot execute move.");
        }
        return playerMove;
    }

    private boolean isActivateLeaderCardMovePossible(String leaderCardID) {
        //TODO
        return false;
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

    // constructs the ProductionMove according to the player choices
    private Move constructProductionMove() {
        Move playerMove = null;

        return playerMove;
    }

    public void viewer() {
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
                viewer(myNickname);
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
                break;
            case "6":
                System.out.println(this.match.getMarket());
                break;
            default:
                return;
        }
        return;

    }

    private void viewer(String myNickname) {
        //shows informations about other players
        System.out.println("Your opponents are : " + this.match.getOpponents(myNickname) +
                "\n tell the nickname");
        String opponent = stdin.nextLine();
        System.out.println("0 - Go Back\n" +
                "1 - show " + opponent + " Points\n" +
                "2 - show " + opponent + " Marker Position\n" +
                "3 - show " + opponent + " Leader Cards\n" +
                "4 - show " + opponent + " Development Cards\n");
        String answer = stdin.nextLine();
        switch (answer) {
            case "0":
                break;
            case "1":
                System.out.println(opponent + " points are: " + this.match.getVictoryPoints(opponent));
            case "2":
                System.out.println(opponent + " marker position is: " + this.match.getMarkerPosition(opponent) +"/24");
                break;
            case "3":
                System.out.println(opponent + " Leader Cards are: " + this.match.getLeaderCards(opponent) );
                break;
            default:
                return;
        }


    }

    private TakeMarketResourcesMove handleTakeMarketResourcesMove(){
        List <Resource> resourcesToDiscard = new ArrayList<>();
        Market market = this.match.getMarket();
        System.out.println(market);
        String answer;
        int axis, position;
        do {
            System.out.println("do you want to insert the marble horizontally or vertically?\n" +
                    "0 - vertical\n" +
                    "1 - horizontal\n" +
                    "2 - go back and to an other move");
            axis = Integer.parseInt(stdin.nextLine());
        }while (axis<0 || axis > 2);
        if(axis == 2 ){
            return null;
        }
        if(axis == 0){
            do {
                System.out.println("Which column?\n" +
                        "from 0 to 3, from left to right");
                position = Integer.parseInt(stdin.nextLine());
            }while(position>3 || position<0);
        }else{
            do {
                System.out.println("Which line?\n" +
                        "from 0 to 2, from bottom to up");
                position = Integer.parseInt(stdin.nextLine());
            }while(position>2 || position<0);
        }

        ResourceType transmutationPerk = match.getTransmutationPerk(myNickname);
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
        System.out.println(warehouse);
        System.out.println("Resources in hand :" + resourcesInHand.entrySet().stream().map(x -> x.getKey() + " " + x.getValue().toString()));
        while(resourcesInHand.size()>0){
            System.out.println("0 - show Warehouse\n" +
                    "1 - show resources in hand\n" +
                    "2 - insert resources in the shelves\n" +
                    "3 - insert resources in the extra deposit\n" +
                    "4 - discard resources\n" +
                    "5 - swap shelves");
            answer = stdin.nextLine();
            switch (answer){
                case "0":
                    System.out.println(warehouse);
                    break;
                case "1":
                    System.out.println("Resources in hand :" + resourcesInHand.entrySet().stream().map(x -> x.getKey() + " " + x.getValue().toString()));
                    break;
                case "2":
                    this.insertInShelves(warehouse,resourcesInHand);
                    break;
                case "3":
                    this.insertInExtraDeposit(warehouse,resourcesInHand);
                    break;
                case "4":
                    resourcesToDiscard.addAll(this.discardResources(warehouse,resourcesInHand)) ;
                    break;
                case "5":
                    swapShelves(warehouse);
                    break;
                default:
                    System.out.println("wrong input");
                    break;
            }
        }
        return new TakeMarketResourcesMove(warehouse,resourcesToDiscard,market,hasRedMarble);
    }
    private void insertInShelves(Warehouse warehouse,Map<ResourceType, Integer> resourcesInHand){

        System.out.println("Which Resource type do you want to put in the shelves?\n");
        Pair<ResourceType,Integer> resourceSelected = resourceSelector(resourcesInHand);
        ShelfFloor shelfFloor;
        if(resourceSelected == null){
            return;
        }
            System.out.println("1 - First floor\n" +
                    "2 - second floor\n" +
                    "3 - third floor");
            switch (stdin.nextLine()){
                case "1":
                    shelfFloor = ShelfFloor.First;
                    break;
                case "2":
                    shelfFloor = ShelfFloor.Second;
                    break;
                case "3":
                    shelfFloor = ShelfFloor.Third;
                    break;
                default:
                    System.out.println("wrong input");
                    return;
            }
            List<Resource> resourceList = new ArrayList<>();
            for(int i = 0; i < resourceSelected._2; i++){
                resourceList.add(new Resource(resourceSelected._1));
            }
            if(!warehouse.insertInShelves(shelfFloor, resourceList)){
                System.out.println("forbidden action");}
            else{
                int oldvalue = resourcesInHand.remove(resourceSelected._1);
                oldvalue = oldvalue - resourceSelected._2;
                if(oldvalue>0){
                    resourcesInHand.put(resourceSelected._1, oldvalue);
                }
            }
                return;




    }
    private Pair<ResourceType,Integer> resourceSelector(Map<ResourceType, Integer> resourcesInHand){
        //returns a map if i have the resources, otherwise null
        String resourceType;
        ResourceType type;
        int n;
        System.out.println("1 - coin\n" +
                "2 - stone\n" +
                "3 - shield\n" +
                "4 - servant");
        resourceType = stdin.nextLine();
        System.out.println("how many?");
        n = Integer.parseInt(stdin.nextLine());
        switch (resourceType){
            case "1":
                type = ResourceType.Coin;
                break;
            case "2":
                type = ResourceType.Stone;
                break;
            case "3":
                type = ResourceType.Shield;
                break;
            case "4":
                type = ResourceType.Servant;
                break;
            default:
                System.out.println("wrong input");
                return null;
        }
        if(resourcesInHand.get(type) >= n){
            return  new Pair<>(type,n);
        }
        else {
            System.out.println("Not enough resources");
            return null;
        }
    }
    public void insertInExtraDeposit(Warehouse warehouse,Map<ResourceType, Integer> resourcesInHand){
        System.out.println("Which Resource type do you want to put in the extra deposit?\n");
        Pair<ResourceType,Integer> resourceSelected = resourceSelector(resourcesInHand);
        if(resourceSelected == null){
            return;
        }
        List<Resource> resourceList = new ArrayList<>();
        for(int i = 0; i < resourceSelected._2; i++){
            resourceList.add(new Resource(resourceSelected._1));
        }
        if(!warehouse.insertInExtraDeposit(resourceList)){
            System.out.println("forbidden action");
        }
        else{
            int oldvalue = resourcesInHand.remove(resourceSelected._1);
            oldvalue = oldvalue - resourceSelected._2;
            if(oldvalue>0){
                resourcesInHand.put(resourceSelected._1, oldvalue);
            }
        }
        return;

    }
    public List<Resource> discardResources(Warehouse warehouse,Map<ResourceType, Integer> resourcesInHand){
        System.out.println("Which Resource type do you want to discard?\n");
        Pair<ResourceType,Integer> resourceSelected = resourceSelector(resourcesInHand);
        if(resourceSelected == null){
            return new ArrayList<>();
        }
        int oldvalue = resourcesInHand.remove(resourceSelected._1);
        oldvalue = oldvalue - resourceSelected._2;
        if(oldvalue>0){
            resourcesInHand.put(resourceSelected._1, oldvalue);
        }
        List<Resource> resources = new ArrayList<>();
        for(int i = 0; i < resourceSelected._2; i++){
            resources.add(new Resource(resourceSelected._1));
        }
        return resources;

    }
    public void swapShelves(Warehouse warehouse){
        System.out.println("0 - show Warehouse\n" +
                "1 - swap first and second floor\n" +
                "2 - swap second and third floor\n" +
                "3 - swap third e first floor\n" +
                "4 - go back");
        switch (stdin.nextLine()){
            case "0":
                System.out.println(warehouse);
                break;
            case "1":
                if(warehouse.getShelves().get(ShelfFloor.Second).size()>1){
                    System.out.println("forbidden action");
                }else
                {
                    warehouse.swapShelves(ShelfFloor.First,ShelfFloor.Second);
                }
                break;
            case "2":
                if(warehouse.getShelves().get(ShelfFloor.Third).size()>2){
                    System.out.println("forbidden action");
                }else
                {
                    warehouse.swapShelves(ShelfFloor.Third,ShelfFloor.Second);
                }
                break;
            case "3":
                if(warehouse.getShelves().get(ShelfFloor.Third).size()>1){
                    System.out.println("forbidden action");
                }else
                {
                    warehouse.swapShelves(ShelfFloor.Third,ShelfFloor.First);
                }
                break;
            case "4":
                break;
            default:
                System.out.println("wrong input");
                break;
        }
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
