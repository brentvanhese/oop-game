import java.util.ArrayList;

/**
 * This class has all the info of a player
 *
 * @author Brent Van Hese
 */

public class Player {
    //variabelen
    private String name;
    private ArrayList<Item> bag;
    private double maxWeightInBag;
    private Planet currentPlanet;
    private int oxygen = 100;
    private boolean giveBillGatesLaptop;
    private boolean talkedToBillGates;
    private boolean allBombExploted;
    private int countExplotedBombs;
    private Planet previousPlanet;
    private boolean burned;
    private int xp;

    //constructor
    public Player(String name) {
        this.name = name;
        bag = new ArrayList<>();
        maxWeightInBag = 25;
        giveBillGatesLaptop = false;
        talkedToBillGates = false;
        allBombExploted = false;
        countExplotedBombs = 0;
        burned = false;
        xp = 0;
    }

    public Player() {
        bag = new ArrayList<>();
        maxWeightInBag = 25;
        giveBillGatesLaptop = false;
        talkedToBillGates = false;
        allBombExploted = false;
        countExplotedBombs = 0;
        burned = false;
        xp = 0;
    }

    //setters
    public void setMaxWeightInBag(double maxWeightInBag) {
        this.maxWeightInBag = maxWeightInBag;
    }

    public void setCurrentRoom(Planet currentPlanet) {
        this.currentPlanet = currentPlanet;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setOxygen(int oxygen) {
        if (oxygen > 100) return;
        this.oxygen = oxygen;
    }

    public void setGiveBillGatesLaptop(boolean giveBillGatesLaptop) {
        this.giveBillGatesLaptop = giveBillGatesLaptop;
    }

    public void setTalkedToBillGates(boolean talkedToBillGates) {
        this.talkedToBillGates = talkedToBillGates;
    }

    public void setAllBombExploted(boolean allBombExploted) {
        this.allBombExploted = allBombExploted;
    }

    public void setPreviousPlanet(Planet previousPlanet) {
        this.previousPlanet = previousPlanet;
    }

    public void setBurned(boolean burned) {
        this.burned = burned;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    //getters
    public String getName() {
        return name;
    }

    public double getMaxWeightInBag() {
        return maxWeightInBag;
    }

    public Planet getCurrentPlanet() {
        return currentPlanet;
    }

    public boolean hasTalkedToBillGates() {
        return talkedToBillGates;
    }

    public boolean hasGivenBillGatesLaptop() {
        return giveBillGatesLaptop;
    }

    public boolean isAllBombExploted() {
        return allBombExploted;
    }

    public int getCountExplotedBombs() {
        return countExplotedBombs;
    }

    public Planet getPreviousPlanet() {
        return previousPlanet;
    }

    public boolean isBurned() {
        return burned;
    }

    public int getXp() {
        return xp;
    }

    //methodes
    public void addItem(Item item){
        bag.add(item);
    }

    /**
     * get all the items of the player's bag
     * @return the items
     */
    public String getBagInfo(){
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

    /**
     * get all the info of the player
     * his name, oxygen, xp and on which planet he/she is
     * @return the player's info
     */
    public String getInfo(){
        String info = "";
        info += "My name is " + name;
        info += "\nOxygen: " + oxygen + "%";
        info += "\nXP: " + xp;
        info += "\n" + getBagInfo();
        info += "And I am on " + currentPlanet.getLongDescription();

        return info;
    }

    /**
     * The player's info for the 'look player' command
     * @return player's info
     */
    public String lookInfo(){
        String info = "My name is " + name;
        info += "\nOxygen: " + oxygen + "%";
        info += "\nXP: " + xp;
        info += "\n" + getBagInfo();
        return info;
    }

    /**
     * player go's to another planet
     * @param direction the direction the player needs to go
     * @return true if the player is on the next planet | false if the direction doesn't exist
     */
    public boolean go(String direction){
        Planet nextPlanet = getCurrentPlanet().getExit(direction);
        if (nextPlanet == null) return false;
        currentPlanet = nextPlanet;
        changeOxygen(nextPlanet);
        return true;
    }

    /**
     * change the oxygen level of the player
     * if he is on earth it is again 100%
     * on a normal planet he/she loses 15% and on a gas-planet the player loses 20%
     * every time you change you also get 10% oxygen
     * @param r the planet you are on
     */
    private void changeOxygen(Planet r){
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

    /**
     * use an item
     * now you can only use the O2-Booster
     * @param itemname the name of the item you want to use
     * @return true if the player used the item | false if not
     */
    public boolean use(String itemname){
        boolean check = false;
        if (itemname.equals("o2-booster")){
            for (Item i : bag) {
                if(i.getName().equals("o2-booster")){
                    oxygen = 100;
                    bag.remove(i);
                    check = true;
                    removeXP(50);
                    break;
                }
            }
        }
        return check;
    }

    /**
     * @param itemName the name of the item
     * @return true if the player can move the item | false if not
     */
    public boolean isMovable(String itemName){
        if (currentPlanet.hasItem(itemName)){
            Item i = currentPlanet.getItem(itemName);
            return i.getIsMovable();
        }
        return false;
    }

    /**
     * take an item and put it in the player's bag
     * @param itemName the name of the item the player wants to take
     * @return true if the item is in the bag | false if the item doesn't exist, isn't movable or isn't show at that moment
     */
    public boolean take(String itemName){
        if (currentPlanet.hasItem(itemName)) {
            Item item = currentPlanet.getItem(itemName);
            if (item.getIsMovable()){
                if (item.isShow()){
                    currentPlanet.removeItem(item);
                    bag.add(item);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * take an item out of the player's bag and put it on the planet
     * @param itemName the name of the item you want to put on the planet
     * @return true if the item is on the planet | false if the item isn't in the players bag
     */
    public boolean drop(String itemName){
        if (hasItem(itemName)){
            Item item = getItem(itemName);
            bag.remove(item);
            currentPlanet.addItem(item);
            return true;
        }
        return false;
    }

    /**
     * @param itemName name of the itel
     * @return true if the item is in the player's bag | false if not
     */
    public boolean hasItem(String itemName){
        for (Item bagItem : bag) {
            if (bagItem.getName().equals(itemName)){
                return true;
            }
        }
        return false;
    }

    /**
     * get an item out of the bag
     * @param itemName the name of the item
     * @return the item
     */
    public Item getItem(String itemName) {
        for (Item bagItem : bag) {
            if (bagItem.getName().equals(itemName)) {
                return bagItem;
            }
        }
        return null;
    }

    public boolean checkItem(String itemName){
        return currentPlanet.hasItem(itemName);
    }

    /**
     * unlock a locked item
     * @param itemName the name of the item
     * @param unlockCode the code to unlock the item
     * @return true if the item is unlocked | false if the unlock code is wrong
     */
    public boolean unlock(String itemName, int unlockCode){
        Item i = currentPlanet.getItem(itemName);
        if (i.getCode() == unlockCode){
            i.setMovable(true);
            addXP(i.getXp());
            return true;
        }
        else{
            i.wrongCode();
        }
        return false;
    }

    /**
     * destroy an item on a planet
     * now you only can destroy the Imac
     * @param itemName the name of the item
     * @return true if the item is destroyed | false if the item isn't on the planet, isn't showed at that moment
     */
    public boolean destroy(String itemName){
        boolean check = false;
        if (itemName.equals("imac")){
            if(currentPlanet.hasItem(itemName)){
                Item imac = currentPlanet.getItem(itemName);
                if (imac.isShow()){
                    currentPlanet.removeItem(imac);
                    addXP(100);
                    check = true;
                }
            }
        }
        return check;
    }

    /**
     * Give an item, from the player's bag, to the person in the room
     * Now a player only can give the microsoft-surface to bill gates
     * @param itemName the name of the item
     * @return true if you gave the person the item | false if the item isn't in the players bag
     */
    public boolean give(String itemName){
        boolean check = false;
        if (itemName.equals("microsoft-surface")){
            if (hasItem(itemName)){
                Item item = getItem(itemName);
                if (currentPlanet.hasPerson("billgates")){
                    bag.remove(item);
                    addXP(100);
                    check = true;
                }
            }
        }
        return check;
    }

    /**
     * @return true if the player has enough oxygen to go to another room | false if he ran out of oxygen
     */
    public boolean alive(){
        boolean check = true;
        if (oxygen <= 0){
            check = false;
        }
        return check;
    }

    public void addExplotedBomb(){
        countExplotedBombs += 1;
        addXP(25);
    }

    public void addXP(int extraXP){
        xp += extraXP;
    }

    public void removeXP(int extraXP){
        xp -= extraXP;
    }
}