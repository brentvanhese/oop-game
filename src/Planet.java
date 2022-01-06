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
 * @author  Brent Van Hese
 */

public class Planet
{
    private String description;
    private boolean gasplaneet = false;
    public HashMap<String, Planet> exits;
    private ArrayList<Person> persons;
    private ArrayList<Item> items;
    private boolean hasInvisableItems = false;

    /**
     * Create a planet described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Planet(String description)
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
        persons = new ArrayList<>();
    }

    //getters
    public boolean isGasplaneet() {
        return gasplaneet;
    }

    //setters
    public void setGasplaneet(boolean gasplaneet) {
        this.gasplaneet = gasplaneet;
    }

    public void setHasInvisableItems(boolean hasInvisableItems) {
        this.hasInvisableItems = hasInvisableItems;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item){
        items.remove(item);
    }

    public void addPerson(Person person){
        persons.add(person);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return all the info of the planet
     */
    public String getLongDescription() {
        String info =  description;
        if (!items.isEmpty()){
            if (!hasInvisableItems){
                info += ". This planet contains";
                for (Item item : items) {
                    info += item.toString();
                }
                if (!persons.isEmpty()){
                    for (Person p : persons) {
                        if (!p.isShow()){
                            info += p.showPersonInRoom();
                        }
                    }
                }

                info += "\n";
            }

        }
        info += "\n" + getExitString();
        return info;
    }

    /**
     * @return the info for the 'look planet' command
     */
    public String planetInfo(){
        return "I am on " + getLongDescription();
    }

    /**
     * @param personName name of the person of who the player wants the text
     * @return the text of the person
     */
    public String getPersonString(String personName){
        String output = "";
        String changedName = personName.toLowerCase().replace(" ", "");
        for (Person p : persons) {
            if (p.getName().equals(changedName)){
                if (!p.isShow()){
                    output = "\n" + p.toString();
                    p.setShow(true);
                }
            }
        }
        return output;
    }

    public void setExit(String direction, Planet planet){
        exits.put(direction, planet);
    }

    public Planet getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * @return the possible directions a player can go to from a planet
     */
    public String getExitString(){
        String exitString = "Exits: ";
        for (String direction : exits.keySet()){
            exitString += direction + " ";
        }
        return exitString;
    }

    /**
     * check if an item is on a planet
     * @param name name of the item
     * @return true if the item is on the planet | false if the item isn't on the planet
     */
    public boolean hasItem(String name){
        for (Item item : items) {
            if (item.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    /**
     * get an item from a planet
     * @param itemName the name of the item
     * @return the item the player wants
     */
    public Item getItem(String itemName){
        for (Item item : items) {
            if (item.getName().equals(itemName)){
                return item;
            }
        }
        return null;
    }

    /**
     * check if person is on that planet
     * @param personName name of the person
     * @return true if the person is on the planet | false if he/she isn't
     */
    public boolean hasPerson(String personName){
        for (Person p : persons) {
            if (p.getName().equals(personName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether a person has talked with the player
     * @param personName name of the person
     * @return true if he/she has talked with the player | false if he/she hasn't
     */
    public boolean hasTalked(String personName){
        for (Person p : persons) {
            if (p.isShow()){
                return true;
            }
        }
        return false;
    }

    /**
     * get the text of a person when the player hasn't talked with bill gates
     * @param personName name of the person
     * @return the text of the person when he/she is locked
     */
    public String getLockedText(String personName){
        String output = "";
        for (Person p : persons) {
            if (p.getName().equals(personName)){
                output = p.getLockedText();
                break;
            }
        }
        return output;
    }

    public Person getPerson(){
        for (Person p : persons) {
            return p;
        }
        return null;
    }
}