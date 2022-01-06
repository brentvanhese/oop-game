import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 *  This class is the main class of the "World of Zuul" application.
 *  "World of Zuul" is a very simple, text based adventure game.  Users
 *  can walk around some scenery. That's all. It should really be extended
 *  to make it more interesting!
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  planets, items and persons. Creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Brent Van Hese
 */

public class Game
{
    private Parser parser;
    private Player player;
    private String namePlayer;
    private Scanner scanner;
    private ArrayList<Item>invisableItems;
    private ArrayList<Planet> otherPlanets;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED= "\u001B[31m";

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        player = new Player();
        parser = new Parser();
        scanner = new Scanner(System.in);
        invisableItems = new ArrayList<>();
        otherPlanets = new ArrayList<>();
    }

    public void createGame(GenerateGame gg){
        invisableItems = gg.getInvisableItems();
        otherPlanets = gg.getOtherPlanets();
    }

    /**
     *  Main play routine.  Loops until end of play.
     *  Checks for the oxygen of a player and checks or that all the bombs have exploded
     */
    public void play()
    {
        System.out.print("Please enter your name: ");
        namePlayer = scanner.nextLine();
        player.setName(namePlayer);
        GenerateGame gg = new GenerateGame(player);
        createGame(gg);
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if (!player.alive() || player.isAllBombExploted()){
                finished = true;
            }

        }

        printGoodbye();
    }

    /**
     * Print out the end message for the player
     * There is a default message which can have an extra message if you are out of oxygen, that all the bombs exploded or if you burned up on the sun
     * also you can see your xp
     */
    private void printGoodbye(){
        if (! player.alive()){
            System.out.println("You ran out of oxygen and died.");
        }
        if (player.isAllBombExploted()){
            System.out.println("congratulations you saved the world !!!");
        }
        if (player.isBurned()){
            System.out.println("You burned up on the sun.");
        }
        System.out.println();
        System.out.println("You had " + player.getXp() + " XP");
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println(player.getCurrentPlanet().getPersonString("Elon Musk"));
        System.out.println();
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        CommandWord commandWord = command.getCommandWord();
        //if you are on the sun you can only give 1 command and is to leave otherwise you die
        if (player.getCurrentPlanet().getDescription().equals("the sun")){
            if (command.getCommandWord().getWord().equals("go")){
                goRoom(command);
            }
            else{
                player.setBurned(true);
                wantToQuit = true;
            }
        }
        else {
            switch (commandWord){
                case HELP:
                    printHelp();
                    break;
                case GO:
                    goRoom(command);
                    break;
                case TAKE:
                    take(command);
                    break;
                case DROP:
                    drop(command);
                    break;
                case QUIT:
                    wantToQuit = quit(command);
                    break;
                case LOOK:
                    look(command);
                    break;
                case USE:
                    use(command);
                    break;
                case UNLOCK:
                    unlock(command);
                    break;
                case TALK:
                    talk(command);
                    break;
                case DESTROY:
                    destroy(command);
                    break;
                case GIVE:
                    give(command);
                    break;
                case EXPLODE:
                    explode(command);
                    break;
                case BACK:
                    back(command);
                    break;
                case ITEMS:
                    showItems(command);
                    break;
                default:
                    System.out.println("I don't know what you mean...");
            }
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print all the command words + a description
     */
    private void printHelp()
    {
        System.out.println("Player " + player.getName() + " needs to save the world by finding the bombs and let them explode on the comet");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
        System.out.println();
    }

    /**
     * Print out the info of the player + the planet
     */
    private void printLocationInfo() {
        System.out.println(player.getInfo());
        System.out.println();
    }

    /**
     * Without a second word in the command it execute printLocationInfo
     * If the second word of the command is: 'Planet', it prints out the planet info
     * If the second word of the command is: 'Person', it prints out the info of the player
     * @param command The command to be processed.
     */
    private void look(Command command){
        if (!command.hasSecondWord()){
            printLocationInfo();

        }
        else{
            String lookWhere = command.getSecondWord();
            if(lookWhere.equals("planet")){
                System.out.println(player.getCurrentPlanet().planetInfo() + "\n");
            }
            else if (lookWhere.equals("player")){
                System.out.println(player.lookInfo());
            }
        }

    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     * @param command The command to be processed.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        player.setPreviousPlanet(player.getCurrentPlanet());
        // Try to leave current room.
        if (!player.go(direction)){
            System.out.println("There is no planet!");
        }else {
            if (player.getCurrentPlanet().getDescription().equals("the sun")){
                printLocationInfo();
                System.out.println(ANSI_RED + "You need to go away or you will die" + ANSI_RESET);
            }
            else{
                if (!player.hasGivenBillGatesLaptop()){
                    try{
                        Person p = player.getCurrentPlanet().getPerson();
                        if (!p.getName().equals("elonmusk")){
                            if (!p.getName().equals("")){
                                System.out.println(p.showLockedText());
                            }
                            if (p.getName().equals("billgates")){
                                player.setTalkedToBillGates(true);
                                showInvisibleItems();
                            }
                        }
                        printLocationInfo();
                    }catch (NullPointerException n){
                        printLocationInfo();
                    }
                }
                else printLocationInfo();
            }
        }
    }

    /**
     * to go back to the previous planet
     * you can only go one planet back
     * otherwise you get an error
     * @param command The command to be processed.
     */
    private void back(Command command){
        if (command.hasSecondWord()){
            System.out.println("I don't know what you mean...");
        }
        try {
            if(player.getPreviousPlanet().equals(player.getCurrentPlanet())){
                System.out.println("You can only go the last planet you visited");
            }
            else {
                player.setCurrentRoom(player.getPreviousPlanet());
                printLocationInfo();
            }
        }
        catch (NullPointerException npe){
            System.out.println("You can't go back now");
        }

    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @param command The command to be processed.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * take an item from the planet and put it in the players bag
     * @param command The command to be processed.
     */
    private void take(Command command){
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        String itemName = command.getSecondWord();
        if (player.take(itemName)) {
            printLocationInfo();
        } else if(player.isMovable(itemName)){
            System.out.println("There is no item here with the name " + itemName);
        } else {
            System.out.println(itemName + " isn't movable, unlock it first.");
        }
    }

    /**
     * Put an item out of the players bag on the planet
     * @param command The command to be processed.
     */
    private void drop(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Drop what?");
            return;
        }
        String itemName = command.getSecondWord();
        if(player.drop(itemName)){
            printLocationInfo();
        }else{
            System.out.println("There is no item here with the name " + itemName);
        }
    }

    /**
     * Use an item out of the players bag
     * @param command The command to be processed.
     */
    private void use(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Use what?");
            return;
        }
        String itemName = command.getSecondWord();
        if (player.use(itemName)){
            printLocationInfo();
        }
        else{
            System.out.println("There is no item in your bag with the name " + itemName);
        }
    }

    /**
     * unlock a locked item so the player can put it in his bag
     * @param command The command to be processed.
     */
    private void unlock(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Move what?");
            return;
        }
        String itemName = command.getSecondWord();
        System.out.println("Please give the unlock code for " + itemName + " :");
        int unlockCode = scanner.nextInt();
        if (player.checkItem(itemName)){
            if(player.unlock(itemName, unlockCode)){
                System.out.println(itemName + " is unlocked.\n");
                printLocationInfo();
            }
            else {
                System.out.println("Wrong code, please try again.");
            }
        }
        else {
            System.out.println("There is no item with name " + itemName);
        }
    }

    /**
     * talk with a person on a planet
     * @param command The command to be processed.
     */
    private void talk(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Talk to who?");
            return;
        }
        String personName = command.getSecondWord().toLowerCase().replace("-", "");
        if (player.getCurrentPlanet().hasPerson(personName)){
            if (personName.equals("billgates")){
                if (player.getCurrentPlanet().hasItem("microsoft-surface")){
                    player.setGiveBillGatesLaptop(true);
                }
            }
            if (player.hasGivenBillGatesLaptop()){
                if (!player.getCurrentPlanet().hasTalked(personName)){
                    System.out.println(player.getCurrentPlanet().getPersonString(personName));
                }
                else {
                    System.out.println("You already talked with " + personName);
                }
            }
            else {
                //System.out.println(player.getCurrentPlanet().getLockedText(personName));
                System.out.println(player.getCurrentPlanet().getPerson().getDisplayName() + " :\tFirst you need to talk to Bill gates.");
            }


        }
        else {
            System.out.println("There is no person with the name " + personName);
        }
    }

    /**
     * destroy an item on a planet
     * @param command The command to be processed.
     */
    private void destroy(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Destroy what?");
            return;
        }
        String itemName = command.getSecondWord().toLowerCase();
        if (player.destroy(itemName)){
            System.out.println(itemName + " has been destroyed.");
        }
        else{
            System.out.println("There is no item with name " + itemName);
        }
    }

    /**
     * give an item to a person, then he/she will help you
     * @param command The command to be processed.
     */
    private void give(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Give what?");
            return;
        }
        String itemName = command.getSecondWord().toLowerCase();
        if (player.give(itemName)){
            System.out.println("You gave " + player.getCurrentPlanet().getPerson().getDisplayName() + " his " + itemName);
            player.setGiveBillGatesLaptop(true);
            System.out.println(player.getCurrentPlanet().getPersonString("billgates") + "\n");
            printLocationInfo();
        }
        else {
            System.out.println("There is no item in your bag with the name " + itemName);
        }
    }

    /**
     * if bill gates have said something to you, show the hidden items on earth
     */
    private void showInvisibleItems(){
        for (Planet p : otherPlanets){
            if (p.getDescription().equals("earth")){
                p.setHasInvisableItems(false);
            }
        }

        for (Item i : invisableItems) {
            i.setShow(true);
            i.setMovable(true);
        }
    }

    /**
     * let a bomb explode
     * the bomb needs to be on the comet, and the player can't be on the comet
     * @param command The command to be processed.
     */
    private void explode(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Explote what");
        }
        String itemName = command.getSecondWord().toLowerCase();
        for (Planet p : otherPlanets) {
            if (p.getDescription().equals("the comet")){
                if (p.hasItem(itemName)){
                    if (!player.getCurrentPlanet().getDescription().equals("the comet")){
                        Item i  = p.getItem(itemName);
                        p.removeItem(i);
                        System.out.println(itemName + " exploded on " + p.getDescription());
                        player.addExplotedBomb();
                        if (player.getCountExplotedBombs() == 3){
                            player.setAllBombExploted(true);
                        }
                        break;
                    }
                    else {
                        System.out.println("You can't be here to let a bomb explode. Go to a planet.");
                    }
                }
            }
        }
    }

    /**
     * Show all the items in the players bag with their weight
     * @param command The command to be processed.
     */
    private void showItems(Command command){
        if (command.hasSecondWord()){
            System.out.println("I don't know what you mean...");
        }
        else {
            System.out.println(player.getBagInfo());
        }
    }
}
