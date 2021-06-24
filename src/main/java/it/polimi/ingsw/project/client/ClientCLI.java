package it.polimi.ingsw.project.client;

import it.polimi.ingsw.project.messages.ResponseMessage;
import it.polimi.ingsw.project.model.CardContainer;
import it.polimi.ingsw.project.model.Match;
import it.polimi.ingsw.project.model.Player;
import it.polimi.ingsw.project.model.TurnPhase;
import it.polimi.ingsw.project.model.board.Board;
import it.polimi.ingsw.project.model.board.DevCardPosition;
import it.polimi.ingsw.project.model.board.ShelfFloor;
import it.polimi.ingsw.project.model.board.Warehouse;
import it.polimi.ingsw.project.model.board.card.developmentCard.DevelopmentCard;
import it.polimi.ingsw.project.model.board.card.leaderCard.LeaderCard;
import it.polimi.ingsw.project.model.market.Market;
import it.polimi.ingsw.project.model.playermove.*;
import it.polimi.ingsw.project.model.playermove.interfaces.Request;
import it.polimi.ingsw.project.model.resource.Resource;
import it.polimi.ingsw.project.model.resource.ResourceType;
import it.polimi.ingsw.project.utils.Pair;
import it.polimi.ingsw.project.utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class ClientCLI extends Client {

    private Scanner stdin;
    private boolean lock = true;
    private Socket socket;

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
        TurnPhase oldTurnPhase = TurnPhase.MainPhase;
        if (this.match != null)
            oldTurnPhase = this.match.getTurnPhase(getNickname());
        this.match = match;
        if (!this.match.getisOver()) {
            if (oldTurnPhase == TurnPhase.WaitPhase
                    && this.match.getTurnPhase(getNickname()) == TurnPhase.InitialPhase) {
                System.out.println("It's your Turn!\nPress 0 to start");
            }
        } else {
            this.showScoreboard();
            super.setActive(false);
        }

        unLock();

    }

    private ResourceType chooseSingleResource(int remainingResources) {
        while (true) {
            try {
                System.out.println("Which Resource do you want? (" + remainingResources
                        + ")\n1 - Shield\n2 - Servant\n3 - Coin\n4 - Stone");
                switch (stdin.nextLine()) {
                    case "1":
                        return ResourceType.Shield;
                    case "2":
                        return ResourceType.Servant;
                    case "3":
                        return ResourceType.Coin;
                    case "4":
                        return ResourceType.Stone;
                    default:
                        System.out.println("This action was not valid. Try again.");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void chooseResources(Integer numberOfResourcesToChoose) {
        List<ResourceType> listOfChosenResources = new ArrayList<>();
        for (int i = 0; i < numberOfResourcesToChoose; i++) {
            listOfChosenResources.add(chooseSingleResource(numberOfResourcesToChoose - listOfChosenResources.size()));
        }
        try {
            getSocketOut().writeObject(new ChooseResourcesMove(this.myNickname, this.gameId, listOfChosenResources));
            getSocketOut().flush();
            this.showWaitMessageForOtherPlayers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            setActive(false);
        }

    }

    private void showScoreboard() {
        LinkedHashMap<Integer, Player> scoreboard = match.getLeaderboard();
        for (Integer position : scoreboard.keySet()) {
            System.out.println("" + (position + 1) + "° " + scoreboard.get(position).getNickname() + " VictoryPoints:"
                    + scoreboard.get(position).getVictoryPoints());
        }

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
                        ResponseMessage inputObject = (ResponseMessage) socketIn.readObject();
                        inputObject.action(getInstance());
                    }
                } catch (Exception e) {
                    setActive(false);
                    unLock();
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
            socketOut.writeObject(new ChooseLeaderCardMove(getNickname(), super.gameId,
                    Utils.extractSelectedLeaderCards(allLeaderCard, chosenIds)));
            socketOut.flush();
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
            socketOut.writeObject(new JoinRequestMove(getNickname(), gameId));
            socketOut.flush();
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
            socketOut.writeObject(new CreateRequestMove(playersNumber, getNickname()));
            socketOut.flush();
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
                        if (!isActive()) {
                            System.out.println("The server is disconnected retry later.");
                            break;
                        }
                        if (!getMatch().isEmpty()) {
                            Request move = handleTurn();
                            while (move == null) {
                                move = handleTurn();
                            }
                            socketOut.writeObject(move);
                            socketOut.flush();
                            socketOut.reset();
                            setLock();
                        }

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

        while (true) {
            switch (this.match.getTurnPhase(getNickname())) {
                case WaitPhase:
                    viewer();
                    break;
                case InitialPhase:
                case EndPhase:
                    return handleLeaderAction();
                case MainPhase:
                    return handleMainPhase();

            }
        }
        // return null;
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
                    System.out.println(this.match.getLeaderCardsToString(getNickname()));
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
            System.out
                    .println("What do you want to do?\n" + "0 - See informations\n" + "1 - Take Resources from Market\n"
                            + "2 - Buy one Development Card\n" + "3 - Activate Production.\n");
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
            while (isInputError)
                ;
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
            System.out.println(this.match.getLeaderCardsToString(getNickname()));
            do {
                System.out.println("Provide the ID of the LeaderCard you want to activate: (Type 'back' to go back)");
                String answer = this.stdin.nextLine();
                if (answer.equals("back")) {
                    return null;
                }
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

    private boolean isActivateLeaderCardMovePossible(String leaderCardID) {
        return this.match.isFeasibleActivateLeaderCardMove(leaderCardID);
    }

    // constructs the BuyDevCardMove according to the player choices
    // TODO verify if the resources to eliminate chosen by the player are correct
    private Move constructBuyDevCardMove() {
        Move playerMove = null;
        Map<ResourceType, Integer> resourcesToEliminateWarehouse = new HashMap<>();
        Map<ResourceType, Integer> resourcesToEliminateChest = new HashMap<>();
        boolean skipElimination = false;
        List<DevelopmentCard> availableDevCards = this.match.getCardContainer().getAvailableDevCards();
        DevelopmentCard devCardToBuy = showAndSelectDevCardToBuy(availableDevCards);
        if (devCardToBuy != null) {
            // while (true) {
            // System.out.println("Do you want to eliminate resources from the Warehouse?
            // (y/n): ");
            // String answer = this.stdin.nextLine();
            // if (answer.equals("y")) {
            // resourcesToEliminateWarehouse = selectResourcesToEliminate(devCardToBuyID,
            // "Warehouse");
            // break;
            // } else {
            // if (answer.equals("n")) {
            // skipElimination = true;
            // break;
            // } else {
            // System.out.println("Choose a correct option.\n");
            // }
            // }
            // }
            // if (!resourcesToEliminateWarehouse.isEmpty() || skipElimination) {
            // skipElimination = false;
            // while (true) {
            // System.out.println("Do you want to eliminate resources from the Chest? (y/n):
            // ");
            // String answer = this.stdin.nextLine();
            // if (answer.equals("y")) {
            // resourcesToEliminateChest = selectResourcesToEliminate(devCardToBuyID,
            // "Chest");
            // break;
            // } else {
            // if (answer.equals("n")) {
            // skipElimination = true;
            // break;
            // } else {
            // System.out.println("Choose a correct option.\n");
            // }
            // }
            // }
            // if (!resourcesToEliminateChest.isEmpty() || skipElimination) {
            // if (resourcesToEliminateWarehouse.isEmpty() &&
            // resourcesToEliminateChest.isEmpty()) {
            // System.out.println("You did not select any resource to be eliminated. Move
            // aborted.\n");
            // } else {
            // DevCardPosition position = selectPositionForDevCard(devCardToBuyID);
            // if (position != null) {
            // playerMove = new BuyDevCardMove(devCardToBuyID, position,
            // resourcesToEliminateWarehouse,
            // resourcesToEliminateChest);
            // }
            // }
            // }
            // }
            return this.selectResToBuyDevCard(devCardToBuy);
        }
        return playerMove;
    }

    // TODO CON EXTRADEPOSIT
    private Move selectResToBuyDevCard(DevelopmentCard developmentCard) {
        Board board = this.match.getBoardByPlayerNickname(myNickname);
        String answer;
        Map<ResourceType, Integer> resourceRequired, resourcesToEliminateWarehouse, resourceToEliminateChest,
                resourceToEliminateExtraDeposit;
        resourceRequired = developmentCard.getCost();
        this.decreaseForDiscount(resourceRequired, board);
        resourcesToEliminateWarehouse = new HashMap<>();
        resourceToEliminateChest = new HashMap<>();
        resourceToEliminateExtraDeposit = new HashMap<>();
        if (!board.areEnoughResourcesPresent(resourceRequired)) {
            System.out.println("Not enough resources!");
            return null;
            // questo mi attesta che le risorse ci sono di sicuro, devo solo scegliere
            // l'ordine
        }
        // for (Map.Entry<ResourceType, Integer> entry : resourceRequired.entrySet()) {
        // System.out.println(entry.getKey() + " required : " + entry.getValue());
        // System.out.println("\nYour Resources: \n" + board.resourcesToString());
        // System.out.println("0 - go back\n" + "1 - use Warehouse's resources first and
        // then Chest's\n"
        // + "2 - use Chest's resources first and then Warehouse's");
        // answer = stdin.nextLine();
        // switch (answer) {
        // // uno di questi metodi deve per forza andare bene a causa del controllo
        // // precedente
        // case "1":
        // this.warehousefirst(board, entry.getKey(), entry.getValue(),
        // resourcesToEliminateWarehouse,
        // resourceToEliminateChest);
        // break;
        // case "2":
        // this.chestfirst(board, entry.getKey(), entry.getValue(),
        // resourcesToEliminateWarehouse,
        // resourceToEliminateChest);
        // break;
        // case "0":
        // default:
        // return null;
        // }
        // }
        DevCardPosition devCardPosition = selectPositionForDevCard(developmentCard.getId());
        if (devCardPosition == null) {
            return null;
        } else {
            this.selectResourcesFromBoard(resourceRequired, resourcesToEliminateWarehouse, resourceToEliminateChest,
                    resourceToEliminateExtraDeposit);
        }

        return new BuyDevCardMove(developmentCard.getId(), devCardPosition, resourcesToEliminateWarehouse,
                resourceToEliminateExtraDeposit, resourceToEliminateChest);
    }

    private void decreaseForDiscount(Map<ResourceType, Integer> resourceRequired, Board board) {
        List<ResourceType> discounts = board.getDiscounts();
        for (ResourceType resourceType : resourceRequired.keySet()) {
            if (resourceRequired.get(resourceType) > 0) {
                if (discounts.contains(resourceType)) {
                    resourceRequired.put(resourceType, resourceRequired.get(resourceType) - 1);
                }
            }
        }
    }

    private void chestfirst(Board board, ResourceType resourcetype, Integer integer,
            Map<ResourceType, Integer> resourcesToEliminateWarehouse,
            Map<ResourceType, Integer> resourceToEliminateChest) {
        Map<ResourceType, Integer> wareHouseMap = new HashMap<>();
        Map<ResourceType, Integer> chestMap = new HashMap<>();
        for (int i = 0; i <= integer; i++) {
            wareHouseMap.put(resourcetype, i);
            chestMap.put(resourcetype, integer - i);
        }
    }

    // TODO METTERE MAPPA CON EXTRA DEPOSIT E SOSTITUIRLA ALLA new HashMap<>()
    private void warehousefirst(Board board, ResourceType resourcetype, Integer integer,
            Map<ResourceType, Integer> resourcesToEliminateWarehouse,
            Map<ResourceType, Integer> resourceToEliminateChest) {
        Map<ResourceType, Integer> wareHouseMap = new HashMap<>();
        Map<ResourceType, Integer> chestMap = new HashMap<>();
        for (int i = 0; i <= integer; i++) {
            wareHouseMap.put(resourcetype, integer - i);
            chestMap.put(resourcetype, i);

            // TODO METTERE MAPPA CON EXTRA DEPOSIT E SOSTITUIRLA ALLA new HashMap<>()
            if (board.areEnoughResourcesPresentForBuyAndProduction(wareHouseMap, new HashMap<>(), chestMap)) {
                break;
            }
        }
        resourceToEliminateChest.put(resourcetype, chestMap.get(resourcetype));
        resourcesToEliminateWarehouse.put(resourcetype, wareHouseMap.get(resourcetype));
    }

    // provides the id of the DevelopmentCard the player wants to buy. Returns null
    // if the player
    // wants to go back
    private DevelopmentCard showAndSelectDevCardToBuy(List<DevelopmentCard> availableDevCards) {
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
                        return devCard;
                    }
                }
                if (!isCardPresent) {
                    System.out.println("A Development Card with that ID is not available. Please provide a correct ID "
                            + "or go back.");
                }
            } else {
                return null;
            }
        }
        return null;
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
            System.out.println("Select the resource type to eliminate from the " + location + " :\n" + "0 - Go Back;\n"
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
        DevelopmentCard devCardToBuy = this.match.getCardContainer().fetchCard(devCardToBuyID);
        DevCardPosition chosenPosition = null;
        String answer = null;
        do {
            while (chosenPosition == null) {
                System.out.println("Choose an option:\n" + "0 - Go Back\n" + "1 - Left;\n" + "2 - Center;\n"
                        + "3 - Right.\n" + "Enter here your answer: ");
                answer = this.stdin.nextLine();
                switch (answer) {
                    case "0":
                        return null;
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
            int lastPosition = this.match.getBoardByPlayerNickname(getNickname()).getMapTray().get(chosenPosition)
                    .size();
            if (lastPosition == 0)
                return chosenPosition;
            DevelopmentCard devCardInLastPosition = this.match.getBoardByPlayerNickname(getNickname()).getMapTray()
                    .get(chosenPosition).get(lastPosition - 1);
            if (!Utils.isOneLevelUpper(devCardToBuy.getLevel(), devCardInLastPosition.getLevel())) {
                chosenPosition = null;
                System.out.println("Forbidden Action");
            }

        } while (chosenPosition == null);
        return chosenPosition;
    }

    private Map<ResourceType, Integer> selectResourcesForBoardProduction() {
        Map<ResourceType, Integer> resourcesForBoardProduction = new HashMap<>();
        Map<ResourceType, Integer> resourcesContainedInWarehouseAndChest = this.match.getCurrentPlayer().getBoard()
                .getWarehouse().mapAllContainedResources();
        this.match.getCurrentPlayer().getBoard().getChest()
                .forEach((ResourceType resource, Integer numberOfResources) -> {
                    if (resourcesContainedInWarehouseAndChest.containsKey(resource)) {
                        resourcesContainedInWarehouseAndChest.put(resource,
                                resourcesContainedInWarehouseAndChest.get(resource) + numberOfResources);
                    } else {
                        resourcesContainedInWarehouseAndChest.put(resource, numberOfResources);
                    }
                });
        while (resourcesForBoardProduction.size() < 2) {
            System.out.println("Which resource do you want to use?\nYour resources are:\n");
            StringBuilder mapToString = new StringBuilder();
            resourcesContainedInWarehouseAndChest.forEach((ResourceType resourceType, Integer numberOfResources) -> {
                if (numberOfResources != 0) {
                    mapToString.append(resourceType + ": " + numberOfResources);
                }
            });
            System.out.println(mapToString.toString());
            System.out.println("Choose option from:\n" + "1 - Servant\n" + "2 - Shield\n" + "3 - Stone\n" + "4 - Coin\n"
                    + "Which one do you choose? 'exit' to leave.\n");
            switch (this.stdin.nextLine()) {
                case "1":
                    if (resourcesContainedInWarehouseAndChest.containsKey(ResourceType.Servant)) {
                        if (resourcesContainedInWarehouseAndChest.get(ResourceType.Servant) > 0) {
                            if (resourcesForBoardProduction.containsKey(ResourceType.Servant)) {
                                resourcesForBoardProduction.put(ResourceType.Servant, 2);
                            } else {
                                resourcesForBoardProduction.put(ResourceType.Servant, 1);
                                resourcesContainedInWarehouseAndChest.replace(ResourceType.Servant,
                                        resourcesContainedInWarehouseAndChest.get(ResourceType.Servant) - 1);
                            }
                        } else {
                            System.out.println("You don't have enough resources in your deposits!");
                        }
                    } else {
                        System.out.println("You don't have this resource in your deposits!");
                    }
                    break;
                case "2":
                    if (resourcesContainedInWarehouseAndChest.containsKey(ResourceType.Shield)) {
                        if (resourcesContainedInWarehouseAndChest.get(ResourceType.Shield) > 0) {
                            if (resourcesForBoardProduction.containsKey(ResourceType.Shield)) {
                                resourcesForBoardProduction.put(ResourceType.Shield, 2);
                            } else {
                                resourcesForBoardProduction.put(ResourceType.Shield, 1);
                                resourcesContainedInWarehouseAndChest.replace(ResourceType.Shield,
                                        resourcesContainedInWarehouseAndChest.get(ResourceType.Shield) - 1);
                            }
                        } else {
                            System.out.println("You don't have enough resources in your deposits!");
                        }
                    } else {
                        System.out.println("You don't have this resource in your deposits!");
                    }
                    break;
                case "3":
                    if (resourcesContainedInWarehouseAndChest.containsKey(ResourceType.Stone)) {
                        if (resourcesContainedInWarehouseAndChest.get(ResourceType.Stone) > 0) {
                            if (resourcesForBoardProduction.containsKey(ResourceType.Stone)) {
                                resourcesForBoardProduction.put(ResourceType.Stone, 2);
                            } else {
                                resourcesForBoardProduction.put(ResourceType.Stone, 1);
                                resourcesContainedInWarehouseAndChest.replace(ResourceType.Stone,
                                        resourcesContainedInWarehouseAndChest.get(ResourceType.Stone) - 1);
                            }
                        } else {
                            System.out.println("You don't have enough resources in your deposits!");
                        }
                    } else {
                        System.out.println("You don't have this resource in your deposits!");
                    }
                    break;
                case "4":
                    if (resourcesContainedInWarehouseAndChest.containsKey(ResourceType.Coin)) {
                        if (resourcesContainedInWarehouseAndChest.get(ResourceType.Coin) > 0) {
                            if (resourcesForBoardProduction.containsKey(ResourceType.Coin)) {
                                resourcesForBoardProduction.put(ResourceType.Coin, 2);
                            } else {
                                resourcesForBoardProduction.put(ResourceType.Coin, 1);
                                resourcesContainedInWarehouseAndChest.replace(ResourceType.Coin,
                                        resourcesContainedInWarehouseAndChest.get(ResourceType.Coin) - 1);
                            }
                        } else {
                            System.out.println("You don't have enough resources in your deposits!");
                        }
                    } else {
                        System.out.println("You don't have this resource in your deposits!");
                    }
                    break;
                case "exit":
                    return null;
                default:
                    System.out.println("Action not permitted! Choose a valid one.");
                    break;
            }
        }
        return resourcesForBoardProduction;
    }

    // constructs the ProductionMove according to the player choices
    // TODO ADATTARE ALLA NUOVA PRODUCTIONMOVE CON EXTRA DEPOSITS
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
            Map<ResourceType, Integer> resourcesToEliminateExtraDeposit = new HashMap<>();
            String devCardID = null;
            String leaderCardID = null;
            switch (productionType) {
                case Board:
                    // selects which resources to eliminate and from where
                    Map<ResourceType, Integer> requiredResourcesBoard = selectResourcesForBoardProduction();
                    if (requiredResourcesBoard == null) {
                        goBack = true;
                    } else {
                        selectResourcesFromBoard(requiredResourcesBoard, resourcesToEliminateWarehouse,
                                resourcesToEliminateChest, resourcesToEliminateExtraDeposit);
                        // TODO ADATTARE ALLA NUOVA PRODUCTIONMOVE CON EXTRA DEPOSITS ( togli new
                        // HashMap<>() )
                        // constructs the move
                        playerMove = new ProductionMove(null, null, resourcesToEliminateWarehouse, new HashMap<>(),
                                resourcesToEliminateChest, productionType,
                                selectBoardOrPerkManufacturedResource(productionType));
                    }
                    break;
                case DevCard:
                    devCardID = getDevCardIDForProduction();
                    if (devCardID.equals("quit")) {
                        goBack = true;
                    } else {
                        Map<ResourceType, Integer> requiredResourcesDevelopmentCard = new HashMap<>();
                        for (DevelopmentCard developmentCard : this.match.getCurrentPlayer().getBoard()
                                .getCurrentProductionCards().values()) {
                            if (developmentCard.getId() == devCardID) {
                                requiredResourcesDevelopmentCard.putAll(developmentCard.getRequiredResources());
                            }
                        }
                        selectResourcesFromBoard(match.getCardContainer().fetchCard(devCardID).getRequiredResources(),
                                resourcesToEliminateWarehouse, resourcesToEliminateChest,
                                resourcesToEliminateExtraDeposit);
                        // TODO ADATTARE ALLA NUOVA PRODUCTIONMOVE CON EXTRA DEPOSITS ( togli new
                        // HashMap<>() )
                        playerMove = new ProductionMove(devCardID, null, resourcesToEliminateWarehouse, new HashMap<>(),
                                resourcesToEliminateChest, productionType, null);
                    }
                    break;
                case LeaderCard:
                case BoardAndLeaderCard:
                    leaderCardID = getLeaderCardIDForProduction();
                    if (leaderCardID.equals("quit")) {
                        goBack = true;
                    } else {
                        Map<ResourceType, Integer> requiredResources = new HashMap<>();
                        for (LeaderCard leaderCard : this.match.getCurrentPlayer().getLeaderCards()) {
                            if (leaderCard.getId() == leaderCardID) {
                                requiredResources.putAll(leaderCard.getRequiredResources());
                            }
                        }

                        selectResourcesFromBoard(requiredResources, resourcesToEliminateWarehouse,
                                resourcesToEliminateChest, resourcesToEliminateExtraDeposit);
                        // TODO ADATTARE ALLA NUOVA PRODUCTIONMOVE CON EXTRA DEPOSITS ( togli new
                        // HashMap<>() )
                        playerMove = new ProductionMove(null, leaderCardID, resourcesToEliminateWarehouse,
                                new HashMap<>(), resourcesToEliminateChest, productionType,
                                selectBoardOrPerkManufacturedResource(productionType));
                    }
                    break;
                case BoardAndDevCard:
                    devCardID = getDevCardIDForProduction();
                    if (devCardID.equals("quit")) {
                        goBack = true;
                    } else {
                        Map<ResourceType, Integer> requiredResourcesDevelopmentCard = new HashMap<>();
                        for (DevelopmentCard developmentCard : this.match.getCurrentPlayer().getBoard()
                                .getCurrentProductionCards().values()) {
                            if (developmentCard.getId() == devCardID) {
                                requiredResourcesDevelopmentCard.putAll(developmentCard.getRequiredResources());
                            }
                        }
                        selectResourcesFromBoard(requiredResourcesDevelopmentCard, resourcesToEliminateWarehouse,
                                resourcesToEliminateChest, resourcesToEliminateExtraDeposit);
                        // TODO ADATTARE ALLA NUOVA PRODUCTIONMOVE CON EXTRA DEPOSITS ( togli new
                        // HashMap<>() )
                        playerMove = new ProductionMove(devCardID, null, resourcesToEliminateWarehouse, new HashMap<>(),
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
                        Map<ResourceType, Integer> requiredResources = new LinkedHashMap<>();
                        for (LeaderCard leaderCard : this.match.getCurrentPlayer().getLeaderCards()) {
                            if (leaderCard.getId() == leaderCardID) {
                                requiredResources.putAll(leaderCard.getRequiredResources());
                            }
                        }
                        Map<ResourceType, Integer> requiredResourcesDevelopmentCard = new HashMap<>();
                        for (DevelopmentCard developmentCard : this.match.getCurrentPlayer().getBoard()
                                .getCurrentProductionCards().values()) {
                            if (developmentCard.getId() == devCardID) {
                                requiredResourcesDevelopmentCard.putAll(developmentCard.getRequiredResources());
                            }
                        }
                        requiredResourcesDevelopmentCard.forEach((ResourceType resource, Integer numberOfResources) -> {
                            if (requiredResources.containsKey(resource)) {
                                requiredResources.put(resource, requiredResources.get(resource) + numberOfResources);
                            } else {
                                requiredResources.put(resource, numberOfResources);
                            }
                        });
                        selectResourcesFromBoard(requiredResources, resourcesToEliminateWarehouse,
                                resourcesToEliminateChest, resourcesToEliminateExtraDeposit);
                        // TODO ADATTARE ALLA NUOVA PRODUCTIONMOVE CON EXTRA DEPOSITS ( togli new
                        // HashMap<>() )
                        playerMove = new ProductionMove(devCardID, leaderCardID, resourcesToEliminateWarehouse,
                                new HashMap<>(), resourcesToEliminateChest, productionType,
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
        List<LeaderCard> leaderCards = this.match.getCurrentPlayer().getBoard().getLeaderCards();
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
        Map<DevCardPosition, DevelopmentCard> productionDevCards = this.match.getCurrentPlayer().getBoard()
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
                            break;
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
        int gameSize = this.match.getPlayerList().size();
        if (gameSize == 1) {
            System.out.println("0 - Go Back\n" + "1 - Show the Black Marker Position\n" + "2 - show your Points\n"
                    + "3 - show your Marker Position\n" + "4 - show your Leader Cards\n"
                    + "5 - show your Development Cards\n" + "6 - show the Market\n" + "7 - show your Resources\n"
                    + "8 - show your history and the Action Token that have been extracted\n"
                    + "9 - show the CardContainer");
        } else {
            System.out.println("0 - Go Back\n" + "1 - show informations about the others players\n"
                    + "2 - show your Points\n" + "3 - show your Marker Position\n" + "4 - show your Leader Cards\n"
                    + "5 - show your Development Cards\n" + "6 - show the Market\n" + "7 - show your Resources\n"
                    + "8 - show your history\n"
                    + "9 - show the CardContainer");
        }
        String answer = stdin.nextLine();
        switch (answer) {
            case "0":
                break;
            case "1":
                if (gameSize == 1) {
                    System.out.println("The Black Marker position is: " + this.match.getBlackMarkerPosition() + "/24");
                } else {
                    viewer(getNickname());
                }
                break;
            case "2":
                System.out.println("Your points are: " + this.match.getVictoryPoints(getNickname()));
                break;
            case "3":
                System.out.println("Your marker position is: " + this.match.getMarkerPosition(getNickname()) + "/24");
                break;
            case "4":
                System.out.println("Your Leader Cards are: " + this.match.getLeaderCardsToString(getNickname()));
                break;
            case "5":
                System.out.println("Your Development Cards are:\n"
                        + this.match.getBoardByPlayerNickname(myNickname).getMapTrayToString());
                break;
            case "6":
                System.out.println(this.match.getMarket());
                break;
            case "7":
                System.out.println(
                        "Your resources are:\n" + this.match.getBoardByPlayerNickname(myNickname).resourcesToString());
                break;
            case "8":
                System.out.println(this.match.getHistoryToString(getNickname()));
                break;
            case "9":
                CardContainer cardContainer = match.getCardContainer();
                List<DevelopmentCard> availableDevCards = cardContainer.getAvailableDevCards();
                for (DevelopmentCard devCard : availableDevCards) {
                    System.out.println(devCard.toString());
                }
                break;
            default:
                return;
        }
        return;

    }

    private void viewer(String myNickname) {
        // shows informations about other players
        System.out
                .println("Your opponents are : " + this.match.getOpponentsToString(myNickname) + "\nTell the nickname");

        String opponent = stdin.nextLine();
        System.out.println(
                "0 - Go Back\n" + "1 - show " + opponent + " Points\n" + "2 - show " + opponent + " Marker Position\n"
                        + "3 - show " + opponent + " Leader Cards\n" + "4 - show " + opponent + " Development Cards\n");

        String answer = stdin.nextLine();
        switch (answer) {
            case "0":
                break;
            case "1":
                System.out.println(opponent + " points are: " + this.match.getVictoryPoints(opponent));
                break;
            case "2":
                System.out.println(opponent + " marker position is: " + this.match.getMarkerPosition(opponent) + "/24");
                break;
            case "3":
                System.out.println(opponent + " Leader Cards are: " + this.match.getLeaderCardsToString(opponent));
                break;
            default:
                return;
        }
    }

    private TakeMarketResourcesMove handleTakeMarketResourcesMove() {
        List<Resource> resourcesToDiscard = new ArrayList<>();
        Market market = this.match.getMarket();
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

        List<ResourceType> transmutationPerk = this.match.getTransmutationPerk(getNickname());

        List<Resource> resourceList = market.insertMarble(axis, position,
                this.handleTransmutationPerk(transmutationPerk));

        Boolean hasRedMarble = false;
        for (int i = 0; i < resourceList.size(); i++) {
            if (resourceList.get(i).getType() == ResourceType.Faith) {
                hasRedMarble = true;
                resourceList.remove(i);
                break;
            }
        }
        Warehouse warehouse = this.match.getWarehouse(getNickname());
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
                    resourcesToDiscard.addAll(this.discardResources(resourcesInHand));
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

    private ResourceType handleTransmutationPerk(List<ResourceType> transmutationPerk) {
        int answer;
        if (transmutationPerk.size() == 0) {
            return null;
        }
        if (transmutationPerk.size() != 2) {
            return transmutationPerk.get(0);
        } else {
            do {
                System.out.println("Chose the perk to use:\n" + "1 - " + transmutationPerk.get(0).toString() + "\n2 - "
                        + transmutationPerk.get(1).toString());
                answer = Integer.parseInt(stdin.nextLine());
            } while (!(answer == 1 || answer == 2));
            return transmutationPerk.get(answer - 1);
        }
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
        try {
            if (resourcesInHand.get(type) >= n) {
                return new Pair<>(type, n);
            } else {
                System.out.println("Not enough resources");
                return null;
            }
        } catch (NullPointerException e) {
            System.out.println("Selected resources not present");
            return null;
        }
    }

    private void insertInExtraDeposit(Warehouse warehouse, Map<ResourceType, Integer> resourcesInHand) {
        if (warehouse.getExtraDeposit() == null) {
            System.out.println("You have not an Extra Depostit");
            return;
        }
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

    private List<Resource> discardResources(Map<ResourceType, Integer> resourcesInHand) {
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

    private void selectResourcesFromBoard(Map<ResourceType, Integer> resourceRequired,
            Map<ResourceType, Integer> resourcesToEliminateWarehouse,
            Map<ResourceType, Integer> resourcesToEliminateChest,
            Map<ResourceType, Integer> resourceToEliminateExtraDeposit) {
        Board board = this.match.getBoardByPlayerNickname(myNickname);
        Warehouse warehouse = board.getWarehouse();
        System.out.println("Your resources are:\n" + board.resourcesToString());
        for (ResourceType resourceType : resourceRequired.keySet()) {
            for (int i = 0; i < 3; i++) {
                if (resourceRequired.get(resourceType) == 0) {
                    break;
                }
                int resourceSelected = 0;
                switch (i) {
                    case 0:
                        do {
                            System.out.println("You need " + resourceRequired.get(resourceType) + " " + resourceType);
                            System.out.println("How many " + resourceType + " do you want to take from the Shelves?");
                            System.out.print(warehouse.getShelvesToString());
                            String answer = stdin.nextLine();
                            try {
                                resourceSelected = Integer.parseInt(answer);
                            } catch (NumberFormatException e) {
                                resourceSelected = -1;
                            }

                        } while (resourceSelected > resourceRequired.get(resourceType) || resourceSelected < 0);
                        if (resourceSelected != 0) {
                            resourceRequired.put(resourceType, resourceRequired.get(resourceType) - resourceSelected);
                            resourcesToEliminateWarehouse.put(resourceType, resourceSelected);
                        }
                        break;
                    case 1:
                        if (warehouse.getExtraDeposit() != null) {
                            if (warehouse.getExtraDeposit().containsKey(resourceType)) {
                                if (warehouse.getExtraDeposit().get(resourceType) != 0) {
                                    do {
                                        System.out.println(
                                                "You need " + resourceRequired.get(resourceType) + " " + resourceType);
                                        System.out.println("How many " + resourceType
                                                + " do you want to take from the Extra Deposit?");
                                        System.out.print(warehouse.getExtraDepositToString());
                                        String answer = stdin.nextLine();
                                        try {
                                            resourceSelected = Integer.parseInt(answer);
                                        } catch (NumberFormatException e) {
                                            resourceSelected = -1;
                                        }
                                    } while (resourceSelected > resourceRequired.get(resourceType)
                                            || resourceSelected < 0);
                                    if (resourceSelected != 0) {
                                        resourceRequired.put(resourceType,
                                                resourceRequired.get(resourceType) - resourceSelected);
                                        resourceToEliminateExtraDeposit.put(resourceType, resourceSelected);
                                    }
                                }
                            }
                        }

                        break;
                    case 2:
                        if (board.getChest().containsKey(resourceType)) {
                            if (board.getChest().get(resourceType) != 0) {
                                do {
                                    System.out.println(
                                            "You need " + resourceRequired.get(resourceType) + " " + resourceType);
                                    System.out.println(
                                            "How many " + resourceType + " do you want to take from the Chest?");
                                    System.out.println(board.getChest().toString());
                                    String answer = stdin.nextLine();
                                    try {
                                        resourceSelected = Integer.parseInt(answer);
                                    } catch (NumberFormatException e) {
                                        resourceSelected = -1;
                                    }
                                } while (resourceSelected > resourceRequired.get(resourceType) || resourceSelected < 0);
                                if (resourceSelected != 0) {
                                    resourceRequired.put(resourceType,
                                            resourceRequired.get(resourceType) - resourceSelected);
                                    resourcesToEliminateChest.put(resourceType, resourceSelected);
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }

    }

    public void run() throws IOException {
        this.socket = new Socket(getIp(), getPort());
        System.out.println("Connection established");
        setSocketOut(new ObjectOutputStream(this.socket.getOutputStream()));
        setSocketIn(new ObjectInputStream(this.socket.getInputStream()));
        this.stdin = new Scanner(System.in);
        try {
            Thread t0 = asyncReadFromSocket();
            Thread t1 = asyncCli();
            buildGame();
            t0.join();
            t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the server side");
        } finally {
            this.stdin.close();
            socketIn.close();
            socketOut.close();
            this.socket.close();
        }
    }
}
