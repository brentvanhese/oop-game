/**
 * All valid command words for the game
 * @author Brent Van Hese
 */
public enum CommandWord {
    GO("go", "\t\t(Type 'go' + the direction you want to go too)"),
    QUIT("quit", "\t\t(To end the game)"),
    HELP("help", "\t\t(To get all command and their explenation)"),
    UNKNOWN("", ""),
    TAKE("take", "\t\t(Type this + the item you want to get from the planet and put in your bag)"),
    DROP("drop", "\t\t(Type this + the item you want to get out your bag and put on the planet)"),
    LOOK("look", "\t\t(Type 'look' to get all info | Type 'look planet' to get the planet's info | Type 'look player' to get the player's info)"),
    USE("use", "\t\t(Type this + the item in your bag that you want to use)"),
    UNLOCK("unlock", "\t(Type this + the item that you want to unlock)"),
    TALK("talk", "\t\t(Type this + the person you want to talk to)"),
    DESTROY("destroy", "\t(Type this + the item on the planet you want to destroy)"),
    GIVE("give", "\t\t(Type this + the item in your bag you want to give to the person on the planet)"),
    EXPLODE("explode", "\t(Type this + the item you want to explode)"),
    BACK("back", "\t\t(Type this to go the previous planet)"),
    ITEMS("items", "\t(Type this to get the info of all the items in your bag)");

    private String word, explanation;

    /**
     * constructor
     * @param word the command word
     * @param explanation the explanation of the command word
     */
    CommandWord(String word, String explanation){
        this.word = word;
        this.explanation = explanation;
    }

    /**
     * getter for the command word
     * @return command word
     */
    public String getWord() {
        return word;
    }

    /**
     *
     * @return explanation of the command word
     */
    public String getExplanation(){
        return explanation;
    }
}


