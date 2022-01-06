import java.util.ArrayList;
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
    private HashMap<String, CommandWord> validCommands;
    private ArrayList<String> validExplenation;
    private ArrayList<String> commands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        validExplenation = new ArrayList<>();
        commands = new ArrayList<>();

        for (CommandWord commandWord : CommandWord.values()) {
            if(commandWord!=CommandWord.UNKNOWN){
                validCommands.put(commandWord.getWord(), commandWord);
                commands.add(commandWord.getWord());
                validExplenation.add(commandWord.getExplanation());
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
        for (int i = 0; i < commands.size(); i++) {
            show += commands.get(i) + " : " + validExplenation.get(i) + "\n";
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
