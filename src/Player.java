import java.util.ArrayList;

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
    }

    public Player() {
        bag = new ArrayList<>();
        maxWeightInBag = 25;
        giveBillGatesLaptop = false;
        talkedToBillGates = false;
        allBombExploted = false;
        countExplotedBombs = 0;
        burned = false;
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

    //methodes
    public void addItem(Item item){
        bag.add(item);
    }

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

    public String getInfo(){
        String info = "";
        info += "My name is " + name;
        info += "\nOxygen: " + oxygen + "%";
        info += "\n" + getBagInfo();
        info += "And I am on " + currentPlanet.getLongDescription();

        return info;
    }

    public String lookInfo(){
        String info = "My name is " + name;
        info += "\nOxygen: " + oxygen + "%";
        info += "\n" + getBagInfo();
        return info;
    }

    public boolean go(String direction){
        Planet nextPlanet = getCurrentPlanet().getExit(direction);
        if (nextPlanet == null) return false;
        currentPlanet = nextPlanet;
        changeOxygen(nextPlanet);
        return true;
    }

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

    public boolean use(String itemname){
        boolean check = false;
        if (itemname.equals("o2-booster")){
            for (Item i : bag) {
                if(i.getName().equals("o2-booster")){
                    oxygen = 100;
                    bag.remove(i);
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    public boolean isMovable(String itemName){
        if (currentPlanet.hasItem(itemName)){
            Item i = currentPlanet.getItem(itemName);
            return i.getIsMovable();
        }
        return false;
    }

    public boolean take(String itemName){
        if (currentPlanet.hasItem(itemName)) {
            Item item = currentPlanet.getItem(itemName);
            if (item.getIsMovable()){
                currentPlanet.removeItem(item);
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
            currentPlanet.addItem(item);
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

    public boolean checkItem(String itemName){
        return currentPlanet.hasItem(itemName);
    }

    public boolean unlock(String itemName, int unlockCode){
        Item i = currentPlanet.getItem(itemName);
        if (i.getCode() == unlockCode){
            i.setMovable(true);
            return true;
        }
        return false;
    }

    public boolean destroy(String itemName){
        boolean check = false;
        if (itemName.equals("imac")){
            if(currentPlanet.hasItem(itemName)){
                Item imac = currentPlanet.getItem(itemName);
                currentPlanet.removeItem(imac);
                check = true;
            }
        }
        return check;
    }

    public boolean give(String itemName){
        boolean check = false;
        if (itemName.equals("microsoft-surface")){
            if (hasItem(itemName)){
                Item item = getItem(itemName);
                if (currentPlanet.hasPerson("billgates")){
                    bag.remove(item);
                    check = true;
                }
            }
        }
        return check;
    }

    public boolean alive(){
        boolean check = true;
        if (oxygen <= 0){
            check = false;
        }
        return check;
    }

    public void addExplotedBomb(){
        countExplotedBombs += 1;
    }
}