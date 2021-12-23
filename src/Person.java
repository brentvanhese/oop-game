public class Person {
    private String displayName;
    private String name;
    private String text;
    private boolean show;

    public Person(String displayName, String name, String text) {
        this.displayName = displayName;
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDisplayName(){
        return displayName;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return displayName + " : " + text;
    }

    public String showPersonInRoom(){
        return "\n      " + displayName + " (talk with me to get a tip).";
    }
}
