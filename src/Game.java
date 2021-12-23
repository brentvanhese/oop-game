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
        parser = new Parser();
        scanner = new Scanner(System.in);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createPlanets()
    {
        //name rooms and items
        Planet earth, neptunes, uranus, sun, mars, jupiter, mercurius, comet, saturnus, venus;
        Item bom1, bom2, bom3, O2Booster, code1, code2, code3;
        Person elonMusk, person1, person2, person3;

        // create the planets
        earth = new Planet("earth");
        neptunes = new Planet("neptunes");
        uranus = new Planet("uranus");
        sun = new Planet("the sun");
        mars = new Planet("mars");
        jupiter = new Planet("jupiter");
        mercurius = new Planet("mercurius");
        comet = new Planet("the comet");
        saturnus = new Planet("saturnus");
        venus = new Planet("venus");

        //creat unlock codes
        Random unlockCodeGenerator = new Random();
        int low = 100000;
        int high = 999999;
        int unlockCode1 = unlockCodeGenerator.nextInt(high-low) + low;
        int unlockCode2 = unlockCodeGenerator.nextInt(high-low) + low;
        int unlockCode3 = unlockCodeGenerator.nextInt(high-low) + low;

        //create the items
        bom1 = new Item("bom1", "", 10.0);
        bom1.setMovable(false);
        bom2 = new Item("bom2", "", 10.0);
        bom2.setMovable(false);
        bom3 = new Item("bom3", "", 10.0);
        bom3.setMovable(false);
        O2Booster = new Item("o2-booster", "bring your O2 to 100%", 0.5);
        code1 = new Item("code1", Integer.toString(unlockCode1), 0.1);
        bom1.setCode(unlockCode1);
        code2 = new Item("code2", Integer.toString(unlockCode2), 0.1);
        bom2.setCode(unlockCode2);
        code3 = new Item("code3", Integer.toString(unlockCode3), 0.1);
        bom3.setCode(unlockCode3);

        //create persons
        String txtElon = "";
        txtElon += "\tWelcome SpaceX astronaut " + player.getName();
        txtElon+= "\n\t\t\t\tWe are dealing with a major problem, there is a comet in bound to the earth.";
        txtElon+= "\n\t\t\t\tYou are the only astronaut that can fly our Starship 5.0.";
        txtElon+= "\n\t\t\t\tYou need to fly it to all the planets. We have put on different planets bombs and codes to unlock them.";
        txtElon+= "\n\t\t\t\tWe don't know anymore where we have put them and the computer where we had written it has crashed.";
        txtElon+= "\n\t\t\t\tPlease go get the bombs and put them on the comet, so that you can them explote from earth.";
        txtElon+= "\n\t\t\t\tHold in mind that your spacesuit is still a prototype, so you will lose 15% on a normal planet and 20% gas planet. Everytime you go back in your Starship get 10%.";
        txtElon+= "\n\t\t\t\tGo save the world " + player.getName() + "!";

        elonMusk = new Person("Elon-Musk", "elonmusk" , txtElon);
        person1 = new Person("Person-1", "person1", "To unlock Bomb 1, you will need " + code1.getName());
        person2 = new Person("Person2", "person2", "To unlock Bomb 2, you will need " + code2.getName());
        person3 = new Person("Person3", "person3", "To unlock Bomb 3, you will need " + code3.getName());
        //add items to planets
        //todo put items and rooms in list and put the items in a random room except sun or comet
        earth.addItem(code1);
        earth.addItem(code2);
        earth.addItem(code3);
        neptunes.addItem(O2Booster);
        mars.addItem(bom1);
        mars.addItem(bom2);
        mars.addItem(bom3);

        //add persons to planet
        earth.addPerson(elonMusk);
        mars.addPerson(person1);

        // initialise planets exits
        earth.setExit("north", neptunes);
        earth.setExit("east", mars);
        neptunes.setExit("north", sun);
        neptunes.setExit("east", uranus);
        neptunes.setExit("south", earth);
        uranus.setExit("west", neptunes);
        sun.setExit("east", saturnus);
        sun.setExit("south", neptunes);
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

        //initialize gasplanets
        saturnus.setGasplaneet(true);
        jupiter.setGasplaneet(true);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        System.out.print("Please enter your name: ");
        namePlayer = scanner.nextLine();
        player.setName(namePlayer);
        createPlanets();
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
    //todo make person that talks to player when you see him for the first time
    private void printWelcome()
    {
        System.out.println(player.getCurrentRoom().getPersonString("Elon Musk"));
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
            case USE:
                use(command);
                break;
            case UNLOCK:
                unlock(command);
                break;
            case TALK:
                talk(command);
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
        } else if(player.isMovable(itemName)){
            System.out.println("There is no item here with the name " + itemName);
        } else {
            System.out.println(itemName + " isn't movable, unlock it first.");
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

    public void use(Command command){
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

    public void unlock(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Move what?");
            return;
        }
        String itemName = command.getSecondWord();
        System.out.println("Please give the unlock code for " + itemName + " :");
        int unlockCode = scanner.nextInt();
        if (player.checkItem(itemName)){
            if(player.unlock(itemName, unlockCode)){
                System.out.println("Wrong code, please try again.");
            }
            else {
                System.out.println(itemName + " is unlocked.\n");
                printLocationInfo();
            }
        }
        else {
            System.out.println("There is no item with name " + itemName);
        }
    }
    //todo talk enkel uitvoeren als aangeroepen wordt
    public void talk(Command command){
        if (!command.hasSecondWord()){
            System.out.println("Talk to who?");
            return;
        }
        String personName = command.getSecondWord().toLowerCase().replace("-", "");
        if (player.getCurrentRoom().hasPerson(personName)){
            if (!player.getCurrentRoom().hasTalked(personName)){
                System.out.println(player.getCurrentRoom().getPersonString(personName));
            }
            else {
                System.out.println("You already talked with " + personName);
            }
        }
        else {
            System.out.println("There is no person with the name " + personName);
        }
    }
}
