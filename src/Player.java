import java.util.ArrayList;

public class Player {
    //variabelen
    private String name;
    private ArrayList<Item> bag;
    private double maxWeightInBag;
    private Room currentRoom;

    //constructor
    public Player(String name) {
        this.name = name;
        bag = new ArrayList<>();
        maxWeightInBag = 100;
    }

    public Player() {
        bag = new ArrayList<>();
        maxWeightInBag = 25;
    }

    //setters
    public void setMaxWeightInBag(double maxWeightInBag) {
        this.maxWeightInBag = maxWeightInBag;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setName(String name){
        this.name = name;
    }

    //getters
    public String getName() {
        return name;
    }

    public double getMaxWeightInBag() {
        return maxWeightInBag;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    //methodes
    public void addItem(Item item){
        bag.add(item);
    }

    private String getBagInfo(){
        String bagInfo = "";
        if (!bag.isEmpty()){
            bagInfo += "I have a bag which contains: ";
            for (Item bagItem : bag) {
                bagInfo += "      " + bagItem.toString();
            }
            bagInfo += "\n\n";
        }
        return bagInfo;
    }

    public String getInfo(){
        String info = "My name is " + name;
        info += "\n" + getBagInfo();
        info += "And I am " + currentRoom.getLongDescription();

        return info;
    }

    public boolean go(String direction){
        Room nextRoom = getCurrentRoom().getExit(direction);
        if (nextRoom == null) return false;
        currentRoom = nextRoom;
        return true;
    }

    public boolean take(String itemName){
        if (currentRoom.hasItem(itemName)) {
            Item item = currentRoom.getItem(itemName);
            if (item.getIsMovable()){
                currentRoom.removeItem(item);
                bag.add(item);
                return true;
            }
        }
        return false;
    }

    public boolean drop(String itemName){
        if (hasItem(itemName)){
            Item item = getItem(itemName);
            bag.remove(item);
            currentRoom.addItem(item);
            return true;
        }
        return false;
    }

    public boolean hasItem(String itemName){
        for (Item bagItem : bag) {
            if (bagItem.getName().equals(itemName)){
                return true;
            }
        }
        return false;
    }

    public Item getItem(String itemName){
        for (Item bagItem : bag) {
            if (bagItem.getName().equals(itemName)){
                return bagItem;
            }
        }
        return null;
    }
}
