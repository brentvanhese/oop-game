import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Planet
{
    private String description;
    private boolean gasplaneet = false;
    public HashMap<String, Planet> exits;
    private ArrayList<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Planet(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    public boolean isGasplaneet() {
        return gasplaneet;
    }

    public void setGasplaneet(boolean gasplaneet) {
        this.gasplaneet = gasplaneet;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item){
        items.remove(item);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public String getLongDescription() {
        String info =  description;
        if (!items.isEmpty()){
            info += ". This planet contains items:";
            for (Item item : items) {
                info += item.toString();
            }
            info += "\n";
        }
        info += "\n" + getExitString();
        return info;
    }

    public void setExit(String direction, Planet planet){
        exits.put(direction, planet);
    }

    public Planet getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitString(){
        String exitString = "Exits: ";
        for (String direction : exits.keySet()){
            exitString += direction + " ";
        }
        return exitString;
    }

    public boolean hasItem(String name){
        for (Item item : items) {
            if (item.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public Item getItem(String itemName){
        for (Item item : items) {
            if (item.getName().equals(itemName)){
                return item;
            }
        }
        return null;
    }
}