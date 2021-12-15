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
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game
{
    private Parser parser;
    private Player player;
    private String namePlayer;
    private Scanner scanner;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        player = new Player();
        createRooms();
        parser = new Parser();
        scanner = new Scanner(System.in);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        //name rooms and items
        Room earth, neptunes, uranus, sun, mars, jupiter, mercurius, comet, saturnus, venus;
        Item bom1, bom2, bom3, O2Booster, code1, code2, code3;

        // create the rooms
        earth = new Room("earth");
        neptunes = new Room("neptunes");
        uranus = new Room("uranus");
        sun = new Room("sun");
        mars = new Room("mars");
        jupiter = new Room("jupiter");
        mercurius = new Room("mercurius");
        comet = new Room("comet");
        saturnus = new Room("saturnus");
        venus = new Room("venus");

        //creat unlock codes
        Random unlockCodeGenerator = new Random();
        int low = 100000;
        int high = 999999;
        int unlockCode1 = unlockCodeGenerator.nextInt(high-low) + low;
        int unlockCode2 = unlockCodeGenerator.nextInt(high-low) + low;
        int unlockCode3 = unlockCodeGenerator.nextInt(high-low) + low;

        //create the items
        bom1 = new Item("Bom1", "", 10.0);
        bom2 = new Item("Bom2", "", 10.0);
        bom3 = new Item("Bom3", "", 10.0);
        O2Booster = new Item("O2-booster", "bring your O2 to 100%", 0.5);
        code1 = new Item("Code1", Integer.toString(unlockCode1), 0.1);
        code2 = new Item("Code2", Integer.toString(unlockCode2), 0.1);
        code3 = new Item("Code3", Integer.toString(unlockCode3), 0.1);

        //add items to rooms
        //todo put items and rooms in list and put the items in a random room except sun or comet
        earth.addItem(code1);
        earth.addItem(code2);
        earth.addItem(code3);
        neptunes.addItem(O2Booster);

        // initialise room exits
        earth.setExit("north", neptunes);
        earth.setExit("east", mars);
        neptunes.setExit("north", sun);
        neptunes.setExit("east", uranus);
        neptunes.setExit("south", earth);
        uranus.setExit("west", neptunes);
        sun.setExit("east", saturnus);
        sun.setExit("south", earth);
        saturnus.setExit("east", venus);
        saturnus.setExit("south", comet);
        saturnus.setExit("west", sun);
        venus.setExit("west", saturnus);
        comet.setExit("north", saturnus);
        comet.setExit("south", mercurius);
        mercurius.setExit("north", comet);
        mercurius.setExit("west", mars);
        mars.setExit("north", jupiter);
        mars.setExit("east", mercurius);
        mars.setExit("west", earth);
        jupiter.setExit("south", mars);

        player.setCurrentRoom(earth);  // start game earth
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        System.out.print("Please enter your name: ");
        namePlayer = scanner.nextLine();

        player.setName(namePlayer);

        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome astronaut " + player.getName());
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
                printLocationInfo();
                break;
            case EAT:
                eat();
                break;
            default:
                System.out.println("I don't know what you mean...");
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("Player " + player.getName() + " is lost and alone, and wanders");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
        System.out.println();
    }

    private void printLocationInfo() {
        System.out.println(player.getInfo());
        System.out.println();
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        if (!player.go(direction)){
            System.out.println("There is no door!");
        }else {
            printLocationInfo();
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
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

    public void eat(){
        System.out.println("I have eaten and I am not hungry anymore");
        System.out.println();
    }

    public void take(Command command){
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        String itemName = command.getSecondWord();
        if (player.take(itemName)) {
            printLocationInfo();
        } else {
            System.out.println("There is no item here with the name " + itemName);
        }
    }

    public void drop(Command command){
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

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
