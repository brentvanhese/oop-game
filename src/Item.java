/**
 * This class has all the info of an item
 *
 * It can change the amount of xp
 * @author Brent Van Hese
 */

public class Item {
    private String name;
    private String description;
    private double weight;
    private boolean isMovable;
    private int code;
    private String location;
    private boolean show;
    private int xp;
    private int amountOfWrongCode;

    public Item(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        isMovable = true;
        show = true;
        xp = 0;
        amountOfWrongCode = 0;
    }

    //setters
    public void setMovable(boolean movable) {
        isMovable = movable;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    //getters
    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public boolean getIsMovable() {
        return isMovable;
    }

    public String getLocation() {
        return location;
    }

    public boolean isShow() {
        return show;
    }

    public int getXp() {
        return xp;
    }

    /**
     * @return all the info of an item
     */
    @Override
    public String toString() {
        String output = "";
        if (show){
            output = "\n      " + this.name + " (" +this.description +") with a weight of " + this.weight + "kg";
        }
        return output;
    }

    /**
     * If the player wants to unlock a bomb, and he gives the wrong unlock code he loses 50% of the xp he can earn
     * If his second unlock code is also wrong, he won't get any xp
     */
    public void wrongCode(){
        if (amountOfWrongCode != 1){
            xp = xp /2;
            amountOfWrongCode++;
        }
        else {
            xp = 0;
        }
    }
}
