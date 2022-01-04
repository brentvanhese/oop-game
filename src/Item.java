public class Item {
    private String name;
    private String description;
    private double weight;
    private boolean isMovable;
    private int code;
    private String location;
    private boolean show;

    public Item(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        isMovable = true;
        show = true;
    }

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

    @Override
    public String toString() {
        String output = "";
        if (show){
            output = "\n      " + this.name + " (" +this.description +") with a weight of " + this.weight + "kg";
        }
        return output;
    }
}
