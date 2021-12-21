import java.util.ArrayList;

public class Player {
    //variabelen
    private String name;
    private ArrayList<Item> bag;
    private double maxWeightInBag;
    private Room currentRoom;
    private int oxygen = 100;

    //constructor
    public Player(String name) {
        this.name = name;
        bag = new ArrayList<>();
        maxWeightInBag = 25;
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

    public void setOxygen(int oxygen) {
        if (oxygen > 100) return;
        this.oxygen = oxygen;
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
        info += "\nOxygen: " + oxygen + "%";

        return info;
    }

    public boolean go(String direction){
        Room nextRoom = getCurrentRoom().getExit(direction);
        if (nextRoom == null) return false;
        currentRoom = nextRoom;
        changeOxygen(nextRoom);
        return true;
    }

    private void changeOxygen(Room r){
        if(r.getDescription().equals("earth")){
            oxygen = 100;
            return;
        }
        if(r.isGasplaneet()){
            oxygen += 10;
            oxygen -= 20;
        }
        else{
            oxygen +=10;
            oxygen -= 15;
        }
    }

    public boolean use(String itemname){
        boolean check = false;
        if (itemname.equals("O2-booster")){
            for (Item i : bag) {
                if(i.getName().equals("O2-booster")){
                    oxygen = 100;
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public boolean isMovable(String itemName){
        if (currentRoom.hasItem(itemName)){
            Item i = currentRoom.getItem(itemName);
            return i.getIsMovable();
        }
        return false;
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

    public Item getItem(String itemName) {
        for (Item bagItem : bag) {
            if (bagItem.getName().equals(itemName)) {
                return bagItem;
            }
        }
        return null;
    }

    public boolean unlock(String itemName, int unlockCode){
        Item i = currentRoom.getItem(itemName);
        if (i.getCode() == unlockCode){
            i.setMovable(true);
            take(itemName);
        }
        return false;
    }
}
