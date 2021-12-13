import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    /*private static final String[] validCommands = {
        "go", "take", "drop", "look", "quit", "help", "eat"
    };*/

    private HashMap<String, CommandWord> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();

        for (CommandWord commandWord : CommandWord.values()) {
            if(commandWord!=CommandWord.UNKNOWN){
                validCommands.put(commandWord.getWord(), commandWord);
            }
        }
    }

    /**
     * Check whether a given String is a valid command word.
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    public String showAll() {
        String show = "";
        for (String command : validCommands.keySet()) {
            show += command + " ";
        }
        return show;
    }

    public CommandWord getCommand(String aString) {
        if (isCommand(aString)){
            return validCommands.get(aString);
        }
        return CommandWord.UNKNOWN;
    }


}
