package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.messages.ResponseMessage;
import it.polimi.ingsw.project.model.Match;
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
import it.polimi.ingsw.project.utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class ClientCLI extends Client {

    private Scanner stdin;
    private boolean lock = true;

    public ClientCLI(String ip, int port) {
        super(ip, port);
        this.lock = true;
    }

    @Override
    public Client getInstance() {
        return this;
    }

    public synchronized void isLock() throws InterruptedException {
        if (this.lock) {
            wait();
        }
    }

    @Override
    public void setMatch(Match match) {
        this.match = match;
        unLock();
    }

    @Override
    public void setGameId(String gameId) {
        this.gameId = gameId;
        System.out.println("Your gameid is: " + gameId);
        unLock();
    }

    public synchronized void unLock() {
        this.lock = false;
        notifyAll();
    }

    public synchronized void setLock() {
        this.lock = true;
    }

    public Thread asyncReadFromSocket() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        ResponseMessage inputObject = (ResponseMessage) getSocketIn().readObject();
                        inputObject.action(getInstance());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    setActive(false);
                }
            }
        });
        t.start();
        return t;

    }

    public void showErrorMessage(String error) {
        System.out.println(error);
    }

    @Override
    public void showWaitMessageForOtherPlayers() {
        System.out.println("Wait for the other players.");
    }

    @Override
    public void chooseLeaderCards(List<LeaderCard> possibleLeaderCards) {
        final List<LeaderCard> allLeaderCard = possibleLeaderCards;
        System.out.println("Possible Leader Cards:");
        for (LeaderCard leader : possibleLeaderCards) {
            System.out.println(leader.toString());
        }
        System.out.println("Choose two ids of the leader cards you like the most:");
        for (LeaderCard leader : possibleLeaderCards) {
            System.out.println(leader.getId());
        }
        List<String> chosenIds = new ArrayList<String>();
        while (true) {
            String chosenId = this.stdin.nextLine();
            if (Utils.isIdPresent(possibleLeaderCards, chosenId)) {
                chosenIds.add(chosenId);
                if (chosenIds.size() == 2) {
                    break;
                }
                possibleLeaderCards = possibleLeaderCards.stream()
                        .filter((LeaderCard leaderCard) -> !leaderCard.getId().equals(chosenId))
                        .collect(Collectors.toList());
                System.out.println("Great! Choose another one from:");
            } else {
                System.out.println("The id you are trying to add doesn't exists. Try a different one!");
            }
            for (LeaderCard leader : possibleLeaderCards) {
                System.out.println(leader.getId());
            }
        }
        try {
            getSocketOut().writeObject(new ChooseLeaderCardMove(getNickname(), super.gameId,
                    Utils.extractSelectedLeaderCards(allLeaderCard, chosenIds)));
            getSocketOut().flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setActive(false);
        }
    }

    @Override
    public void reChooseLeaderCards(String errorMessage, List<LeaderCard> possibleLeaderCards) {
        System.out.println(errorMessage);
        chooseLeaderCards(possibleLeaderCards);
    }

    @Override
    public void reBuildGame(String errorMessage) {
        this.showErrorMessage(errorMessage);
        this.buildGame();
    }

    private void buildGame() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Hello what is your name?");
                    String tempNickName = stdin.nextLine();
                    System.out.println("Your nickname is " + tempNickName + ", do you confirm? 'yes' | 'no'");
                    String decision = stdin.nextLine();
                    if (decision.equals("yes")) {
                        setNickname(tempNickName);
                        break;
                    }
                }
                while (true) {
                    System.out.println("What do you want to 'join' or 'create' a game?");
                    String request = stdin.nextLine();
                    boolean wasValid = false;
                    if (request.equals("join")) {
                        wasValid = joinGame();
                    } else if (request.equals("create")) {
                        wasValid = createGame();
                    } else {
                        System.out.println("Request not valid!");
                    }
                    if (wasValid) {
                        break;
                    }
                }
            }
        });

        t.start();
    }

    private boolean joinGame() { // returns true if the joining request was created successfully
        System.out.println("Which game do you want to join? Type 'exit' to leave.");
        String gameId;
        gameId = stdin.nextLine();
        if (gameId.equals("exit")) {
            return false;
        }
        try {
            getSocketOut().writeObject(new JoinRequestMove(getNickname(), gameId));
            getSocketOut().flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setActive(false);
        }
        return true;

    }

    private boolean createGame() { // returns true if the creation request was created successfully
        Integer playersNumber;
        while (true) {
            System.out.println("How many people do you want in your game? (max 4) Type 'exit' to leave.");
            try {
                try {
                    String request = stdin.nextLine();
                    if (request.equals("exit")) {
                        return false;
                    }
                    playersNumber = Integer.parseInt(request);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
                if (playersNumber > 4) {
                    throw new Exception("Insert an integer number less than equal 4.");
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            getSocketOut().writeObject(new CreateRequestMove(playersNumber, getNickname()));
            getSocketOut().flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setActive(false);
        }
        return true;
    }

    public Thread asyncCli() {// sends to server and shows the match
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        isLock();
                        if (!getMatch().isEmpty()) {
                            Request move = handleTurn();
                            if (move != null) {
                                getSocketOut().writeObject(move);
                            }
                            getSocketOut().flush();
                        }
                        setLock();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Move handleTurn() {
        // quando do come comando 0 entro SEMPRE in una funzione che mi permette di
        // visualizzare le varie informazioni

        switch (getMatch().get().getTurnPhase(getNickname())) {
            case WaitPhase:
                viewer();
                break;
            case InitialPhase:
            case EndPhase:
                return handleLeaderAction();
            case MainPhase:
                return handleMainPhase();

        }

        return null;
    }

    private Move handleLeaderAction() {
        // quando do come comando 0 entro SEMPRE in una funzione che mi permette di
        // visualizzare le varie informazioni

        while (true) {
            System.out.println("Do you want to perform a Leader Card Action?\n" + "0 - See information;\n"
                    + "1 - Discard Leader Card;\n" + "2 - Activate Leader Card;\n" + "3 - No");
            String answer = stdin.nextLine();
            switch (answer) {
                case "0":
                    viewer();
                    break;
                case "1":
                    System.out.println(getMatch().get().getLeaderCardsToString(getNickname()));
                    System.out.println("Give the LeaderCard id that you want to discard");
                    return new DiscardLeaderCardMove(stdin.nextLine());
                case "2":
                    return constructActivateLeaderCardMove();
                case "3":
                    System.out.println("Are you sure? [y/n]");
                    if (stdin.nextLine().equals("y")) {
                        return new NoMove();
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private Move handleMainPhase() {
        // quando do come comando 0 entro SEMPRE in una funzione che mi permette di
        // visualizzare le varie informazioni
        Move playerMove = null;
        boolean isInputError = false;
        do {
            System.out.print("What do you want to do?\n" + "0 - See informations\n" + "1 - Take Resources from Market\n"
                    + "2 - Buy one Development Card\n" + "3 - Activate Production.\n" + "> ");
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
        if (!getMatch().get().getCurrentPlayer().getBoard().getLeaderCards().isEmpty()) {
            System.out.println(getMatch().get().getLeaderCardsToString(getNickname()));
            do {
                System.out.println(
                        "Provide the ID of the LeaderCard you want to activate: (Type 'quit' to go back)\n" + "> ");
                String answer = this.stdin.nextLine();
                for (LeaderCard leaderCard : getMatch().get().getCurrentPlayer().getBoard().getLeaderCards()) {
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
                        System.out.println("You cannot activate the Leader Card with ID=" + leaderCardID
                                + " because you"
                                + " don't have the requirements for the activation! Please choose another Leader Card or go back!\n");
                    }
                }
            } while (!isMovePossible);
        } else {
            System.out.println("No Leader Cards present. Cannot execute move.");
        }
        return playerMove;
    }

    // TODO
    private boolean isActivateLeaderCardMovePossible(String leaderCardID) {
        return false;
    }

    // constructs the BuyDevCardMove according to the player choices
    // TODO verify if the resources to eliminate chosen by the player are correct
    private Move constructBuyDevCardMove() {
        Move playerMove = null;
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        boolean skipElimination = false;
        List<DevelopmentCard> availableDevCards = getMatch().get().getCardContainer().getAvailableDevCards();
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
                            playerMove = new BuyDevCardMove(devCardToBuyID, position, resourcesToEliminateWarehouse,
                                    resourcesToEliminateChest);
                        }
                    }
                }
            }
        }
        return playerMove;
    }

    // provides the id of the DevelopmentCard the player wants to buy. Returns null
    // if the player
    // wants to go back
    private String showAndSelectDevCardToBuy(List<DevelopmentCard> availableDevCards) {
        boolean isCardPresent = false;
        String answer = null;
        System.out.println("Development Cards available for purchase:\n");
        for (DevelopmentCard devCard : availableDevCards) {
            System.out.println(devCard.toString());
        }
        while (!isCardPresent) {
            System.out.println("Which Development Card do you want to buy? (Provide the correct ID or type "
                    + "'back' to go back): ");
            answer = stdin.nextLine();
            if (!answer.equals("back")) {
                for (DevelopmentCard devCard : availableDevCards) {
                    if (answer.equals(devCard.getId())) {
                        isCardPresent = true;
                        break;
                    }
                }
                if (!isCardPresent) {
                    System.out.println("A Development Card with that ID is not available. Please provide a correct ID "
                            + "or go back.");
                }
            } else {
                answer = null;
                break;
            }
        }
        return answer;
    }

    // helper for the selectResourcesToEliminate() function, asks the player to
    // select how many
    // of each resource to eliminate
    private void selectResourcesToEliminateHelper(Map<ResourceType, Integer> resourcesToEliminate, ResourceType type) {
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

    // provides the resources to eliminate from the Warehouse or the Chest for the
    // purchase of the
    // DevelopmentCard the player wants to buy. Returns null if the player wants to
    // go back.
    private Map<ResourceType, Integer> selectResourcesToEliminate(String devCardToBuyID, String location) {
        Map<ResourceType, Integer> resourcesToEliminate = new HashMap<>();
        String answer = null;
        boolean goBack = false;
        boolean isDone = false;
        do {
            System.out.println("Select the resource type to eliminate from the " + location + " :" + "0 - Go Back;\n"
                    + "1 - Coin;\n" + "2 - Servant;\n" + "3 - Shield;\n" + "4 - Stone;\n" + "Enter here your answer: ");
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
                System.out.println("Do you want to keep choosing or do you want to go to the"
                        + " next step? (Press 1 to keep choosing and 2 to go forward): ");
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

    // provides the position on the MapTray of the DevelopmentCard once it is
    // bought. Returns null
    // if the player wants to go back.
    private DevCardPosition selectPositionForDevCard(String devCardToBuyID) {
        boolean goBack = false;
        DevelopmentCard devCardToBuy = getMatch().get().getCardContainer().fetchCard(devCardToBuyID);
        DevCardPosition chosenPosition = null;
        String answer = null;
        do {
            while (chosenPosition == null) {
                System.out.println("Choose an option:\n" + "0 - Go Back\n" + "1 - Left;\n" + "2 - Center;\n"
                        + "3 - Right.\n" + "Enter here your answer: ");
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
                        break;
                }
            }
            // verifies that the player can put the DevelopmentCard in the position he/she
            // indicated
            if (!goBack) {
                int lastPosition = getMatch().get().getBoardByPlayerNickname(getNickname()).getMapTray()
                        .get(chosenPosition).size();
                DevelopmentCard devCardInLastPosition = getMatch().get().getBoardByPlayerNickname(getNickname())
                        .getMapTray().get(chosenPosition).get(lastPosition);
                if (devCardInLastPosition.getLevel().compareTo(devCardToBuy.getLevel()) > 0) {
                    chosenPosition = null;
                    System.out.println(
                            "The level of the upper Development Card present in the section you selected of the "
                                    + "Map Tray is higher than the one of the Card you want to buy. Please select another position "
                                    + "or go back.");
                }
            }
        } while (chosenPosition == null || !goBack);
        return chosenPosition;
    }

    // constructs the ProductionMove according to the player choices
    // TODO verify if the resources to eliminate chosen by the player are correct
    private Move constructProductionMove() {
        Move playerMove = null;
        boolean goBack = false;
        ProductionType productionType = null;
        do {
            System.out.println("Which type of production do you want to activate?\n" + "0 - Go Back;\n"
                    + "1 - Board Production;\n" + "2 - Development Card Production;\n" + "3 - Leader Card Production.\n"
                    + "4 - Board and Development Card Production;\n" + "5 - Board and Leader Card Production;\n"
                    + "6 - Board, Development Card and Leader Card Production;\n"
                    + "7 - Development Card and Leader Card Production;\n" + "> ");
            String answer = this.stdin.nextLine();
            switch (answer) {
                case "0":
                    goBack = true;
                    break;
                case "1":
                    productionType = ProductionType.Board;
                    break;
                case "2":
                    productionType = ProductionType.DevCard;
                    break;
                case "3":
                    productionType = ProductionType.LeaderCard;
                    break;
                case "4":
                    productionType = ProductionType.BoardAndDevCard;
                    break;
                case "5":
                    productionType = ProductionType.BoardAndLeaderCard;
                    break;
                case "6":
                    productionType = ProductionType.BoardAndDevCardAndLeaderCard;
                    break;
                case "7":
                    productionType = ProductionType.DevCardAndLeader;
                    break;
                default:
                    System.out.println("Choose a correct option.");
                    break;
            }
        } while (productionType == null && !goBack);
        if (!goBack) {
            Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
            Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
            String devCardID = null;
            String leaderCardID = null;
            switch (productionType) {
                case Board:
                    // selects which resources to eliminate and from where
                    selectResourcesToEliminateForBoardProduction(resourcesToEliminateWarehouse,
                            resourcesToEliminateWarehouse);
                    // TODO verify resources (if present and enough to satisfy production)
                    // constructs the move
                    playerMove = new ProductionMove(null, null, resourcesToEliminateWarehouse,
                            resourcesToEliminateChest, productionType,
                            selectBoardOrPerkManufacturedResource(productionType));
                    break;
                case DevCard:
                    devCardID = getDevCardIDForProduction();
                    if (devCardID.equals("quit")) {
                        goBack = true;
                    } else {
                        resourcesToEliminateWarehouse = selectResourcesToEliminate(devCardID, "Warehouse");
                        resourcesToEliminateChest = selectResourcesToEliminate(devCardID, "Chest");
                        // TODO verify resources (if present and enough to satisfy production)
                        playerMove = new ProductionMove(devCardID, null, resourcesToEliminateWarehouse,
                                resourcesToEliminateChest, productionType, null);
                    }
                    break;
                case LeaderCard:
                case BoardAndLeaderCard:
                    leaderCardID = getLeaderCardIDForProduction();
                    if (leaderCardID.equals("quit")) {
                        goBack = true;
                    } else {
                        resourcesToEliminateWarehouse = selectResourcesToEliminate(devCardID, "Warehouse");
                        resourcesToEliminateChest = selectResourcesToEliminate(devCardID, "Chest");
                        // TODO verify resources (if present and enough to satisfy production)
                        playerMove = new ProductionMove(null, leaderCardID, resourcesToEliminateWarehouse,
                                resourcesToEliminateChest, productionType,
                                selectBoardOrPerkManufacturedResource(productionType));
                    }
                    break;
                case BoardAndDevCard:
                    devCardID = getDevCardIDForProduction();
                    if (devCardID.equals("quit")) {
                        goBack = true;
                    } else {
                        resourcesToEliminateWarehouse = selectResourcesToEliminate(devCardID, "Warehouse");
                        resourcesToEliminateChest = selectResourcesToEliminate(devCardID, "Chest");
                        // TODO verify resources (if present and enough to satisfy production)
                        playerMove = new ProductionMove(devCardID, null, resourcesToEliminateWarehouse,
                                resourcesToEliminateChest, productionType,
                                selectBoardOrPerkManufacturedResource(productionType));
                    }
                    break;
                case BoardAndDevCardAndLeaderCard:
                case DevCardAndLeader:
                    devCardID = getDevCardIDForProduction();
                    leaderCardID = getLeaderCardIDForProduction();
                    if (devCardID.equals("quit")) {
                        goBack = true;
                    } else {
                        resourcesToEliminateWarehouse = selectResourcesToEliminate(devCardID, "Warehouse");
                        resourcesToEliminateChest = selectResourcesToEliminate(devCardID, "Chest");
                        // TODO verify resources (if present and enough to satisfy production)
                        playerMove = new ProductionMove(devCardID, leaderCardID, resourcesToEliminateWarehouse,
                                resourcesToEliminateChest, productionType,
                                selectBoardOrPerkManufacturedResource(productionType));
                    }
                    break;
                default:
                    System.out.println("Choose a correct option.");
                    break;
            }
        }
        return playerMove;
    }

    // handles the CLI aspect of choosing the LeaderCard for the ProductionMove
    private String getLeaderCardIDForProduction() {
        boolean isCorrectID = false;
        boolean goBack = false;
        String leaderCardID = null;
        List<LeaderCard> leaderCards = getMatch().get().getCurrentPlayer().getBoard().getLeaderCards();
        System.out.println("Choose a Development Card:\n");
        for (LeaderCard leaderCard : leaderCards) {
            System.out.println(leaderCard + "\n");
        }
        do {
            System.out.println(
                    "Provide the ID of the Leader Card for the production or type 'quit'" + " to go back:\n" + "> ");
            String answer = this.stdin.nextLine();
            if (answer.equals("quit")) {
                goBack = true;
            } else {
                for (LeaderCard leaderCard : leaderCards) {
                    if (leaderCard.getId().equals(answer)) {
                        isCorrectID = true;
                        leaderCardID = answer;
                        break;
                    }
                }
                if (!isCorrectID) {
                    System.out.println("Choose a correct ID!");
                }
            }
        } while (!isCorrectID);
        if (goBack) {
            return "quit";
        }
        return leaderCardID;
    }

    // handles the CLI aspect of choosing the DevelopmentCard for the ProductionMove
    private String getDevCardIDForProduction() {
        boolean isCorrectID = false;
        boolean goBack = false;
        String devCardID = null;
        Map<DevCardPosition, DevelopmentCard> productionDevCards = getMatch().get().getCurrentPlayer().getBoard()
                .getCurrentProductionCards();
        System.out.println("Choose a Development Card:\n");
        for (DevCardPosition position : productionDevCards.keySet()) {
            if (productionDevCards.get(position) != null) {
                System.out.println(position + ":\n" + productionDevCards.get(position) + "\n");
            }
        }
        do {
            System.out.println("Provide the ID of the Development Card for the production or type 'quit'"
                    + " to go back:\n" + "> ");
            String answer = this.stdin.nextLine();
            if (answer.equals("quit")) {
                goBack = true;
            } else {
                for (DevCardPosition position : productionDevCards.keySet()) {
                    if (productionDevCards.get(position) != null) {
                        if (productionDevCards.get(position).getId().equals(answer)) {
                            isCorrectID = true;
                            devCardID = answer;
                        }
                    }
                }
                if (!isCorrectID) {
                    System.out.println("Choose a correct ID!");
                }
            }
        } while (!isCorrectID);
        if (goBack) {
            return "quit";
        }
        return devCardID;
    }

    // handles the CLI aspect of choosing which resources to eliminate for a Board
    // production for the ProductionMove
    private void selectResourcesToEliminateForBoardProduction(Map<ResourceType, Integer> resourcesToEliminateWarehouse,
            Map<ResourceType, Integer> resourcesToEliminateChest) {
        boolean eliminateFromWarehouse = false;
        boolean eliminateFromChest = false;
        int toBeEliminated = 2;
        while (true) {
            System.out.println(
                    "Do you want to eliminate resources from the Warehouse for the Board production? [y/n]:\n" + "> ");
            String answer = this.stdin.nextLine();
            if (answer.equals("y")) {
                eliminateFromWarehouse = true;
                while (toBeEliminated == 2) {
                    System.out.println(
                            "How many types of resources do you want to eliminate from the Warehouse? [1/2]:\n" + "> ");
                    answer = this.stdin.nextLine();
                    if (answer.equals("1")) {
                        toBeEliminated = 1;
                        int count = 0;
                        do {
                            System.out.println("Choose the resource you want to eliminate from the Warehouse:\n"
                                    + "1 - Coin;\n" + "2 - Servant;\n" + "3 - Shield;\n" + "4 - Stone.\n" + "> ");
                            answer = this.stdin.nextLine();
                            switch (answer) {
                                case "1":
                                    resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
                                    count++;
                                    break;
                                case "2":
                                    resourcesToEliminateWarehouse.put(ResourceType.Servant, 1);
                                    count++;
                                    break;
                                case "3":
                                    resourcesToEliminateWarehouse.put(ResourceType.Shield, 1);
                                    count++;
                                    break;
                                case "4":
                                    resourcesToEliminateWarehouse.put(ResourceType.Stone, 1);
                                    count++;
                                    break;
                                default:
                                    System.out.println("Choose a correct option.");
                                    break;
                            }
                        } while (count < 1);
                    } else {
                        if (answer.equals("2")) {
                            toBeEliminated = 0;
                            int count = 0;
                            do {
                                System.out.println(
                                        "Choose the resource you want to add to the list of resources to be eliminated "
                                                + "from the Warehouse:\n" + "1 - Coin;\n" + "2 - Servant;\n"
                                                + "3 - Shield;\n" + "4 - Stone.\n" + "> ");
                                answer = this.stdin.nextLine();
                                switch (answer) {
                                    case "1":
                                        if (resourcesToEliminateWarehouse.get(ResourceType.Coin) == 1) {
                                            resourcesToEliminateWarehouse.put(ResourceType.Coin, 2);
                                        } else {
                                            resourcesToEliminateWarehouse.put(ResourceType.Coin, 1);
                                        }
                                        count++;
                                        break;
                                    case "2":
                                        if (resourcesToEliminateWarehouse.get(ResourceType.Servant) == 1) {
                                            resourcesToEliminateWarehouse.put(ResourceType.Servant, 2);
                                        } else {
                                            resourcesToEliminateWarehouse.put(ResourceType.Servant, 1);
                                        }
                                        count++;
                                        break;
                                    case "3":
                                        if (resourcesToEliminateWarehouse.get(ResourceType.Shield) == 1) {
                                            resourcesToEliminateWarehouse.put(ResourceType.Shield, 2);
                                        } else {
                                            resourcesToEliminateWarehouse.put(ResourceType.Shield, 1);
                                        }
                                        count++;
                                        break;
                                    case "4":
                                        if (resourcesToEliminateWarehouse.get(ResourceType.Stone) == 1) {
                                            resourcesToEliminateWarehouse.put(ResourceType.Stone, 2);
                                        } else {
                                            resourcesToEliminateWarehouse.put(ResourceType.Stone, 1);
                                        }
                                        count++;
                                        break;
                                    default:
                                        System.out.println("Choose a correct option.");
                                        break;
                                }
                            } while (count < 2);
                        } else {
                            System.out.println("Choose a correct number.");
                        }
                    }

                }
                break;
            } else {
                if (answer.equals("no")) {
                    break;
                } else {
                    System.out.println("Choose a correct option.");
                }
            }
        }
        if (toBeEliminated == 1) {
            int count = 0;
            do {
                System.out.println("Choose the resource you want to eliminate from the Chest:\n" + "1 - Coin;\n"
                        + "2 - Servant;\n" + "3 - Shield;\n" + "4 - Stone.\n" + "> ");
                String answer = this.stdin.nextLine();
                switch (answer) {
                    case "1":
                        resourcesToEliminateChest.put(ResourceType.Coin, 1);
                        count++;
                        break;
                    case "2":
                        resourcesToEliminateChest.put(ResourceType.Servant, 1);
                        count++;
                        break;
                    case "3":
                        resourcesToEliminateChest.put(ResourceType.Shield, 1);
                        count++;
                        break;
                    case "4":
                        resourcesToEliminateChest.put(ResourceType.Stone, 1);
                        count++;
                        break;
                    default:
                        System.out.println("Choose a correct option.");
                        break;
                }
            } while (count < 1);
        } else {
            if (toBeEliminated == 2) {
                int count = 0;
                do {
                    System.out.println("Choose the resource you want to add to the list of resources to be eliminated "
                            + "from the Warehouse:\n" + "1 - Coin;\n" + "2 - Servant;\n" + "3 - Shield;\n"
                            + "4 - Stone.\n" + "> ");
                    String answer = this.stdin.nextLine();
                    switch (answer) {
                        case "1":
                            if (resourcesToEliminateChest.get(ResourceType.Coin) == 1) {
                                resourcesToEliminateChest.put(ResourceType.Coin, 2);
                            } else {
                                resourcesToEliminateChest.put(ResourceType.Coin, 1);
                            }
                            count++;
                            break;
                        case "2":
                            if (resourcesToEliminateChest.get(ResourceType.Servant) == 1) {
                                resourcesToEliminateChest.put(ResourceType.Servant, 2);
                            } else {
                                resourcesToEliminateChest.put(ResourceType.Servant, 1);
                            }
                            count++;
                            break;
                        case "3":
                            if (resourcesToEliminateChest.get(ResourceType.Shield) == 1) {
                                resourcesToEliminateChest.put(ResourceType.Shield, 2);
                            } else {
                                resourcesToEliminateChest.put(ResourceType.Shield, 1);
                            }
                            count++;
                            break;
                        case "4":
                            if (resourcesToEliminateChest.get(ResourceType.Stone) == 1) {
                                resourcesToEliminateChest.put(ResourceType.Stone, 2);
                            } else {
                                resourcesToEliminateChest.put(ResourceType.Stone, 1);
                            }
                            count++;
                            break;
                        default:
                            System.out.println("Choose a correct option.");
                            break;
                    }
                } while (count < 2);
            }
        }
    }

    // helper function for selectBoardOrPerkManufacturedResource(), handles the main
    // CLI logic
    private void selectBoardOrPerkManufacturedResourceHelper(ProductionType productionType,
            List<ResourceType> boardOrPerkManufacturedResource) {
        do {
            System.out.println("Choose the " + productionType + " production manufactured resource:\n" + "1 - Coin;\n"
                    + "2 - Servant;\n" + "3 - Shield;\n" + "4 - Stone.\n" + "> ");
            String answer = this.stdin.nextLine();
            switch (answer) {
                case "1":
                    boardOrPerkManufacturedResource.add(ResourceType.Coin);
                    break;
                case "2":
                    boardOrPerkManufacturedResource.add(ResourceType.Servant);
                    break;
                case "3":
                    boardOrPerkManufacturedResource.add(ResourceType.Shield);
                    break;
                case "4":
                    boardOrPerkManufacturedResource.add(ResourceType.Stone);
                    break;
                default:
                    System.out.println("Choose a correct number.");
                    break;
            }
        } while (boardOrPerkManufacturedResource.size() == 0);
    }

    // asks the player to choose which resource he/she wants to manufacture for the
    // Board or LeaderCard production
    // or both combined
    private List<ResourceType> selectBoardOrPerkManufacturedResource(ProductionType productionType) {
        List<ResourceType> boardOrPerkManufacturedResource = new ArrayList<>();
        if (productionType.equals(ProductionType.Board) || productionType.equals(ProductionType.LeaderCard)) {
            selectBoardOrPerkManufacturedResourceHelper(productionType, boardOrPerkManufacturedResource);
        }
        if (productionType.equals(ProductionType.BoardAndDevCardAndLeaderCard)
                || productionType.equals(ProductionType.BoardAndLeaderCard)) {
            selectBoardOrPerkManufacturedResourceHelper(ProductionType.Board, boardOrPerkManufacturedResource);
            selectBoardOrPerkManufacturedResourceHelper(ProductionType.LeaderCard, boardOrPerkManufacturedResource);
        }
        return boardOrPerkManufacturedResource;
    }

    public void viewer() {
        System.out.println("0 - Go Back\n" + "1 - show informations about the others players\n"
                + "2 - show your Points\n" + "3 - show your Marker Position\n" + "4 - show your Leader Cards\n"
                + "5 - show your Development Cards\n" + "6 - show the Market\n" + "7 - show your Warehouse\n"
                + "8 - show your history");
        String answer = stdin.nextLine();
        switch (answer) {
            case "0":
                break;
            case "1":
                viewer(getNickname());
                break;
            case "2":
                System.out.println("Your points are: " + getMatch().get().getVictoryPoints(getNickname()));
                break;
            case "3":
                System.out.println(
                        "Your marker position is: " + getMatch().get().getMarkerPosition(getNickname()) + "/24");
                break;
            case "4":
                System.out.println("Your Leader Cards are: " + getMatch().get().getLeaderCardsToString(getNickname()));
                break;
            case "5":
                break;
            case "6":
                System.out.println(getMatch().get().getMarket());
                break;
            case "7":
                System.out.println(getMatch().get().getWarehouseToString(getNickname()));
                break;
            case "8":
                System.out.println(getMatch().get().getHistoryToString(getNickname()));
                break;
            default:
                return;
        }
        return;

    }

    private void viewer(String myNickname) {
        // shows informations about other players
        System.out.println(
                "Your opponents are : " + getMatch().get().getOpponentsToString(myNickname) + "\n tell the nickname");
        String opponent = stdin.nextLine();
        System.out.println(
                "0 - Go Back\n" + "1 - show " + opponent + " Points\n" + "2 - show " + opponent + " Marker Position\n"
                        + "3 - show " + opponent + " Leader Cards\n" + "4 - show " + opponent + " Development Cards\n");
        String answer = stdin.nextLine();
        switch (answer) {
            case "0":
                break;
            case "1":
                System.out.println(opponent + " points are: " + getMatch().get().getVictoryPoints(opponent));
                break;
            case "2":
                System.out.println(
                        opponent + " marker position is: " + getMatch().get().getMarkerPosition(opponent) + "/24");
                break;
            case "3":
                System.out
                        .println(opponent + " Leader Cards are: " + getMatch().get().getLeaderCardsToString(opponent));
                break;
            default:
                return;
        }

    }

    private TakeMarketResourcesMove handleTakeMarketResourcesMove() {
        List<Resource> resourcesToDiscard = new ArrayList<>();
        Market market = getMatch().get().getMarket();
        System.out.println(market);
        String answer;
        int axis, position;
        do {
            System.out.println("do you want to insert the marble horizontally or vertically?\n" + "0 - vertical\n"
                    + "1 - horizontal\n" + "2 - go back and to an other move");
            axis = Integer.parseInt(stdin.nextLine());
        } while (axis < 0 || axis > 2);
        if (axis == 2) {
            return null;
        }
        if (axis == 0) {
            do {
                System.out.println("Which column?\n" + "from 0 to 3, from left to right");
                position = Integer.parseInt(stdin.nextLine());
            } while (position > 3 || position < 0);
        } else {
            do {
                System.out.println("Which line?\n" + "from 0 to 2, from bottom to up");
                position = Integer.parseInt(stdin.nextLine());
            } while (position > 2 || position < 0);
        }

        ResourceType transmutationPerk = getMatch().get().getTransmutationPerk(getNickname());
        List<Resource> resourceList = market.insertMarble(axis, position, transmutationPerk);
        Boolean hasRedMarble = false;
        for (int i = 0; i < resourceList.size(); i++) {
            if (resourceList.get(i).getType() == ResourceType.Faith) {
                hasRedMarble = true;
                resourceList.remove(i);
                break;
            }
        }
        Warehouse warehouse = getMatch().get().getWarehouse(getNickname());
        Map<ResourceType, Integer> resourcesInHand = warehouse.listToMapResources(resourceList);
        System.out.println(warehouse);
        System.out.println("Resources in hand:");
        for (Map.Entry<ResourceType, Integer> entry : resourcesInHand.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        while (resourcesInHand.size() > 0) {
            System.out.println("0 - show Warehouse\n" + "1 - show resources in hand\n"
                    + "2 - insert resources in the shelves\n" + "3 - insert resources in the extra deposit\n"
                    + "4 - discard resources\n" + "5 - swap shelves");
            answer = stdin.nextLine();
            switch (answer) {
                case "0":
                    System.out.println(warehouse);
                    break;
                case "1":
                    System.out.println("Resources in hand:");
                    for (Map.Entry<ResourceType, Integer> entry : resourcesInHand.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;
                case "2":
                    this.insertInShelves(warehouse, resourcesInHand);
                    break;
                case "3":
                    this.insertInExtraDeposit(warehouse, resourcesInHand);
                    break;
                case "4":
                    resourcesToDiscard.addAll(this.discardResources(warehouse, resourcesInHand));
                    break;
                case "5":
                    swapShelves(warehouse);
                    break;
                default:
                    System.out.println("wrong input");
                    break;
            }
        }
        return new TakeMarketResourcesMove(warehouse, resourcesToDiscard, market, hasRedMarble);
    }

    private void insertInShelves(Warehouse warehouse, Map<ResourceType, Integer> resourcesInHand) {

        System.out.println("Which Resource type do you want to put in the shelves?\n");
        Pair<ResourceType, Integer> resourceSelected = resourceSelector(resourcesInHand);
        ShelfFloor shelfFloor;
        if (resourceSelected == null) {
            return;
        }
        System.out.println(warehouse.getShelvesToString());
        System.out.println("1 - First floor\n" + "2 - second floor\n" + "3 - third floor");
        switch (stdin.nextLine()) {
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
        for (int i = 0; i < resourceSelected._2; i++) {
            resourceList.add(new Resource(resourceSelected._1));
        }
        if (!warehouse.insertInShelves(shelfFloor, resourceList)) {
            System.out.println("forbidden action");
        } else {
            int oldvalue = resourcesInHand.remove(resourceSelected._1);
            oldvalue = oldvalue - resourceSelected._2;
            if (oldvalue > 0) {
                resourcesInHand.put(resourceSelected._1, oldvalue);
            }
        }
        return;

    }

    private Pair<ResourceType, Integer> resourceSelector(Map<ResourceType, Integer> resourcesInHand) {
        // returns a map if i have the resources, otherwise null
        String resourceType;
        ResourceType type;
        int n;
        System.out.println("Resources in hand:");
        for (Map.Entry<ResourceType, Integer> entry : resourcesInHand.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("1 - coin\n" + "2 - stone\n" + "3 - shield\n" + "4 - servant");
        resourceType = stdin.nextLine();
        System.out.println("how many?");
        n = Integer.parseInt(stdin.nextLine());
        switch (resourceType) {
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
        if (resourcesInHand.get(type) >= n) {
            return new Pair<>(type, n);
        } else {
            System.out.println("Not enough resources");
            return null;
        }
    }

    private void insertInExtraDeposit(Warehouse warehouse, Map<ResourceType, Integer> resourcesInHand) {
        System.out.println("Which Resource type do you want to put in the extra deposit?\n");
        Pair<ResourceType, Integer> resourceSelected = resourceSelector(resourcesInHand);
        if (resourceSelected == null) {
            return;
        }
        List<Resource> resourceList = new ArrayList<>();
        for (int i = 0; i < resourceSelected._2; i++) {
            resourceList.add(new Resource(resourceSelected._1));
        }
        if (!warehouse.insertInExtraDeposit(resourceList)) {
            System.out.println("forbidden action");
        } else {
            int oldvalue = resourcesInHand.remove(resourceSelected._1);
            oldvalue = oldvalue - resourceSelected._2;
            if (oldvalue > 0) {
                resourcesInHand.put(resourceSelected._1, oldvalue);
            }
        }
        return;

    }

    private List<Resource> discardResources(Warehouse warehouse, Map<ResourceType, Integer> resourcesInHand) {
        System.out.println("Which Resource type do you want to discard?\n");
        Pair<ResourceType, Integer> resourceSelected = resourceSelector(resourcesInHand);
        if (resourceSelected == null) {
            return new ArrayList<>();
        }
        int oldvalue = resourcesInHand.remove(resourceSelected._1);
        oldvalue = oldvalue - resourceSelected._2;
        if (oldvalue > 0) {
            resourcesInHand.put(resourceSelected._1, oldvalue);
        }
        List<Resource> resources = new ArrayList<>();
        for (int i = 0; i < resourceSelected._2; i++) {
            resources.add(new Resource(resourceSelected._1));
        }
        return resources;

    }

    private void swapShelves(Warehouse warehouse) {
        System.out.println("0 - show Warehouse\n" + "1 - swap first and second floor\n"
                + "2 - swap second and third floor\n" + "3 - swap third e first floor\n" + "4 - go back");
        switch (stdin.nextLine()) {
            case "0":
                System.out.println(warehouse);
                break;
            case "1":
                if (warehouse.getShelves().get(ShelfFloor.Second).size() > 1) {
                    System.out.println("forbidden action");
                } else {
                    warehouse.swapShelves(ShelfFloor.First, ShelfFloor.Second);
                }
                break;
            case "2":
                if (warehouse.getShelves().get(ShelfFloor.Third).size() > 2) {
                    System.out.println("forbidden action");
                } else {
                    warehouse.swapShelves(ShelfFloor.Third, ShelfFloor.Second);
                }
                break;
            case "3":
                if (warehouse.getShelves().get(ShelfFloor.Third).size() > 1) {
                    System.out.println("forbidden action");
                } else {
                    warehouse.swapShelves(ShelfFloor.Third, ShelfFloor.First);
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
        Socket socket = new Socket(getIp(), getPort());
        System.out.println("Connection established");
        setSocketOut(new ObjectOutputStream(socket.getOutputStream()));
        setSocketIn(new ObjectInputStream(socket.getInputStream()));
        stdin = new Scanner(System.in);
        try {
            Thread t0 = asyncReadFromSocket();
            Thread t1 = asyncCli();
            buildGame();
            t0.join();
            t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            getSocketIn().close();
            getSocketOut().close();
            socket.close();
        }
    }
}
