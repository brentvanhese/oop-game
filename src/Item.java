public class Item {
    private String name;
    private String description;
    private double weight;
    private boolean isMovable;

    public Item(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        isMovable = true;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return "\n      " + this.name + " (" +this.description +") with a weight of " + this.weight + "kg";
    }
}
